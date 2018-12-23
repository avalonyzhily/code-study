package com.avalony.web;

import com.avalony.auth.AuthorizationValid;
import com.avalony.license.LicenseCreate;
import com.avalony.util.CreateResponseMapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Map;

@RestController
public class CreateCdKeyController {

    private LicenseCreate licenseCreate;
    private AuthorizationValid authorizationValid;

    @Autowired
    public CreateCdKeyController(LicenseCreate licenseCreate, AuthorizationValid authorizationValid) {
        this.licenseCreate = licenseCreate;
        this.authorizationValid = authorizationValid;
    }

    @RequestMapping(value="createCdKey")
    public Map<String,String> aesCreateCdKey(String dateStr, HttpServletRequest req){
        if(StringUtils.isEmpty(dateStr)||!dateStr.matches("\\d{4}-\\d{2}-\\d{2}")){
            return CreateResponseMapUtils.createErrorMsg("请以dateStr=yyyy-MM-dd的形式传入日期参数");
        }
        String authBase64 = req.getHeader("Authorization");
        if(!StringUtils.isEmpty(authBase64)&&authBase64.startsWith("Basic ")){
            String auth = new String(Base64.getDecoder().decode(authBase64.substring(6,authBase64.length())));
            if(authorizationValid.isValidAuth(auth)) {
                return licenseCreate.createLicense(dateStr);
            }else {
                return CreateResponseMapUtils.createErrorMsg("身份验证失效");
            }
        }else {
            return CreateResponseMapUtils.createErrorMsg("身份验证模式错误");
        }
    }
}

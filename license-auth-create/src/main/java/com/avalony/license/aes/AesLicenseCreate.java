package com.avalony.license.aes;

import com.avalony.license.LicenseCreate;
import com.avalony.util.CreateResponseMapUtils;
import com.avalony.util.MacNetCardUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;

/**
 * 临时开放的生成cdkey的类,需要前端通过headers使用Basic Auth验证传送写死的账号密码来获取cdkey
 * @Author avalony
 */
@Component
public class AesLicenseCreate implements LicenseCreate {
    private static final String CHARSET="UTF-8";
    @Value("${valid.license.validText}")
    private String validSignalText;

    public Map<String,String> createLicense(String dateStr) {
        //读取网卡信息
        Map<String,String> netCardNoMap = MacNetCardUtils.queryNetCardNo();
        if (netCardNoMap==null||netCardNoMap.size()==0) {
            return CreateResponseMapUtils.createErrorMsg("网卡信息获取异常");
        }
        String netCardNos = assembleNos(netCardNoMap);
        SecretKeySpec rawKey = AESUtils.getRawKey(validSignalText, CHARSET);
        String rawKeyStr = new String(rawKey.getEncoded(), Charset.forName(CHARSET));
        String cdKey = AESUtils.encrypt(netCardNos + "|" + dateStr, rawKey, CHARSET);
        return CreateResponseMapUtils.createAesRes(cdKey,rawKeyStr);
    }

    private String assembleNos(Map<String, String> netCardNoMap) {
        StringBuilder res = new StringBuilder("");
        Set<Map.Entry<String,String>> entrySet = netCardNoMap.entrySet();
        for(Map.Entry<String,String> entry:entrySet){
            String macStr = entry.getValue();
            res.append(macStr+"-");
        }
        return res.subSequence(0, res.length() - 1).toString();
    }
}

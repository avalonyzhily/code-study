package com.avalony.license.aes;

import com.avalony.license.LicenseStore;
import com.avalony.util.MacNetCardUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class AesLicenseStore implements LicenseStore {
    private static Logger logger = LoggerFactory.getLogger(AesLicenseStore.class);
    private boolean licenseValid;

    private static final String VALID_DATE_STR_REGEX = "\\d{4}-\\d{2}-\\d{2}";
    private static final String DATE_STR_PATTERN = "yyyy-MM-dd";
    private static final String CHARSET="UTF-8";

    @Value("${valid.license.validText}")
    private String validSignalText;
    @Value("${valid.cdKey}")
    private String cdKey;
    @Value("${valid.rawKey}")
    private String rawKey;

    @PostConstruct
    public void initialLicenseValidInfo(){
        this.validatorExecute();
    }

    public boolean getLicenseValid() {
        return this.licenseValid;
    }

    public void validatorExecute(){
        this.licenseValid = validExpiredLicense();
    }

    private boolean validExpiredLicense() {
        boolean valid = false;
        if("".equalsIgnoreCase(cdKey)){
            return valid;
        }
        //读取cpu序列号
        Map<String,String> netCardNoMap = MacNetCardUtils.queryNetCardNo();
        if(netCardNoMap==null||netCardNoMap.size()==0) {
            logger.error("读取cpu序列号为空!,使用默认值");
            return valid;
        }
        List<String> netCardNoListCurrent = this.transferNetCardNos(netCardNoMap);
        SecretKeySpec rawKey = AESUtils.getRawKey(validSignalText,CHARSET);
        if(!isValidRawKey(rawKey)){
            logger.error("错误的密钥串");
            return valid;
        }

        String decryptedKey = AESUtils.decrypt(cdKey, rawKey,CHARSET);
        if(StringUtils.isEmpty(decryptedKey)) {
            logger.error("解密结果为空");
            return valid;
        }

        String[] decryptedKeyArr = decryptedKey.split("\\|");
        if(decryptedKeyArr==null||decryptedKeyArr.length!=2) {
            logger.error("解密结果为结构异常");
            return valid;
        }

        String netCardNosStrCdkey = decryptedKeyArr[0];
        List<String> netCardNoListCdkey = this.transferNetCardNos(netCardNosStrCdkey);
        if(netCardNoListCurrent.size()>0
                &&netCardNoListCurrent.size()==netCardNoListCdkey.size()
                &&netCardNoListCurrent.containsAll(netCardNoListCdkey)) {
            logger.error("设备信息不符");
            return valid;
        }

        String validDateStr = decryptedKeyArr[1];
        if(!StringUtils.isEmpty(validDateStr)&&validDateStr.matches(VALID_DATE_STR_REGEX)){
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_STR_PATTERN);
            LocalDate validDate = LocalDate.parse(validDateStr, dtf);
            return validDate.compareTo(LocalDate.now()) > -1;
        }
        return valid;
    }

    private boolean isValidRawKey(SecretKeySpec rawKey) {
        String rawKeyStrCurrent = new String(rawKey.getEncoded(), Charset.forName(CHARSET));
        return rawKeyStrCurrent!=null?rawKeyStrCurrent.equals(this.rawKey):false;
    }

    private List<String> transferNetCardNos(String netCardNosStrCdkey) {
        List<String> res = new ArrayList();
        String[] netCardNosArr = netCardNosStrCdkey.split("-");
        if(netCardNosArr!=null&&netCardNosArr.length>0){
            for(int i=0;i<netCardNosArr.length;i++){
                res.add(netCardNosArr[i]);
            }
        }
        return res;
    }

    private List<String> transferNetCardNos(Map<String, String> netCardNoMap) {
        List<String> res = new ArrayList(netCardNoMap.size());
        Set<Map.Entry<String,String>> entrySet = netCardNoMap.entrySet();
        for(Map.Entry<String,String> entry:entrySet){
            String macStr = entry.getValue();
            res.add(macStr);
        }
        return res;
    }

}

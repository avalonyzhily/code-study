package com.avalony.util;

import java.util.HashMap;
import java.util.Map;

public class CreateResponseMapUtils {
    public static Map<String,String> createErrorMsg(String s) {
        Map<String,String> res = new HashMap<>();
        res.put("msg",s);
        return res;
    }

    public static Map<String,String> createRsaRes(String[] license) {
        Map<String,String> res = new HashMap<>();
        res.put("cdKey",license[0]);
        res.put("pubKey",license[1]);
        res.put("priKey",license[2]);
        return res;
    }

    public static Map<String,String> createAesRes(String cdKey, String rawKeyStr) {
        Map<String,String> res = new HashMap<>();
        res.put("cdKey",cdKey);
        res.put("rawKey",rawKeyStr);
        return res;
    }
}

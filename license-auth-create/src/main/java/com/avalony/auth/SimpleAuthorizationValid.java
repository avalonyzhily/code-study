package com.avalony.auth;

import org.springframework.stereotype.Component;

/**
 * 暂时用硬编码
 */
@Component
public class SimpleAuthorizationValid implements AuthorizationValid {
    private static final String AUTH_USERNAME="CaiSt";
    private static final String AUTH_PASSWORD="cAIsT";


    public static String getAuthUsername() {
        return AUTH_USERNAME;
    }

    public static String getAuthPassword() {
        return AUTH_PASSWORD;
    }
    public boolean isValidAuth(String auth) {
        return (getAuthUsername()+":"+ getAuthPassword()).equals(auth);
    }
}

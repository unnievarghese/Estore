package com.example.Estore.Estore.Security;

import com.example.Estore.Estore.SpringApplicationContext;

public class SecurityConstants {

    public static final String SIGN_UP_URL = "/home/create-user";
    public static final long EXPIRATION_TIME = 864000000;
    public static final long EMAIL_EXPIRATION_TIME = 600000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String VERIFICATION_EMAIL_URL = "/home/email-verification";
    public static final String PASSWORD_RESET_REQUEST_URL = "/home/password-reset-request";
    public static final String PASSWORD_RESET_URL = "/home/password-reset";


    public static String getTokenSecret(){
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }
}

package com.example.Estore.Estore.Security;

import com.example.Estore.Estore.SpringApplicationContext;

public class SecurityConstants {

    public static final String SIGN_UP_URL = "/home/user/create";
    public static final long EXPIRATION_TIME = 864000000;
    public static final long EMAIL_EXPIRATION_TIME = 600000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String VERIFICATION_EMAIL_URL = "/home/email/verify";
    public static final String PASSWORD_RESET_REQUEST_URL = "/home/password/reset";
    public static final String PASSWORD_RESET_URL = "/home/password/create";
    public static final String USER_CREATE_EMAIL_LINK = "http://localhost:8080/estore/home/email/verify?token=";
    public static final String PASSWORD_EMAIL_LINK = "http://localhost:8080/estore/home/password/create?token=";


    public static String getTokenSecret(){
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }
}

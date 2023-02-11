package com.xiao.util;

public class RedisConstants {

    public static final String LOGIN_USER_KEY = "login:token:";
    public static final String LOGIN_CAPTCHA_KEY = "captcha:";

    public static final Long LOGIN_USER_TTL = 30L;
    public static final Long LOGIN_CAPTCHA_TTL = 5L;
    public static final int LOGIN_CAPTCHA_LEN = 4;


}

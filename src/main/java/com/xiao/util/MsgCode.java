package com.xiao.util;

public class MsgCode {

    public static final String ERR_SERVICE_DEFAULT = "-1";

    // format: Module 01 + Function 01 + ErrorCode 01
    // UserController 01 + login 01

    // register XXXX0X
    public static final String USER_REGISTER_ERR_EXIST= "010101";
    public static final String USER_REGISTER_FAIL = "010102";
    public static final String USER_DELETE_FAIL = "010103";


    // wrong format  = xxxx1x
    public static final String USER_ERR_FORMAT = "010110";
    public static final String USER_LOGIN_ERR_FORMAT_PHONE = "010111";
    public static final String USER_LOGIN_ERR_FORMAT_CAPTCHA = "010112";


    // captcha = xxxx2x
    public static final String USER_LOGIN_ERR_CAPTCHA_TTL = "010120";
    public static final String USER_LOGIN_ERR_CAPTCHA_FREQUENT = "010121";

    // database return false = xxxx4x
    public static final String USER_LOGIN_ERR_SAVE = "010140";
    public static final String USER_LOGIN_ERR_NO_USER = "010141";
    public static final String USER_LOGIN_ERR_WRONG_USER = "010142";


    //无权访问
    public static final String USER_NO_PERMISSION = "010150";

    public static final String USER_LOGIN_ERR_STATUS = "010113";
    public static final String USER_LOGIN_NO_PERMISSION = "010114";


}

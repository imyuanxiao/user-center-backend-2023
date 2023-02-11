package com.xiao.util;

import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.StrUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VaildUtils {

    public static final String USEACCOUNT_FAIL = "用户名长度不少于8";
    public static final String PASSWORD_FAIL = "密码长度不少于8";

    //密码长度为8到20位,必须包含字母和数字，字母区分大小写
    private static String regExPwd = "^(?=.*[0-9])(?=.*[a-zA-Z])(.{8,20})$";

    //用户名长度4-20位，只能包含数字、字母和下划线，且必须字母开头
    private static String regExAccount = "^([a-zA-Z])([0-9a-zA-Z_]{4,20})$";

    /*宽松验证，字母和数字即可*/
    private static String regExEasy = "([0-9a-zA-Z]{4,20})$";


    //用户名为手机号

    /**
     * 密码长度为8到20位,必须包含字母和数字，字母区分大小写
     * @param password
     * @return
     */
    public static boolean checkPassword(String password){
        Pattern Password_Pattern = Pattern.compile(regExEasy);
        Matcher matcher = Password_Pattern.matcher(password);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 用户名长度4-20位，只能包含数字、字母和下划线，且必须字母开头
     * @param userAccount
     * @return
     */
    public static boolean checkUserAccount(String userAccount){
        Pattern Password_Pattern = Pattern.compile(regExEasy);
        Matcher matcher = Password_Pattern.matcher(userAccount);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

}

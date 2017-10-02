package com.technoindians.library;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by girish on 1/7/16.
 */
public class LoginValidation_ {

    private static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static int isName(String name){
        if (name==null||name.length()<=0){
            return 0;
        }
        if (name.length()<=2||name.length()>40){
            return 0;
        }
        return 1;
    }
    public static int isCity(String city){
        if (city==null||city.length()<=0){
            return 0;
        }
        if (city.length()<=2||city.length()>30){
            return 0;
        }
        return 1;
    }

    public static boolean isEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches() && !(email.length() < 3 || email.length() > 140);
    }

    public static int isMobile(String mobile){
        int result = 0;
        if(!Pattern.matches("[a-zA-Z]+", mobile))
        {
            if(mobile.length() < 6 || mobile.length() > 13)
            {
                result = 0;
            }
            else
            {
                result = 1;
            }
        }
        else
        {
            result = 0;
        }
        return result;
    }

    public static boolean isPassword(String password){
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()){
            return false;
        }
        if (password.length() < 6 || password.length() > 10){
            return false;
        }
        return true;
    }
}

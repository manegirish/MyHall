package com.technoindians.library;

import java.util.regex.Pattern;

/**
 * Created by GirishM on 03-10-2017.
 */

public class OtherValidation_ {

    public static boolean isValidFamilyName(String teamName) {
        if (teamName.trim().length() <= 0 || teamName.trim().length() <= 3) {
            return false;
        }
        Pattern p = Pattern.compile("[^a-zA-Z0-9]+([ '-][a-zA-Z]+)*");
        boolean result = p.matcher(teamName).find();
        result = !result && teamName.length() >= 6 && !isNumeric(teamName) && !isStartWithNumber(teamName) || isValidName(teamName);
        return result;
    }

    private static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isValidName(String userName) {
        boolean result = userName.matches("[a-zA-z]+([ '-][a-zA-Z]+)*");
        boolean rtn;
        int len = userName.length();
        rtn = len < 40 && len > 3 && result;
        return rtn;
    }

    private static boolean isStartWithNumber(String teamName) {
        char c = teamName.charAt(0);
        return isNumeric(Character.toString(c));
    }

}

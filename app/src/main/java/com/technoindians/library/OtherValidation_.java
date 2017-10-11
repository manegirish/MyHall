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
        rtn = len < 40 && len > 1 && result;
        return rtn;
    }

    public static boolean isValidMiddleName(String userName) {
        boolean result = userName.matches("[a-zA-z]+([ '-][a-zA-Z]+)*");
        boolean rtn;
        int len = userName.length();
        rtn = len < 40 && len > 0 && result;
        return rtn;
    }

    private static boolean isStartWithNumber(String teamName) {
        char c = teamName.charAt(0);
        return isNumeric(Character.toString(c));
    }

    public static boolean validatePhoneNumber(String phoneNo) {
        //validate phone numbers of format "1234567890"
        if (phoneNo.matches("\\d{10}")) return true;
            //validating phone number with -, . or spaces
        else if (phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) return true;
            //validating phone number with extension length from 3 to 5
        else if (phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) return true;
            //validating phone number where area code is in braces ()
        else if (phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) return true;
            //return false if nothing matches the input
        else if (phoneNo.matches("\\+\\d{2}-\\d{10}")) return true;
        else if (phoneNo.matches("\\+\\d{12}")) return true;
        else return false;

    }


}

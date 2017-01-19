/*
 * Copyright (c) 2016.
 * All Right Reserved  Girish D. Mane (girishmane8692@gmail.com)
 *
 */

package com.technoindians.library;

/**
 * @author Girish D M(girishmane8692@gmail.com)
 *         Created on 28/10/16.
 *         Last Modified on 28/10/16.
 */

public class GetInitials {

    public static String get(String name_) {
        String initials = "";
        String[] parts = name_.split(" ");
        for (int i = 0; i < parts.length; i++) {
            if (!parts[0].equalsIgnoreCase("")) {
                initials = initials + "" + parts[i].charAt(0);
            }else {
                initials = initials + "" + parts[i].charAt(1);
            }
        }
        initials.toUpperCase();
        return initials;
    }
}

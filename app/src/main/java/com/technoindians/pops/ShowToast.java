/*
 * Copyright (c) 2016.
 * All Right Reserved  Girish D. Mane (girishmane8692@gmail.com)
 *
 */

package com.technoindians.pops;

import android.content.Context;
import android.widget.Toast;

import com.technoindians.myhall.R;

/**
 * @author Girish D M(girishmane8692@gmail.com)
 *         Created on 28/10/16.
 *         Last Modified on 28/10/16.
 */

public class ShowToast {

    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void successful(Context context) {
        Toast.makeText(context, context.getResources().getString(R.string.successful), Toast.LENGTH_SHORT).show();
    }
}

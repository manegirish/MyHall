/*
 * Copyright (c) 2016.
 * All Right Reserved  Girish D. Mane (girishmane8692@gmail.com)
 *
 */

package com.technoindians.pops;

import android.app.Activity;
import android.widget.Toast;

/**
 * @author Girish D M(girishmane8692@gmail.com)
 *         Created on 28/10/16.
 *         Last Modified on 28/10/16.
 */

public class ShowToast {

    public static void toast(Activity activity,String message){
        Toast.makeText(activity.getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
}

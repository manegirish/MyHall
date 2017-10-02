/*
 * Copyright (c) 2016
 * Girish D Mane (girishmane8692@gmail.com)
 * Gurujot Singh Pandher (gsp11111992@gmail.com)
 * All rights reserved.
 * This application code can not be used directly without prior permission of owners.
 *
 */

package com.technoindians.main;


import android.app.Application;
import android.content.Context;

/**
 * @author
 * Girish M
 * Created on 15/07/16.
 * Last modified 01/08/2016
 *
 */

public class MyHall extends Application {
    public static final String TAG = MyHall.class.getSimpleName();
    private static MyHall mInstance;

    public static synchronized MyHall getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

}
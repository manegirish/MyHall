
/*
 * Copyright (c) 2016.
 * All Right Reserved  Girish D. Mane (girishmane8692@gmail.com)
 *
 */

package com.technoindians.myhall;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.WindowManager;

import com.technoindians.main.LoginActivity;
import com.technoindians.main.MainActivity;
import com.technoindians.preferences.Preferences;
import com.technoindians.variales.Constants;

/**
 * @author Girish D M(girishmane8692@gmail.com)
 * Created on 27/10/16.
 * Last Modified on 27/10/16.
 */

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Preferences.initialize(getApplicationContext());
    }

    @Override
    protected void onStart() {
        super.onStart();
        new CountDownTimer(1900, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                intent();
            }
        }.start();
    }

    private void intent() {
        Intent next = new Intent(getApplicationContext(), LoginActivity.class);
        if (Preferences.contains(Constants.IS_LOGIN)){
            if (Preferences.get(Constants.IS_LOGIN).equalsIgnoreCase("1")){
                next = new Intent(getApplicationContext(), MainActivity.class);
            }
        }
        startActivity(next);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}

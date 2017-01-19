
/*
 * Copyright (c) 2016.
 * All Right Reserved  Girish D. Mane (girishmane8692@gmail.com)
 *
 */

package com.technoindians.main;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.technoindians.myhall.R;
import com.technoindians.preferences.Preferences;
import com.technoindians.variales.Constants;

/**
 * @author Girish D M(girishmane8692@gmail.com)
 * Created on 27/10/16.
 * Last Modified on 27/10/16.
 */

public class LoginActivity extends Activity implements View.OnClickListener {

    public static final String TAG = LoginActivity.class.getSimpleName();

    private EditText usernameBox, passwordBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Preferences.initialize(getApplicationContext());

        usernameBox = (EditText) findViewById(R.id.login_username);
        passwordBox = (EditText) findViewById(R.id.login_password);

    }

    private boolean validate(String username, String password) {
        if (username == null || username.length() <= 0) {
            usernameBox.setError("Invalid Username");
            return false;
        }
        if (password == null || password.length() <= 0) {
            passwordBox.setError("Invalid Password");
            return false;
        }
        if (username.equalsIgnoreCase("test1234") && password.equalsIgnoreCase("1234")) {
            Preferences.save(Constants.USERNAME, "test1234");
            Preferences.save(Constants.PASSWORD, "1234");
            Preferences.save(Constants.IS_LOGIN, "1");

            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                String username = usernameBox.getText().toString();
                String password = passwordBox.getText().toString();

                if (validate(username, password)) {
                    intent();
                }
                break;

            case R.id.login_register:
                break;

            case R.id.login_forgot_password:
                break;
        }
    }

    private void intent() {
        Intent next = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(next);
        finish();
    }
}

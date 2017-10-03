
/*
 * Copyright (c) 2016.
 * All Right Reserved  Girish D. Mane (girishmane8692@gmail.com)
 *
 */

package com.technoindians.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.technoindians.library.LoginValidation_;
import com.technoindians.myhall.R;
import com.technoindians.network.JsonArrays_;
import com.technoindians.network.MakeCall;
import com.technoindians.network.Urls;
import com.technoindians.parser.GetJson_;
import com.technoindians.pops.ShowSnack;
import com.technoindians.pops.ShowToast;
import com.technoindians.preferences.Preferences;
import com.technoindians.variales.Constants;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * @author Girish D M(girishmane8692@gmail.com)
 *         Created on 27/10/16.
 *         Last Modified on 27/10/16.
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

        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);

        usernameBox = (EditText) findViewById(R.id.login_username);
        passwordBox = (EditText) findViewById(R.id.login_password);

        usernameBox.setText("sagarpdml@gmail.com");
        passwordBox.setText("Test@1234");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                String username = usernameBox.getText().toString();
                String password = passwordBox.getText().toString();
                if (!LoginValidation_.isEmail(username)) {
                    usernameBox.setError("Invalid Email");
                    return;
                }
                if (!LoginValidation_.isPassword(password)) {
                    passwordBox.setError("Invalid Password\nPassword must be alphanumeric\nPassword must contain special character");
                    return;
                }
                new LoginCall(username, password).execute();
                break;

            case R.id.login_register:
                break;

            case R.id.login_forgot_password:
                break;
        }
    }

    private class LoginCall extends AsyncTask<Void, Void, Integer> {
        String email, password;
        private ProgressDialog nDialog;

        LoginCall(String email, String password) {
            this.email = email;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog = new ProgressDialog(LoginActivity.this);
            nDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            nDialog.setMessage("Loading...");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(false);
            nDialog.setCanceledOnTouchOutside(false);
            nDialog.show();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = 12;
            RequestBody requestBody = new FormBody.Builder()
                    .add(Constants.EMAIL, email)
                    .add(Constants.PASSWORD, password)
                    .build();

            try {
                String response = MakeCall.post(Urls.DOMAIN + Urls.LOGIN_URL, requestBody, TAG, getApplicationContext());
                if (response != null) {
                    JsonArray jsonArray = GetJson_.array(response, JsonArrays_.LOG_IN);
                    if (jsonArray != null) {
                        JsonObject object = jsonArray.get(0).getAsJsonObject();
                        if (object.has(Constants.STATUS)) {
                            result = object.get(Constants.STATUS).getAsInt();
                            Preferences.save(Constants.USER_ID, object.get(Constants.USER_ID).getAsString());
                            Preferences.save(Constants.NAME, object.get(Constants.NAME).getAsString());
                            Preferences.save(Constants.EMAIL, object.get(Constants.EMAIL).getAsString());
                            Preferences.save(Constants.IMAGE, object.get(Constants.IMAGE).getAsString());
                            Preferences.save(Constants.IS_LOGIN, "1");
                        } else {
                            result = 11;
                        }
                    } else {
                        result = 11;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if (nDialog != null && nDialog.isShowing()) {
                nDialog.dismiss();
            }
            showResponse(integer, usernameBox);
        }
    }

    private void showResponse(int status, View view) {
        switch (status) {
            case 1:
                intent();
                ShowToast.toast(getApplicationContext(), this.getResources().getString(R.string.successful));
                break;
            case 2:
                ShowSnack.viewSnackError(view, this.getResources().getString(R.string.action_failed));
                break;
            case 11:
                ShowSnack.viewSnackError(view, this.getResources().getString(R.string.internal_error_occurred));
                break;
            case 12:
                ShowSnack.viewSnackError(view, this.getResources().getString(R.string.network_error_occurred));
                break;
            default:
                ShowSnack.viewSnackError(view, this.getResources().getString(R.string.internal_error_occurred));
                break;
        }
    }

    private void intent() {
        Intent next = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(next);
        finish();
    }
}

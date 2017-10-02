
/*
 * Copyright (c) 2016.
 * All Right Reserved  Girish D. Mane (girishmane8692@gmail.com)
 *
 */

package com.technoindians.phonebook;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.technoindians.myhall.R;
import com.technoindians.network.JsonArrays_;
import com.technoindians.network.MakeCall;
import com.technoindians.network.Urls;
import com.technoindians.parser.GetJson_;
import com.technoindians.preferences.Preferences;
import com.technoindians.variales.Constants;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by girish on 26/10/16.
 */

public class PhoneMainFragment extends Fragment {

    public static final String TAG = PhoneMainFragment.class.getSimpleName();

    private Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_layout, container, false);

        activity = getActivity();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Preferences.initialize(activity.getApplicationContext());
        new GetFamilies().execute();
    }

    private class GetFamilies extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            int result = 12;
            RequestBody requestBody = new FormBody.Builder()
                    .add(Constants.USER_ID, Preferences.get(Constants.USER_ID))
                    .add(Constants.ACTION, JsonArrays_.GET_FAMILIES)
                    .build();

            try {
                String response = MakeCall.post(Urls.DOMAIN + Urls.USER_OPERATIONS, requestBody, TAG, activity.getApplicationContext());
                if (response != null) {
                    JsonArray jsonArray = GetJson_.array(response, JsonArrays_.GET_FAMILIES, TAG, activity.getApplicationContext());
                    if (jsonArray != null) {
                        JsonObject object = jsonArray.get(0).getAsJsonObject();
                        if (object.has(Constants.STATUS)) {

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
        }
    }

}


/*
 * Copyright (c) 2016.
 * All Right Reserved  Girish D. Mane (girishmane8692@gmail.com)
 *
 */

package com.technoindians.phonebook;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.technoindians.library.ActivityTransition;
import com.technoindians.library.ErrorMessage_;
import com.technoindians.myhall.R;
import com.technoindians.network.JsonArrays_;
import com.technoindians.network.MakeCall;
import com.technoindians.network.Urls;
import com.technoindians.parser.Contact_;
import com.technoindians.preferences.Preferences;
import com.technoindians.variales.Constants;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by girish on 26/10/16.
 */

public class PhoneMainFragment extends Fragment {

    private static final String TAG = PhoneMainFragment.class.getSimpleName();
    private ArrayList<Family_> familyArrayList;

    private TextView errorText;
    private ListView listView;

    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_layout, container, false);

        activity = getActivity();
        familyArrayList = new ArrayList<>();

        errorText = (TextView) view.findViewById(R.id.list_view_error);
        listView = (ListView) view.findViewById(R.id.list_view);
        listView.setOnItemClickListener(onItemClick);

        FloatingActionButton createButton = (FloatingActionButton) view.findViewById(R.id.list_view_float);
        createButton.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Preferences.initialize(activity.getApplicationContext());
        new GetFamilies().execute();
    }

    private void warningView(int status) {
        if (status == 1) {
            listView.setVisibility(View.VISIBLE);
            errorText.setVisibility(View.GONE);
        } else {
            listView.setVisibility(View.GONE);
            errorText.setVisibility(View.VISIBLE);
            errorText.setText(ErrorMessage_.get(status, activity.getApplicationContext()));
        }
    }


    private AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (familyArrayList.get(position).getStatus() == 1) {
                Intent listIntent = new Intent(activity.getApplicationContext(), ContactListActivity.class);
                listIntent.putExtra(Constants.ID, familyArrayList.get(position).getId());
                startActivity(listIntent, ActivityTransition.moveToNextAnimation(activity.getApplicationContext()));
            }
        }
    };

    private class GetFamilies extends AsyncTask<Void, Void, Integer> {
        private ProgressDialog nDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog = new ProgressDialog(activity);
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
                    .add(Constants.USER_ID, Preferences.get(Constants.USER_ID))
                    .add(Constants.ACTION, JsonArrays_.GET_FAMILIES)
                    .build();

            try {
                String response = MakeCall.post(Urls.DOMAIN + Urls.USER_OPERATIONS, requestBody,
                        TAG, activity.getApplicationContext());
                if (response != null) {
                    familyArrayList = Contact_.parseFamilies(response, JsonArrays_.GET_FAMILIES,
                            activity.getApplicationContext(), TAG);
                    if (familyArrayList.size() > 0) {
                        result = familyArrayList.get(0).getStatus();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (nDialog != null && nDialog.isShowing()) {
                nDialog.dismiss();
            }
            warningView(result);
            if (result == 1) {
                FamilyListAdapter familyListAdapter = new FamilyListAdapter(
                        activity.getApplicationContext(), familyArrayList);
                listView.setAdapter(familyListAdapter);
            }
        }
    }

}

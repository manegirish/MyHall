package com.technoindians.profile;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.technoindians.library.LoginValidation_;
import com.technoindians.library.OtherValidation_;
import com.technoindians.myhall.R;
import com.technoindians.network.JsonArrays_;
import com.technoindians.network.MakeCall;
import com.technoindians.network.Urls;
import com.technoindians.parser.GetJson_;
import com.technoindians.pops.ShowSnack;
import com.technoindians.preferences.Preferences;
import com.technoindians.variales.Constants;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by girish on 26/10/16.
 */

public class ProfileMainFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = ProfileMainFragment.class.getSimpleName();

    private EditText firstBox, middleBox, lastBox, primaryBox, secondaryBox, oldBox, newBox, confirmBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment_layout, container, false);

        firstBox = (EditText) view.findViewById(R.id.profile_fragment_first_name);
        middleBox = (EditText) view.findViewById(R.id.profile_fragment_middle_name);
        lastBox = (EditText) view.findViewById(R.id.profile_fragment_last_name);
        primaryBox = (EditText) view.findViewById(R.id.profile_fragment_primary_contact);
        secondaryBox = (EditText) view.findViewById(R.id.profile_fragment_secondary_contact);

        oldBox = (EditText) view.findViewById(R.id.profile_fragment_old_pass);
        newBox = (EditText) view.findViewById(R.id.profile_fragment_new_pass);
        confirmBox = (EditText) view.findViewById(R.id.profile_fragment_confirm_pass);

        firstBox.setText(Preferences.get(Constants.FIRST_NAME));
        middleBox.setText(Preferences.get(Constants.MIDDLE_NAME));
        lastBox.setText(Preferences.get(Constants.LAST_NAME));
        primaryBox.setText(Preferences.get(Constants.PRIMARY_PHONE));
        secondaryBox.setText(Preferences.get(Constants.SECONDARY_PHONE));

        Button passwordButton = (Button) view.findViewById(R.id.profile_fragment_password_button);
        passwordButton.setOnClickListener(this);

        Button profileButton = (Button) view.findViewById(R.id.profile_fragment_profile_button);
        profileButton.setOnClickListener(this);

        return view;
    }

    private boolean validatePassword(String new_password, String confirm_password, String old_password) {
        if (old_password.length() <= 0) {
            oldBox.setError("Invalid Old Password ");
            return false;
        }
        if (!LoginValidation_.isPassword(new_password)) {
            newBox.setError("Invalid Password\nPassword must be alphanumeric\nPassword must contain special character");
            return false;
        }
        if (!new_password.equals(confirm_password)) {
            confirmBox.setError("Password mismatch");
            return false;
        }
        return true;
    }

    private boolean validateProfile(String first_name, String middle_name, String last_name,
                                    String primary_contact, String secondary_contact) {
        if (first_name.length() <= 0 || !OtherValidation_.isValidName(first_name)) {
            firstBox.setError("Invalid First Name");
            return false;
        }
        if (middle_name.length() > 0 && !OtherValidation_.isValidName(last_name)) {
            middleBox.setError("Invalid Middle Name");
            return false;
        }
        if (last_name.length() <= 0 || !OtherValidation_.isValidName(last_name)) {
            lastBox.setError("Invalid Last Name");
            return false;
        }
        if (primary_contact.length() > 0 && !OtherValidation_.validatePhoneNumber(primary_contact)) {
            primaryBox.setError("Invalid Primary Contact");
            return false;
        }
        if (primary_contact.length() > 25) {
            primaryBox.setError("Invalid Primary Contact");
            return false;
        }
        if (secondary_contact.length() > 0 && !OtherValidation_.validatePhoneNumber(secondary_contact)) {
            secondaryBox.setError("Invalid Secondary Contact");
            return false;
        }
        if (secondary_contact.length() > 25) {
            secondaryBox.setError("Invalid Secondary Contact");
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_fragment_password_button:
                String old_password = oldBox.getText().toString();
                String new_password = newBox.getText().toString();
                String confirm_password = confirmBox.getText().toString();
                if (validatePassword(new_password, confirm_password, old_password)) {
                    new UpdatePassword(new_password, old_password).execute();
                }
                break;
            case R.id.profile_fragment_profile_button:
                String first_name = firstBox.getText().toString().trim();
                String middle_name = middleBox.getText().toString().trim();
                String last_name = lastBox.getText().toString().trim();
                String primary_contact = primaryBox.getText().toString().trim();
                String secondary_contact = secondaryBox.getText().toString().trim();

                if (validateProfile(first_name, middle_name, last_name, primary_contact, secondary_contact)) {
                    new UpdateProfile(first_name, middle_name, last_name, primary_contact, secondary_contact).execute();
                }
                break;
        }
    }

    private class UpdateProfile extends AsyncTask<Void, Void, Integer> {
        private String first_name, middle_name, last_name, primary_contact, secondary_contact;
        private ProgressDialog nDialog;

        UpdateProfile(String first_name, String middle_name, String last_name,
                      String primary_contact, String secondary_contact) {
            this.first_name = first_name;
            this.middle_name = middle_name;
            this.last_name = last_name;
            this.primary_contact = primary_contact;
            this.secondary_contact = secondary_contact;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog = new ProgressDialog(getActivity());
            nDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            nDialog.setMessage("Updating...");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(false);
            nDialog.setCanceledOnTouchOutside(false);
            nDialog.show();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = 12;
            RequestBody requestBody = new FormBody.Builder()
                    .add(Constants.FIRST_NAME, first_name)
                    .add(Constants.LAST_NAME, last_name)
                    .add(Constants.MIDDLE_NAME, middle_name)
                    .add(Constants.PRIMARY_PHONE, primary_contact)
                    .add(Constants.SECONDARY_PHONE, secondary_contact)
                    .add(Constants.USER_ID, Preferences.get(Constants.USER_ID))
                    .add(Constants.ACTION, JsonArrays_.CHANGE_PROFILE)
                    .build();
            try {
                String response = MakeCall.post(Urls.DOMAIN + Urls.USER_OPERATIONS, requestBody, TAG,
                        getActivity().getApplicationContext());
                if (response != null) {
                    JsonArray jsonArray = GetJson_.array(response, JsonArrays_.CHANGE_PROFILE);
                    if (jsonArray != null) {
                        JsonObject object = jsonArray.get(0).getAsJsonObject();
                        if (object.has(Constants.STATUS)) {
                            result = object.get(Constants.STATUS).getAsInt();
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
            if (integer == 1) {
                Preferences.save(Constants.FIRST_NAME, first_name);
                Preferences.save(Constants.MIDDLE_NAME, middle_name);
                Preferences.save(Constants.LAST_NAME, last_name);
                Preferences.save(Constants.NAME, first_name + " " + last_name);
                Preferences.save(Constants.PRIMARY_PHONE, primary_contact);
                Preferences.save(Constants.SECONDARY_PHONE, secondary_contact);
            }
            showResponse(integer, firstBox);
        }
    }


    private class UpdatePassword extends AsyncTask<Void, Void, Integer> {
        private String new_password, old_password;
        private ProgressDialog nDialog;

        UpdatePassword(String new_password, String old_password) {
            this.old_password = old_password;
            this.new_password = new_password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog = new ProgressDialog(getActivity());
            nDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            nDialog.setMessage("Updating...");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(false);
            nDialog.setCanceledOnTouchOutside(false);
            nDialog.show();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = 12;
            RequestBody requestBody = new FormBody.Builder()
                    .add("new_password", new_password)
                    .add("old_password", old_password)
                    .add(Constants.USER_ID, Preferences.get(Constants.USER_ID))
                    .add(Constants.ACTION, JsonArrays_.CHANGE_PASSWORD)
                    .build();
            try {
                String response = MakeCall.post(Urls.DOMAIN + Urls.USER_OPERATIONS, requestBody, TAG,
                        getActivity().getApplicationContext());
                if (response != null) {
                    JsonArray jsonArray = GetJson_.array(response, JsonArrays_.CHANGE_PASSWORD);
                    if (jsonArray != null) {
                        JsonObject object = jsonArray.get(0).getAsJsonObject();
                        if (object.has(Constants.STATUS)) {
                            result = object.get(Constants.STATUS).getAsInt();
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
            if (integer == 1) {
                newBox.setText("");
                oldBox.setText("");
                confirmBox.setText("");
            }
            showResponse(integer, newBox);
        }
    }

    private void showResponse(int status, View view) {
        switch (status) {
            case 1:
                ShowSnack.viewSnackSuccess(view, this.getResources().getString(R.string.successful));
                break;
            case 2:
                ShowSnack.viewSnackError(view, this.getResources().getString(R.string.action_failed));
                break;
            case 3:
                ShowSnack.viewSnackError(view, "Old password did not matched");
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
}

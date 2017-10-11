package com.technoindians.phonebook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

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
import com.technoindians.pops.ShowToast;
import com.technoindians.preferences.Preferences;
import com.technoindians.variales.Constants;

import okhttp3.FormBody;
import okhttp3.RequestBody;


/**
 * Created by GirishM on 03-10-2017.
 */

public class AddMemberActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = AddMemberActivity.class.getSimpleName();
    private long id = 0;
    private String relation = "";

    private TextView relationText;
    private EditText firstNameBox, middleNameBox, lastNameBox, emailBox, primaryContactBox, secondaryContactBox,
            passwordBox, confirmPasswordBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_member_activity_layout);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_toolbar_layout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.vi_left_arrow_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView titleText = (TextView) findViewById(R.id.activity_toolbar_title);
        titleText.setText(this.getResources().getString(R.string.add_member));

        TextView submitText = (TextView) findViewById(R.id.activity_toolbar_post);
        submitText.setVisibility(View.VISIBLE);
        submitText.setOnClickListener(this);

        AppCompatImageView searchButton = (AppCompatImageView) findViewById(R.id.activity_toolbar_search_button);
        searchButton.setVisibility(View.GONE);

        relationText = (TextView) findViewById(R.id.add_member_relation);
        relationText.setOnClickListener(this);

        firstNameBox = (EditText) findViewById(R.id.add_member_first_name);
        middleNameBox = (EditText) findViewById(R.id.add_member_middle_name);
        lastNameBox = (EditText) findViewById(R.id.add_member_last_name);
        emailBox = (EditText) findViewById(R.id.add_member_email);
        primaryContactBox = (EditText) findViewById(R.id.add_member_primary_contact);
        secondaryContactBox = (EditText) findViewById(R.id.add_member_secondary_contact);
        passwordBox = (EditText) findViewById(R.id.add_member_password);
        confirmPasswordBox = (EditText) findViewById(R.id.add_member_password_confirm);

        Intent data = getIntent();
        if (data != null && data.hasExtra(Constants.ID)) {
            id = data.getLongExtra(Constants.ID, 0);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.animation_left_to_right,
                R.anim.animation_right_to_left);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (id <= 0) {
            onBackPressed();
        }
        Preferences.initialize(getApplicationContext());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_member_relation:
                showRelationPopup();
                break;
            case R.id.activity_toolbar_post:
                String first_name = firstNameBox.getText().toString().trim();
                String middle_name = middleNameBox.getText().toString().trim();
                String last_name = lastNameBox.getText().toString().trim();
                String email = emailBox.getText().toString().trim();
                String primaryContact = primaryContactBox.getText().toString().trim();
                String secondaryContact = secondaryContactBox.getText().toString().trim();
                String password = passwordBox.getText().toString().trim();
                String password_confirm = confirmPasswordBox.getText().toString().trim();
                if (isValid(first_name, middle_name, last_name, email, password, password_confirm)) {
                    new AddMember(first_name, middle_name, last_name, email, password, primaryContact,
                            secondaryContact).execute();
                }
                break;
        }
    }

    private boolean isValid(String first_name, String middle_name, String last_name, String email, String password,
                            String password_confirm) {
        if (relation.trim().length() <= 0) {
            ShowToast.toast(getApplicationContext(), "Select relation");
            return false;
        }
        if (first_name.length() <= 0 || !OtherValidation_.isValidName(first_name)) {
            firstNameBox.setError("Invalid First Name");
            return false;
        }
        if (middle_name.length() > 0 && !OtherValidation_.isValidMiddleName(middle_name)) {
            middleNameBox.setError("Invalid Middle Name");
            return false;
        }
        if (last_name.length() <= 0 || !OtherValidation_.isValidName(last_name)) {
            lastNameBox.setError("Invalid Last Name");
            return false;
        }
        if (email.length() <= 0 || !LoginValidation_.isEmail(email)) {
            emailBox.setError("Invalid Email");
            return false;
        }
        if (password.length() <= 0 || !LoginValidation_.isPassword(password)) {
            passwordBox.setError("Invalid Password\nPassword must be alphanumeric\nPassword must contain special character");
            return false;
        }
        if (!password.equals(password_confirm)) {
            confirmPasswordBox.setError("Password mismatch");
            return false;
        }
        return true;
    }

    private class AddMember extends AsyncTask<Void, Void, Integer> {
        String first_name, middle_name, last_name, email, password, primaryContact, secondaryContact;
        private ProgressDialog nDialog;

        AddMember(String first_name, String middle_name, String last_name, String email, String password,
                  String primaryContact, String secondaryContact) {
            this.first_name = first_name;
            this.middle_name = middle_name;
            this.last_name = last_name;
            this.email = email;
            this.password = password;
            this.primaryContact = primaryContact;
            this.secondaryContact = secondaryContact;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog = new ProgressDialog(AddMemberActivity.this);
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
                    .add("firstname", first_name)
                    .add("lastname", last_name)
                    .add("middlename", middle_name)
                    .add(Constants.EMAIL, email)
                    .add(Constants.PASSWORD, password)
                    .add("primary_phone", primaryContact)
                    .add("secondary_phone", secondaryContact)
                    .add("relation", relation)
                    .add("family_id", "" + id)
                    .add(Constants.USER_ID, Preferences.get(Constants.USER_ID))
                    .add(Constants.ACTION, JsonArrays_.ADD_CONTACT)
                    .build();
            try {
                String response = MakeCall.post(Urls.DOMAIN + Urls.USER_OPERATIONS, requestBody, TAG, getApplicationContext());
                if (response != null) {
                    JsonArray jsonArray = GetJson_.array(response, JsonArrays_.ADD_CONTACT);
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
            showResponse(integer, relationText);
        }
    }

    private void showResponse(int status, View view) {
        switch (status) {
            case 1:
                onBackPressed();
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

    private void showRelationPopup() {
        final PopupMenu popup = new PopupMenu(this, relationText);
        popup.getMenuInflater().inflate(R.menu.relation_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                relation = item.getTitle().toString();
                relationText.setText(relation);
                popup.dismiss();
                return true;
            }
        });
        popup.show();
    }

}

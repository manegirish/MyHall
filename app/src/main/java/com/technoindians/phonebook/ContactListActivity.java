package com.technoindians.phonebook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.technoindians.views.CircularReveal;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by GirishM on 03-10-2017.
 */

public class ContactListActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = ContactListActivity.class.getSimpleName();
    private long id = 0;
    private ArrayList<Member_> memberArrayList;

    private TextView errorText;
    private ListView listView;
    private Toolbar searchToolbar;
    private Menu search_menu;
    private MenuItem item_search;

    private MemberListAdapter memberListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list_activity_layout);

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
        setSearchToolbar();

        TextView titleText = (TextView) findViewById(R.id.activity_toolbar_title);
        titleText.setText(this.getResources().getString(R.string.contact));

        errorText = (TextView) findViewById(R.id.list_view_error);
        listView = (ListView) findViewById(R.id.list_view);

        final FloatingActionButton createButton = (FloatingActionButton) findViewById(R.id.list_view_float);
        createButton.setImageResource(R.drawable.ic_add_member_float);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createIntent = new Intent(getApplicationContext(), AddMemberActivity.class);
                createIntent.putExtra(Constants.ID, id);
                startActivity(createIntent, ActivityTransition.moveToNextAnimation(getApplicationContext()));
            }
        });

        AppCompatImageView searchButton = (AppCompatImageView) findViewById(R.id.activity_toolbar_search_button);
        searchButton.setVisibility(View.VISIBLE);
        searchButton.setOnClickListener(this);

        Intent data = getIntent();
        if (data != null && data.hasExtra(Constants.ID)) {
            id = data.getLongExtra(Constants.ID, 0);
            Log.e(TAG, "id: " + id);
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
        if (id > 0) {
            Preferences.initialize(getApplicationContext());
            new GetContacts().execute();
        } else {
            onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_toolbar_search_button:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    CircularReveal.circleReveal(R.id.searchtoolbar, 1, true, true, ContactListActivity.this);
                } else {
                    searchToolbar.setVisibility(View.VISIBLE);
                }
                item_search.expandActionView();
                break;
        }
    }

    private void setSearchToolbar() {
        searchToolbar = (Toolbar) findViewById(R.id.searchtoolbar);
        if (searchToolbar != null) {
            searchToolbar.inflateMenu(R.menu.menu_search);
            search_menu = searchToolbar.getMenu();

            searchToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        CircularReveal.circleReveal(R.id.searchtoolbar, 1, true, false, ContactListActivity.this);
                    else
                        searchToolbar.setVisibility(View.GONE);
                }
            });

            item_search = search_menu.findItem(R.id.action_filter_search);

            MenuItemCompat.setOnActionExpandListener(item_search, new MenuItemCompat.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    // Do something when collapsed
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        CircularReveal.circleReveal(R.id.searchtoolbar, 1, true, false, ContactListActivity.this);
                    } else
                        searchToolbar.setVisibility(View.GONE);
                    return true;
                }

                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    // Do something when expanded
                    return true;
                }
            });
            initSearchView();
        }
    }

    private void initSearchView() {
        final SearchView searchView =
                (SearchView) search_menu.findItem(R.id.action_filter_search).getActionView();
        // Enable/Disable Submit button in the keyboard
        searchView.setSubmitButtonEnabled(false);
        // Change search close button image
        ImageView closeButton = (ImageView) searchView.findViewById(R.id.search_close_btn);
        closeButton.setImageResource(R.drawable.vi_cancel_white);
        // set hint and the text colors
        EditText txtSearch = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
        txtSearch.setHint("Search...");
        txtSearch.setHintTextColor(getResources().getColor(R.color.white));
        txtSearch.setTextColor(getResources().getColor(R.color.white));
        txtSearch.setBackgroundColor(this.getResources().getColor(R.color.transparent));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearch(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                callSearch(newText);
                return true;
            }

            private void callSearch(String query) {
                if (memberListAdapter != null) {
                    memberListAdapter.filterUsers(query);
                }
            }
        });
    }

    private void warningView(int status) {
        if (status == 1) {
            listView.setVisibility(View.VISIBLE);
            errorText.setVisibility(View.GONE);
        } else {
            listView.setVisibility(View.GONE);
            errorText.setVisibility(View.VISIBLE);
            errorText.setText(ErrorMessage_.get(status, getApplicationContext()));
        }
    }

    private class GetContacts extends AsyncTask<Void, Void, Integer> {
        private ProgressDialog nDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog = new ProgressDialog(ContactListActivity.this);
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
                    .add(Constants.ID, "" + id)
                    .add(Constants.ACTION, JsonArrays_.GET_MEMBERS)
                    .build();

            try {
                String response = MakeCall.post(Urls.DOMAIN + Urls.USER_OPERATIONS, requestBody,
                        TAG, getApplicationContext());
                if (response != null) {
                    memberArrayList = Contact_.parseMembers(response, getApplicationContext(), TAG);
                    if (memberArrayList.size() > 0) {
                        result = memberArrayList.get(0).getStatus();
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
                memberListAdapter = new MemberListAdapter(getApplicationContext(), memberArrayList);
                listView.setAdapter(memberListAdapter);
            }
        }
    }
}

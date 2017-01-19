/*
 * Copyright (c) 2016.
 * All Right Reserved  Girish D. Mane (girishmane8692@gmail.com)
 *
 */

package com.technoindians.calendar;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.technoindians.myhall.R;

import java.util.ArrayList;

/**
 * @author Girish D M(girishmane8692@gmail.com)
 *         Created on 28/10/16.
 *         Last Modified on 28/10/16.
 */

public class EventListActivity extends ActionBarActivity {

    public static final String TAG = EventListActivity.class.getSimpleName();
    private Toolbar toolbar;
    private ListView listView;
    private ArrayList<Event_> eventList;
    private EventAdapter eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        toolbar = (Toolbar) findViewById(R.id.event_list_toolbar);
        setSupportActionBar(toolbar);
//        getActionBar().setDisplayHomeAsUpEnabled(true);

        eventList = new ArrayList<>();
        sampleData();

        listView = (ListView)findViewById(R.id.event_list_view);
        Log.e(TAG,"size "+eventList.size());
        eventAdapter = new EventAdapter(EventListActivity.this,eventList);
        listView.setAdapter(eventAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sampleData(){
        Event_ event_ = new Event_();
        Event_ event_1 = new Event_();

        event_.setId("1");
        event_.setName("Amaya Patel");
        event_.setTitle("Marriage Function");
        event_.setDescription("Amaya weds Ameya on 20th October,2016");
        event_.setTime("9.30 Pm");

        event_1.setId("2");
        event_1.setName("Girish Mane");
        event_1.setTitle("Birth day party");
        event_1.setDescription("Birth Day Celebration on 20th October,2016");
        event_1.setTime("7.30 Pm");

        eventList.add(event_);
        eventList.add(event_1);
    }
}

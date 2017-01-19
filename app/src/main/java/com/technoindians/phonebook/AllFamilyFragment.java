/*
 * Copyright (c) 2016.
 * All Right Reserved  Girish D. Mane (girishmane8692@gmail.com)
 *
 */

package com.technoindians.phonebook;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.technoindians.myhall.R;

import java.util.ArrayList;

/**
 * @author Girish D M(girishmane8692@gmail.com)
 *         Created on 28/10/16.
 *         Last Modified on 28/10/16.
 */

public class AllFamilyFragment extends Fragment {

    public static final String TAG = AllFamilyFragment.class.getSimpleName();

    private ArrayList<Friend_> friendsList;
    private ListView listView;
    private ContactAdapter contactAdapter;
    private Activity activity;
    private Toolbar toolbar;

    public AllFamilyFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_list_layout, container, false);

        activity = getActivity();

        toolbar = (Toolbar)view.findViewById(R.id.event_list_toolbar);
        toolbar.setVisibility(View.GONE);

        listView = (ListView)view.findViewById(R.id.event_list_view);

        friendsList = new ArrayList<>();
        sampleList();

        contactAdapter = new ContactAdapter(activity,friendsList,2);
        listView.setAdapter(contactAdapter);

        return view;
    }

    private void sampleList(){
        String [] name = {"Girish Mane","Amaya Patel","Sagar Padmale","Geeta Shukla","Ajeet Maurya",
                "Jeams Bond","Salman Khan","Katrina Kaif","Kareena Kapoor","Emma Watson"};
        for (int i = 0;i<name.length;i++){
            Friend_ friend_ = new Friend_();
            friend_.setId(""+i);
            friend_.setName(name[i]);

            friendsList.add(friend_);
        }
    }
}

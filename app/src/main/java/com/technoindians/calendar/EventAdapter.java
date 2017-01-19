/*
 * Copyright (c) 2016.
 * All Right Reserved  Girish D. Mane (girishmane8692@gmail.com)
 *
 */

package com.technoindians.calendar;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.technoindians.library.GetInitials;
import com.technoindians.myhall.R;

import java.util.ArrayList;


/**
 * @author Girish D M(girishmane8692@gmail.com)
 *         Created on 28/10/16.
 *         Last Modified on 28/10/16.
 */

public class EventAdapter extends ArrayAdapter<Event_> {

    private ArrayList<Event_> eventList;
    private Activity activity;

    public EventAdapter(Activity activity, ArrayList<Event_> eventList) {
        super(activity, 0, eventList);
        this.eventList = eventList;
        this.activity = activity;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parrent) {
        ViewHolder holder;
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.event_list_item, parrent, false);
            holder = new ViewHolder();

            holder.nameText = (TextView)view.findViewById(R.id.event_list_item_name);
            holder.titleText = (TextView)view.findViewById(R.id.event_list_item_title);
            holder.descriptionText = (TextView)view.findViewById(R.id.event_list_item_description);
            holder.timeText = (TextView)view.findViewById(R.id.event_list_item_time);
            holder.initialText = (TextView)view.findViewById(R.id.event_list_item_initials);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Event_ event_ = eventList.get(position);
        Log.e("Event Adapter","=> "+event_.getName()+" position -> "+position);
        holder.nameText.setText(event_.getName());
        holder.titleText.setText(event_.getTitle());
        holder.descriptionText.setText(event_.getDescription());
        holder.timeText.setText(event_.getTime());
        holder.initialText.setText(GetInitials.get(event_.getName()));

        return view;
    }

    class ViewHolder {
        TextView nameText,titleText,descriptionText,timeText,initialText;
    }
}
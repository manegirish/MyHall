/*
 * Copyright (c) 2016.
 * All Right Reserved  Girish D. Mane (girishmane8692@gmail.com)
 *
 */

package com.technoindians.phonebook;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.technoindians.library.GetInitials;
import com.technoindians.myhall.R;

import java.util.ArrayList;
import java.util.Random;


/**
 * @author Girish D M(girishmane8692@gmail.com)
 *         Created on 28/10/16.
 *         Last Modified on 28/10/16.
 */

public class ContactAdapter extends ArrayAdapter<Friend_> {

    private ArrayList<Friend_> contactList;
    private Activity activity;
    private int type;
    private int[] image = {R.drawable.pink_circle, R.drawable.primary_circle,
            R.drawable.orange_circle,R.drawable.green_circle,R.drawable.green_circle2};

    public ContactAdapter(Activity activity, ArrayList<Friend_> contactList, int type) {
        super(activity, 0, contactList);
        this.contactList = contactList;
        this.activity = activity;
        this.type = type;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parrent) {
        ViewHolder holder;
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.contact_list_item_layout, parrent, false);
            holder = new ViewHolder();

            holder.nameText = (TextView) view.findViewById(R.id.contact_list_item_name);
            holder.initialText = (TextView) view.findViewById(R.id.contact_list_item_photo);
            holder.icon = (ImageView) view.findViewById(R.id.contact_list_item_photo_team);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Friend_ friend_ = contactList.get(position);
        Log.e("Contact Adapter", "=> " + friend_.getName() + " position -> " + position);
        holder.nameText.setText(friend_.getName());
        if (type == 1) {
            holder.initialText.setVisibility(View.VISIBLE);
            holder.icon.setVisibility(View.GONE);
            holder.initialText.setText(GetInitials.get(friend_.getName()));
            holder.initialText.setBackgroundResource(randomImage());
        } else {
            holder.initialText.setVisibility(View.GONE);
            holder.icon.setVisibility(View.VISIBLE);
            holder.icon.setBackgroundResource(R.drawable.ic_group);
        }
        return view;
    }

    private int randomImage() {
        Random random = new Random();
        int position = random.nextInt(image.length);
        return image[position];
    }

    class ViewHolder {
        TextView nameText, initialText;
        ImageView icon;
    }
}
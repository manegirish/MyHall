package com.technoindians.phonebook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.technoindians.myhall.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Girish Mane(girishmane8692@gmail.com)
 *         Created on 03-10-2017
 *         Last Modified on 03-10-2017
 */

class FamilyListAdapter extends ArrayAdapter<Family_> {

    private List<Family_> familyList;
    private List<Family_> list;
    private Context context;

    FamilyListAdapter(Context context, List<Family_> familyList) {
        super(context, 0, familyList);
        this.familyList = familyList;
        this.context = context;
        this.list = new ArrayList<>();
        this.list.addAll(this.familyList);
    }

    @Override
    public int getCount() {
        return familyList.size();
    }

    @Override
    public Family_ getItem(int position) {
        if (familyList != null && familyList.size() > 0) {
            return familyList.get(position);
        }
        return null;
    }

    private int getStatus(int position) {
        if (familyList != null && familyList.size() > 0) {
            return familyList.get(position).getStatus();
        }
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;

        View view = convertView;
        if (view == null) {
            viewHolder = new ViewHolder();

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.family_list_item_layout, parent, false);

            viewHolder.nameText = (TextView) view.findViewById(R.id.family_list_item_title);
            viewHolder.memberText = (TextView) view.findViewById(R.id.family_list_item_sub_title);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (familyList.get(position).getStatus() == 1) {
            view.setBackgroundColor(context.getResources().getColor(R.color.white));
            viewHolder.nameText.setText(familyList.get(position).getName());
            viewHolder.memberText.setText("Total " + "Members " + familyList.get(position).getTotal_members());
        }
        return view;
    }

    private class ViewHolder {
        TextView nameText, memberText;
    }

    public void filterUsers(String searchString) {
        familyList.clear();
        if (searchString == null) {
            familyList.addAll(list);
            notifyDataSetChanged();
            return;
        }
        searchString = searchString.toLowerCase(Locale.getDefault());
        if (searchString.length() <= 0) {
            familyList.addAll(list);
        } else {
            for (Family_ consumer_ : list) {
                String name = consumer_.getName();
                if (name.toLowerCase(Locale.getDefault()).contains(searchString)) {
                    familyList.add(consumer_);
                }
            }
        }
        if (familyList.size() <= 0) {
            Family_ consumer_ = new Family_();
            consumer_.setStatus(0);
            familyList.add(consumer_);
        }
        notifyDataSetChanged();
    }
}

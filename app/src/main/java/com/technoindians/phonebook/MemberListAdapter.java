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

class MemberListAdapter extends ArrayAdapter<Member_> {

    private List<Member_> memberList;
    private List<Member_> list;
    private Context context;

    MemberListAdapter(Context context, List<Member_> memberList) {
        super(context, 0, memberList);
        this.memberList = memberList;
        this.context = context;
        this.list = new ArrayList<>();
        this.list.addAll(this.memberList);
    }

    @Override
    public int getCount() {
        return memberList.size();
    }

    @Override
    public Member_ getItem(int position) {
        if (memberList != null && memberList.size() > 0) {
            return memberList.get(position);
        }
        return null;
    }

    private int getStatus(int position) {
        if (memberList != null && memberList.size() > 0) {
            return memberList.get(position).getStatus();
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

        if (memberList.get(position).getStatus() == 1) {
            view.setBackgroundColor(context.getResources().getColor(R.color.white));
            viewHolder.nameText.setText(memberList.get(position).getName());
            viewHolder.memberText.setText(memberList.get(position).getEmail());
        }
        return view;
    }

    private class ViewHolder {
        TextView nameText, memberText;
    }

    public void filterUsers(String searchString) {
        memberList.clear();
        if (searchString == null) {
            memberList.addAll(list);
            notifyDataSetChanged();
            return;
        }
        searchString = searchString.toLowerCase(Locale.getDefault());
        if (searchString.length() <= 0) {
            memberList.addAll(list);
        } else {
            for (Member_ consumer_ : list) {
                String name = consumer_.getName();
                if (name.toLowerCase(Locale.getDefault()).contains(searchString)) {
                    memberList.add(consumer_);
                }
            }
        }
        if (memberList.size() <= 0) {
            Member_ consumer_ = new Member_();
            consumer_.setStatus(0);
            memberList.add(consumer_);
        }
        notifyDataSetChanged();
    }
}

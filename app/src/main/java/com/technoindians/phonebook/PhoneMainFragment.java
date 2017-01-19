
/*
 * Copyright (c) 2016.
 * All Right Reserved  Girish D. Mane (girishmane8692@gmail.com)
 *
 */

package com.technoindians.phonebook;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.technoindians.myhall.R;
import com.technoindians.variales.Constants;
import com.technoindians.variales.Fragments_;

/**
 * Created by girish on 26/10/16.
 */

public class PhoneMainFragment extends Fragment {

    public static final String TAG = PhoneMainFragment.class.getSimpleName();
    private TabLayout tabs;
    private int position = 0;

    public PhoneMainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_main_layout, container, false);
        tabs = (TabLayout) view.findViewById(R.id.sos_update_tabs);

        tabs.addTab(tabs.newTab().setText("All Contact"));
        tabs.addTab(tabs.newTab().setText("Family Contact"));

        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey(Constants.POSITION)) {
                position = Integer.parseInt(bundle.getString(Constants.POSITION));
                setTabFragment(position);
            } else {
                setTabFragment(0);
            }
        } else {
            setTabFragment(0);
        }

        tabs.setOnTabSelectedListener(tabSelectedListener);
        return view;
    }

    TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            position = tab.getPosition();
            setTabFragment(position);
        }
        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }
        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.POSITION, "" + position);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(Constants.POSITION)) {
                position = Integer.parseInt(savedInstanceState.getString(Constants.POSITION));
                setTabFragment(position);
            }
        } else {
            setTabFragment(position);
        }
    }

    private void setTabFragment(int tabPosition) {
        tabs.getTabAt(tabPosition).select();
        switch (tabPosition) {
            case 0:
                replaceFragment(new AllContactFragment());
                break;
            case 1:
                replaceFragment(new AllFamilyFragment());
                break;
            default:
                replaceFragment(new AllContactFragment());
                break;
        }
    }

    public void replaceFragment(Fragment fragment) {
        android.support.v4.app.FragmentManager fm = getFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        Fragment oldFragment = fm.findFragmentByTag(Fragments_.ALL_CONTACT);
        if (oldFragment != null) {
            ft.remove(oldFragment);
        }
        ft.replace(R.id.sos_update_container, fragment, Fragments_.ALL_CONTACT);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }
}

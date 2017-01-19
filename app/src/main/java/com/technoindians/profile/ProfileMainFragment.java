package com.technoindians.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.technoindians.myhall.R;

/**
 * Created by girish on 26/10/16.
 */

public class ProfileMainFragment extends Fragment {

    public static final String TAG = ProfileMainFragment.class.getSimpleName();

    public ProfileMainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile_fragment_layout, container, false);
    }
}

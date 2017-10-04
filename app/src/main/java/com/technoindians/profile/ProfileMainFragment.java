package com.technoindians.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.technoindians.myhall.R;
import com.technoindians.preferences.Preferences;
import com.technoindians.variales.Constants;

/**
 * Created by girish on 26/10/16.
 */

public class ProfileMainFragment extends Fragment {

    public static final String TAG = ProfileMainFragment.class.getSimpleName();

    private EditText firstBox, middleBox, lastBox, primaryBox, secondaryBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment_layout, container, false);

        firstBox = (EditText) view.findViewById(R.id.profile_fragment_first_name);
        middleBox = (EditText) view.findViewById(R.id.profile_fragment_middle_name);
        lastBox = (EditText) view.findViewById(R.id.profile_fragment_last_name);
        primaryBox = (EditText) view.findViewById(R.id.profile_fragment_primary_contact);
        secondaryBox = (EditText) view.findViewById(R.id.profile_fragment_secondary_contact);

        firstBox.setText(Preferences.get(Constants.FIRST_NAME));
        middleBox.setText(Preferences.get(Constants.MIDDLE_NAME));
        lastBox.setText(Preferences.get(Constants.LAST_NAME));
        primaryBox.setText(Preferences.get(Constants.PRIMARY_PHONE));
        secondaryBox.setText(Preferences.get(Constants.SECONDARY_PHONE));
        return view;
    }
}

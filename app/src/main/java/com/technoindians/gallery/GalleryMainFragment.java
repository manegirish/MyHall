package com.technoindians.gallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.technoindians.myhall.R;

/**
 * Created by girish on 26/10/16.
 */

public class GalleryMainFragment extends Fragment {

    public static final String TAG = GalleryMainFragment.class.getSimpleName();

    private Activity activity;

    public GalleryMainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.gallery_fragment_layout, container, false);

        GridView gridView = (GridView)view. findViewById(R.id.gallery_grid_view);

        // Instance of ImageAdapter Class
        gridView.setAdapter(new ImageAdapter(activity.getApplicationContext()));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                // Sending image id to FullScreenActivity
                Intent i = new Intent(activity.getApplicationContext(), FullImageActivity.class);
                // passing array index
                i.putExtra("id", position);
                startActivity(i);
            }
        });
        return view;
    }
}

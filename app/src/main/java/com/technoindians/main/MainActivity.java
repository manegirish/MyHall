package com.technoindians.main;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.technoindians.calendar.CalendarMainFragment;
import com.technoindians.gallery.GalleryMainFragment;
import com.technoindians.library.GetInitials;
import com.technoindians.myhall.R;
import com.technoindians.phonebook.PhoneMainFragment;
import com.technoindians.profile.ProfileMainFragment;
import com.technoindians.variales.Fragments_;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by girish on 26/10/16.
 */

public class MainActivity extends ActionBarActivity {

    private static Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private int[] tabIcons = {
            R.drawable.ic_calendar,
            R.drawable.ic_gallery,
            R.drawable.ic_contact,
            R.drawable.ic_profile
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(onTabSelected);
        setupTabIcons();

        GetInitials.get("Girish Mane Dnyandeo");
    }

    TabLayout.OnTabSelectedListener onTabSelected = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            int position = tab.getPosition();
            setTitle(adapter.getPageTitle(position));
        }
        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }
        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new CalendarMainFragment(), Fragments_.CALENDAR);
        adapter.addFrag(new GalleryMainFragment(), Fragments_.GALLERY);
        adapter.addFrag(new PhoneMainFragment(), Fragments_.CONTACTS);
        adapter.addFrag(new ProfileMainFragment(), Fragments_.PROFILE);
        viewPager.setAdapter(adapter);
    }

    public static void setTitle(String title){
        toolbar.setTitle(title);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}

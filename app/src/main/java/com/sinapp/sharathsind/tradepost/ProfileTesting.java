package com.sinapp.sharathsind.tradepost;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Templates;

import Model.CustomPagerAdapter;
import Model.OffersViewPagerAdapter;
import Model.SlidingTabLayout;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HenryChiang on 15-08-04.
 */

public class ProfileTesting extends Fragment {

    ViewPager pager;
    OffersViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Item for trade","feedbacks"};
    int numbOfTabs = 2;
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = LayoutInflater.from(getActivity());
        View v = inflator.inflate(R.layout.toolbar_custom_title, null);
        TextView title = (TextView) v.findViewById(R.id.title);
        Typeface type = Typeface.createFromAsset(getResources().getAssets(), "fonts/black_jack.ttf");
        title.setText("Profile");
        title.setTextColor(getResources().getColor(R.color.ColorPrimaryDark));
        title.setTypeface(type);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setCustomView(v);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_profile, container, false);
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new OffersViewPagerAdapter(getFragmentManager(),Titles, numbOfTabs, new ChatFragment(), new ProfileFeedbackFragment());

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager)rootView.findViewById(R.id.profile_viewpager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout)rootView.findViewById(R.id.profile_sliding_tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        tabs.setBackgroundColor(getResources().getColor(R.color.darkgrey));
        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.ColorPrimary);
            }
        });

        //

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

        //


        return rootView;
    }

}


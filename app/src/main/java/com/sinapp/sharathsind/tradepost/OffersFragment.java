package com.sinapp.sharathsind.tradepost;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import Model.OffersViewPagerAdapter;
import Model.SlidingTabLayout;

/**
 * Created by HenryChiang on 15-06-27.
 */
public class OffersFragment extends Fragment {

    ViewPager pager;
    OffersViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Sent","Received"};
    int numbOfTabs = 2;
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Offers");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_offers, container, false);
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new OffersViewPagerAdapter(getFragmentManager(),Titles, numbOfTabs, new OffersTabOne(), new OffersTabTwo());

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager)rootView.findViewById(R.id.offers_viewpager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout)rootView.findViewById(R.id.sliding_tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.ColorPrimaryDark);
            }
        });

        //

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

        //


        return rootView;
    }

}

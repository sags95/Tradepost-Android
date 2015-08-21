package com.sinapp.sharathsind.tradepost;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import Model.OffersViewPagerAdapter;
import Model.SlidingTabLayout;

/**
 * Created by HenryChiang on 15-06-27.
 */
public class MyOffersFragment extends Fragment {

    ViewPager pager;
    OffersViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Received","Sent"};
    int numbOfTabs = 2;
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = LayoutInflater.from(getActivity());
        View v = inflator.inflate(R.layout.toolbar_custom_title, null);
        TextView title1 = (TextView) v.findViewById(R.id.toolbar_title1);
        TextView title2 = (TextView) v.findViewById(R.id.toolbar_title2);
        title1.setText("My Offers");
        title2.setVisibility(View.GONE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setCustomView(v);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_myoffers, container, false);
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new OffersViewPagerAdapter(getFragmentManager(),Titles, numbOfTabs, new MyOffersTabOne(), new MyOffersTabTwo());

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager)rootView.findViewById(R.id.offers_viewpager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout)rootView.findViewById(R.id.sliding_tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        tabs.setBackgroundColor(getResources().getColor(R.color.ColorPrimary));
        tabs.setTextColorState(R.color.offers_slidingtab_title_color);

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.grey);
            }
        });

        //

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

        //


        return rootView;
    }

}

package com.sinapp.sharathsind.tradepost;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import Model.OffersViewPagerAdapter;
import Model.SlidingTabLayout;

/**
 * Created by HenryChiang on 15-08-05.
 */
public class ProfileActivity extends AppCompatActivity{

    ViewPager pager;
    OffersViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Item for trade","feedbacks"};
    int numbOfTabs = 2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        adapter =  new OffersViewPagerAdapter(getSupportFragmentManager(),Titles, numbOfTabs, new ChatFragment(), new ProfileFeedbackFragment());

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager)findViewById(R.id.profile_viewpager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout)findViewById(R.id.profile_sliding_tabs);
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }
}

package com.sinapp.sharathsind.tradepost;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.util.ArrayList;

import Model.CustomTextView;
import Model.OffersViewPagerAdapter;
import Model.SlidingTabLayout;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HenryChiang on 15-08-05.
 */
public class ProfileActivity extends AppCompatActivity{

    private ViewPager pager;
    private OffersViewPagerAdapter adapter;
    private SlidingTabLayout tabs;
    private CharSequence Titles[]={"Item for trade","feedbacks"};
    private CustomTextView profileUsername;
    private CircleImageView profilePic;
    private int numbOfTabs = 2;
public static int u;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        GCMService.b=true;

        ArrayList<String> profileClicked = getIntent().getStringArrayListExtra("profileDetails");
        profilePic = (CircleImageView)findViewById(R.id.profile_userImg_placeholder);
        profileUsername = (CustomTextView)findViewById(R.id.profile_username_placeholder);

        if(getIntent().getStringExtra("caller").equals("MarketPlace")||getIntent().getStringExtra("caller").equals("SingleListingActivity")){

            //Profile Picture
            profilePic.setImageBitmap((Bitmap)getIntent().getParcelableExtra("profilePic"));

            //Profile Username
            profileClicked = getIntent().getStringArrayListExtra("itemProfileClicked");
            profileUsername.setText(profileClicked.get(1));
            u=Integer.parseInt(profileClicked.get(0));
            Log.d("USERID", profileClicked.get(0));

        }else if(getIntent().getStringExtra("caller").equals("NavigationDrawer")){

            //Profile Picture
            profilePic.setImageBitmap((Bitmap)getIntent().getParcelableExtra("profilePic"));

            //Profile Username
            profileClicked = getIntent().getStringArrayListExtra("profileDetails");
            profileUsername.setText(profileClicked.get(1));
            u=Integer.parseInt(profileClicked.get(0));
            Log.d("EMAIL", profileClicked.get(2));
            Log.d("USERID", profileClicked.get(0));
        }


        ProFileItemForTradeFragment pf  = new ProFileItemForTradeFragment();
        pf.userid=u;
//        pf.i(u);
        adapter =  new OffersViewPagerAdapter(getSupportFragmentManager(),Titles, numbOfTabs,pf , new ProfileFeedbackFragment());

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager)findViewById(R.id.profile_viewpager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout)findViewById(R.id.profile_sliding_tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        tabs.setBackgroundColor(getResources().getColor(R.color.darkgrey));
        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setTextColorState(R.color.profile_slidingtab_title_color);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.ColorPrimary);
            }
        });

        //

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

        //For eidt the profile
        if(pf.userid == Constants.userid){
            ImageView edit = (ImageView)findViewById(R.id.profile_edit);

        }



    }

    @Override
    protected void onStart() {
        super.onStart();
        GCMService.b=true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        GCMService.b=true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        GCMService.b=true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        GCMService.b=false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        GCMService.b=false;
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

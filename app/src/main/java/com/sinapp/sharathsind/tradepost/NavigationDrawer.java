package com.sinapp.sharathsind.tradepost;

/**
 * Created by HenryChiang on 15-06-25.
 */

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.net.Uri;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Model.MarketPlaceData;
import Model.NavigationDrawerCallbacks;


public class NavigationDrawer extends AppCompatActivity
        implements NavigationDrawerCallbacks, MarketPlaceFragment.TempMarketPlaceDataCallBack{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */

    FrameLayout mFrameLayoutContainer, mFrameLayoutRight;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private Fragment chatPageFrag, notiFrag;
    private FragmentManager fm;
    private ArrayList<MarketPlaceData> tempData;
    private boolean tempDataStatus=false;



    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        //mToolbar.setBackgroundColor(getResources().getColor(R.color.lightgrey));
        //tempDataStatus=false;

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.toolbar_custom_title, null);
        getSupportActionBar().setCustomView(v);



        mFrameLayoutContainer = (FrameLayout)findViewById(R.id.container);
        mFrameLayoutRight = (FrameLayout)findViewById(R.id.container_right);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer);

        chatPageFrag = new ChatPageFragment();
        notiFrag = new NotificationFragment();

        //initialize the right drawer
        fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        //transaction.add(R.id.container_right, new ChatPageFragment() , "chatPageFragment");
        transaction.add(R.id.container_right, chatPageFrag, "chatPageFragment");
        transaction.commit();

        //Set up drawer and drawer's header
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, mDrawerLayout, mToolbar, mFrameLayoutRight);


        mNavigationDrawerFragment.setUserData("User", "sample@tradepost.com", BitmapFactory.decodeResource(getResources(), R.drawable.sample_img));


        isAnyDrawerOpen(this.findViewById(android.R.id.content));
        setUpRightDrawerWidth();
        setUpLeftDrawerWidth();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0: {
                fragment = new MarketPlaceFragment();
                break;
            }
            case 1: {
                fragment = new MyOffersFragment();
                break;
            }
            case 2:{
                fragment = new MyItemsFragment();
                break;
            }
            case 3:{
                fragment = new MyFavoriteFragment();
                break;
            }
            case 4: {
                fragment = new CategoryFragment();
                break;
            }
            case 5: {
                fragment = new SettingsFragment();

                break;
            }

        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item;

        item = menu.add("Search");
        item.setIcon(R.drawable.ic_action_search);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);

        item = menu.add("Chat");
        item.setIcon(R.drawable.ic_action_chat);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);

        item = menu.add("Notification");
        item.setIcon(R.drawable.ic_action_notification);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);




        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getTitle().toString()){
            case "Chat" :{
                Toast.makeText(getApplicationContext(), "Chat?", Toast.LENGTH_SHORT).show();
                chatPageFragmentHandling();
                // Set up the drawer.
                break;
            }
            case "Notification":{
                Toast.makeText(getApplicationContext(), "Notification?", Toast.LENGTH_SHORT).show();
                notificationFragmentHandling();
                break;
            }
            case "Search":{
                Toast.makeText(getApplicationContext(), "Search?", Toast.LENGTH_SHORT).show();
                break;
            }

        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen()||mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            mDrawerLayout.closeDrawer(Gravity.RIGHT);
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }else {
            super.onBackPressed();
        }
    }

    public void setUpLeftDrawerWidth(){
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float density  = getResources().getDisplayMetrics().density;
        float dpWidth  = outMetrics.widthPixels / density;
        final ViewGroup.LayoutParams params = mNavigationDrawerFragment.getView().getLayoutParams();
        params.width = (int)(Math.round(dpWidth - 112) * density +0.5);
        mNavigationDrawerFragment.getView().setLayoutParams(params);
    }

    public void setUpRightDrawerWidth(){
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float density  = getResources().getDisplayMetrics().density;
        float dpWidth  = outMetrics.widthPixels / density;
        final ViewGroup.LayoutParams params = mFrameLayoutRight.getLayoutParams();
        params.width = (int)(Math.round(dpWidth - 56) * density +0.5);
        mFrameLayoutRight.setLayoutParams(params);
    }


    public void isAnyDrawerOpen(View v){
        v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                /*
                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                }
                */

                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mNavigationDrawerFragment.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
                } else {
                    mNavigationDrawerFragment.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);

                }

            }
        });

    }
    public void openChatPageFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mDrawerLayout.openDrawer(Gravity.RIGHT);
        if(!chatPageFrag.isAdded()){
            transaction.replace(R.id.container_right, chatPageFrag, "chatPageFragment");
            transaction.commit();
            mDrawerLayout.openDrawer(Gravity.RIGHT);
            Log.d("DEBUG", "adding chatPageFrag");
            Log.d("DEBUG", "chatPageFrag is :"+mDrawerLayout.isDrawerOpen(Gravity.RIGHT));
        }else{
            transaction.show(chatPageFrag);
            transaction.commit();
            mDrawerLayout.openDrawer(Gravity.RIGHT);
            Log.d("DEBUG","showing chatPageFrag");
        }


    }

    public void openNotificationFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mDrawerLayout.openDrawer(Gravity.RIGHT);
        if(!notiFrag.isAdded()){
            transaction.replace(R.id.container_right, notiFrag, "notificationFragment");
            transaction.commit();
            mDrawerLayout.openDrawer(Gravity.RIGHT);
            Log.d("DEBUG", "adding notiFrag");
            Log.d("DEBUG", "notiFrag is :"+mDrawerLayout.isDrawerOpen(Gravity.RIGHT));

        }else{
            transaction.show(notiFrag);
            transaction.commit();
            mDrawerLayout.openDrawer(Gravity.RIGHT);
            Log.d("DEBUG", "showing notiFrag");
        }

    }

    public void chatPageFragmentHandling(){
        if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            openChatPageFragment();

        }else {
            if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                if(chatPageFrag.isResumed()) {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                    Log.d("DEBUG", "closing chatPageFrag");
                }else{
                    openChatPageFragment();
                }
            } else {
                openChatPageFragment();
            }
        }

    }

    public void notificationFragmentHandling(){
        if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            openNotificationFragment();
        }else {
            if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                if(notiFrag.isResumed()){
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                    Log.d("DEBUG", "closing notiFrag");

                }else{
                    openNotificationFragment();
                }
            } else {
                openNotificationFragment();
            }
        }
    }
    @Override
    public void storeTempMarketPlaceData(ArrayList<MarketPlaceData> tempData){
            this.tempData=tempData;
    }

    @Override
    public boolean hasTempData() {
        return tempDataStatus;
    }

    @Override
    public void setTempDataStatus(boolean status) {
            this.tempDataStatus=status;
    }

    @Override
    public ArrayList<MarketPlaceData> getTempData() {
        return tempData;
    }
}

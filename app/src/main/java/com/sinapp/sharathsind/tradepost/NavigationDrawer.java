package com.sinapp.sharathsind.tradepost;

/**
 * Created by HenryChiang on 15-06-25.
 */

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.Toast;

import Model.NavigationDrawerCallbacks;


public class NavigationDrawer extends AppCompatActivity
        implements NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */

    FrameLayout mFrameLayoutContainer, mFrameLayoutRight;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private Fragment chatPageFrag, notiFrag;
    private FragmentManager fm;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        mFrameLayoutContainer = (FrameLayout)findViewById(R.id.container);
        mFrameLayoutRight = (FrameLayout)findViewById(R.id.container_right);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer);

        chatPageFrag = new ChatPageFragment();
        notiFrag = new NotificationFragment();
        //initialize the right drawer
        fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        //transaction.add(R.id.container_right, notiFrag, "chatPageFragment");

        transaction.add(R.id.container_right, chatPageFrag, "chatPageFragment");
        //transaction.addToBackStack("chatPageFragment");
        transaction.commit();
        //fm.executePendingTransactions();


        //Set up drawer and drawer's header
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar,mFrameLayoutRight);
        mNavigationDrawerFragment.setUserData("User", "sample@tradepost.com", BitmapFactory.decodeResource(getResources(), R.drawable.sample_img));


        isAnyDrawerOpen(this.findViewById(android.R.id.content));
        setUpDrawerWidth();
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
            case 2: {
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
        item.setIcon(R.drawable.search_icon);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);

        item = menu.add("Chat");
        item.setIcon(R.drawable.chat_icon);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);

        item = menu.add("Notification");
        item.setIcon(R.drawable.notification_icon);
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

    public void isAnyDrawerOpen(View v){
        v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                }
            }
        });

    }
    public void openChatPageFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
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

    public void setUpDrawerWidth(){
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float density  = getResources().getDisplayMetrics().density;
        float dpWidth  = outMetrics.widthPixels / density;
        final ViewGroup.LayoutParams params = mNavigationDrawerFragment.getView().getLayoutParams();
        params.width = (int)(Math.round(dpWidth - 112) * density +0.5);
        mNavigationDrawerFragment.getView().setLayoutParams(params);
    }

    public void chatPageFragmentHandling(){
        if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            openChatPageFragment();
        }else {
            if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                if(chatPageFrag.isResumed()){
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
}

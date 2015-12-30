package com.sinapp.sharathsind.tradepost;

/**
 * Created by HenryChiang on 15-06-25.
 */

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
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
import android.widget.Toast;

import com.google.android.gms.analytics.ExceptionReporter;
import com.google.android.gms.analytics.Tracker;

import java.io.File;
import java.util.ArrayList;

import Model.BadgeUtils;
import Model.MarketPlaceData;
import Model.NavigationDrawerCallbacks;
import services.OffersMessage;


public class NavigationDrawer extends AppCompatActivity
        implements NavigationDrawerCallbacks, MarketPlaceFragment.TempMarketPlaceDataCallBack{
//    public enum num{chat,individualcha,notification};

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
public static int e;
    boolean doubleBackToExitPressedOnce = false;
    FrameLayout mFrameLayoutContainer, mFrameLayoutRight;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private Fragment chatPageFrag, notiFrag;
    private FragmentManager fm;
    private ArrayList<MarketPlaceData> tempData;
    private boolean tempDataStatus=false;

    private int mChatCount = 0;
    private int mNotificationsCount = 0;

    private int selectedPosition;
    private Tracker mTracker;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        TradePost application = (TradePost) getApplication();
        mTracker = application.getDefaultTracker();
        //ctivity().getApplication()).getTracker(TrackerName.APP_TRACKER);

// Build and send exception.
        Thread.UncaughtExceptionHandler myHandler = new ExceptionReporter(
                mTracker,                                        // Currently used Tracker.
                Thread.getDefaultUncaughtExceptionHandler(),      // Current default uncaught exception handler.
                this);                                         // Context of the application.

// Make myHandler the new default uncaught exception handler.
        Thread.setDefaultUncaughtExceptionHandler(myHandler);
      Intent o=new Intent();
       o.setAction("com.tradepost.logged");
        sendBroadcast(o);
        //Context context=getApplicationContext();
//        WakefulIntentService.acquireStaticLock(context);
      //  OffersMessage.startActionFoo(this,",","");
       /*final Intent intent = new Intent(context, MyService.class);
        final PendingIntent pending = PendingIntent.getService(context, 0, intent, 0);
        final AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pending);
        long interval = 30;//milliseconds
        alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), interval, pending);*/

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
        selectedPosition=position;
        if (fragment != null) {
            displayView(fragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        /*
        MenuItem item;
        item = menu.add("Search");
        item.setIcon(R.drawable.ic_action_search);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
        */

        getMenuInflater().inflate(R.menu.main, menu);

        //search


        // Get the notifications MenuItem and
        // its LayerDrawable (layer-list)
        //chat
        MenuItem itemChat = menu.findItem(R.id.action_chat);
        itemChat.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        LayerDrawable chatIcon = (LayerDrawable) itemChat.getIcon();

        // Update LayerDrawable's BadgeDrawable
        BadgeUtils.setBadgeCount(this, chatIcon, mChatCount);


        //notification
        MenuItem itemNotification = menu.findItem(R.id.action_notification);
        itemNotification.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        LayerDrawable notifiactionIcon = (LayerDrawable) itemNotification.getIcon();

        // Update LayerDrawable's BadgeDrawable
        BadgeUtils.setBadgeCount(this, notifiactionIcon, mNotificationsCount);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        menu.findItem(R.id.search).setActionView(R.layout.activity_search);
        menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));



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

                new FetchCountTask().execute();
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
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            if (selectedPosition != 0) {
                displayView(new MarketPlaceFragment());
                selectedPosition=0;
                mNavigationDrawerFragment.selectItem(0);
            }else {
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            }
        }
    }

    private void displayView(Fragment f) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, f).commit();
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


    public void isAnyDrawerOpen(View v) {
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
    public void openChatPageFragment(Fragment f){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mDrawerLayout.openDrawer(Gravity.RIGHT);
        if(!f.isAdded()){
            transaction.replace(R.id.container_right, f, "chatPageFragment");
            transaction.commit();
            mDrawerLayout.openDrawer(Gravity.RIGHT);
            Log.d("DEBUG", "adding chatPageFrag");
            Log.d("DEBUG", "chatPageFrag is :"+mDrawerLayout.isDrawerOpen(Gravity.RIGHT));
        }else{
            transaction.show(f);
            transaction.commit();
            mDrawerLayout.openDrawer(Gravity.RIGHT);
            Log.d("DEBUG","showing chatPageFrag");
        }


    }

    public void openNotificationFragment(Fragment f){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mDrawerLayout.openDrawer(Gravity.RIGHT);
        if(!f.isAdded()){
            transaction.replace(R.id.container_right, f, "notificationFragment");
            transaction.commit();
            mDrawerLayout.openDrawer(Gravity.RIGHT);
            Log.d("DEBUG", "adding notiFrag");
            Log.d("DEBUG", "notiFrag is :"+mDrawerLayout.isDrawerOpen(Gravity.RIGHT));

        }else{
            transaction.show(f);
            transaction.commit();
            mDrawerLayout.openDrawer(Gravity.RIGHT);
            Log.d("DEBUG", "showing notiFrag");
        }

    }

    public void chatPageFragmentHandling() {
        e=0;
        if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            openChatPageFragment(chatPageFrag);

        }else {
            if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                if(chatPageFrag.isResumed()) {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                    Log.d("DEBUG", "closing chatPageFrag");
                }else{
                    openChatPageFragment(chatPageFrag);
                }
            } else {
                openChatPageFragment(chatPageFrag);
            }
        }

    }

    public void notificationFragmentHandling(){
        e=1;
        if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            openNotificationFragment(notiFrag);
        }else {
            if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                if(notiFrag.isResumed()){
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                    Log.d("DEBUG", "closing notiFrag");

                }else{
                    openNotificationFragment(notiFrag);
                }
            } else {
                openNotificationFragment(notiFrag);
            }
        }
    }

    class FetchCountTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            // example count. This is where you'd
            // query your data store for the actual count.
            return 5;
        }

        @Override
        public void onPostExecute(Integer count) {
            updateNotificationsBadge(count);
            updateChatBadge(count+4);
        }
    }

    private void updateChatBadge(int count) {
        mChatCount = count;

        // force the ActionBar to relayout its MenuItems.
        // onCreateOptionsMenu(Menu) will be called again.
        invalidateOptionsMenu();
    }

    private void updateNotificationsBadge(int count) {
        mNotificationsCount = count;

        // force the ActionBar to relayout its MenuItems.
        // onCreateOptionsMenu(Menu) will be called again.
        invalidateOptionsMenu();
    }

    @Override
    public void storeTempMarketPlaceData(ArrayList<MarketPlaceData> tempData){
            this.tempData=tempData;
    }
public BroadcastReceiver br   =new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {


    }
};
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

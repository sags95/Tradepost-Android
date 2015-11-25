package com.sinapp.sharathsind.tradepost;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.util.ArrayList;
import java.util.Vector;


import Model.BadgeUtils;
import Model.MarketPlaceData;
import Model.MarketPlaceStaggeredAdapter;
import Model.NavigationDrawerCallbacks;
import datamanager.ItemResult;
import datamanager.userdata;
import webservices.ItemWebService;
import webservices.MainWebService;

public class Search extends AppCompatActivity {
    private StaggeredGridView mGridView;
    private RecyclerView mRecyclerView;
    private MarketPlaceStaggeredAdapter stagAdapter2;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout, mSwipeRefreshLayoutEmpty;
    private View rootView;
public String type,query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dele);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.toolbar_custom_title, null);
        TextView title1 = (TextView) v.findViewById(R.id.toolbar_title1);
        TextView title2 = (TextView) v.findViewById(R.id.toolbar_title2);
        title1.setText("Trade");
        title2.setVisibility(View.VISIBLE);
        rootView = findViewById(R.id.i);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.ColorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


            }
        });

        //
        mSwipeRefreshLayoutEmpty = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout_empty);


        /*
        //StaggeredGridViewTesting
        //For Testing Only
        String[] tempTags= {
                "hello",
                "laptop",
                "android",
                "iphone",
                "whatever"
        };
        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.recylcer_view);
        mStaggeredAdapterTest = new StaggeredAdapterTest(getActivity(),MarketPlaceData.generateSampleDataTest(),listingItemClickListener,tempTags);
        mRecyclerView.setAdapter(mStaggeredAdapterTest);
        applyStaggeredGridLayoutManager();
        */


        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recylcer_view);
        mRecyclerView.setHasFixedSize(true);

        applyStaggeredGridLayoutManager();
        this.getSupportActionBar().setCustomView(v);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
             query = intent.getStringExtra(SearchManager.QUERY);
    type="serach";
        }
else{
        if(intent.getStringExtra("type").equals("tags"))
        {
            query = intent.getStringExtra(SearchManager.QUERY);
type="tags";
        }
        else{
            query = intent.getStringExtra(SearchManager.QUERY);
            type="cat";

        }

        }
        TagsSearch t=new   TagsSearch(query,this,type);
        t.execute();

    }

    private void applyStaggeredGridLayoutManager() {
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
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


        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        menu.findItem(R.id.search).setActionView(R.layout.activity_search);
        menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));


        return super.onCreateOptionsMenu(menu);
    }

    public void openChatPageFragment(Fragment f) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mDrawerLayout.openDrawer(Gravity.RIGHT);
        if (!f.isAdded()) {
            transaction.replace(R.id.container_right, f, "chatPageFragment");
            transaction.commit();
            mDrawerLayout.openDrawer(Gravity.RIGHT);
            Log.d("DEBUG", "adding chatPageFrag");
            Log.d("DEBUG", "chatPageFrag is :" + mDrawerLayout.isDrawerOpen(Gravity.RIGHT));
        } else {
            transaction.show(f);
            transaction.commit();
            mDrawerLayout.openDrawer(Gravity.RIGHT);
            Log.d("DEBUG", "showing chatPageFrag");
        }


    }

    public void openNotificationFragment(Fragment f) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mDrawerLayout.openDrawer(Gravity.RIGHT);
        if (!f.isAdded()) {
            transaction.replace(R.id.container_right, f, "notificationFragment");
            transaction.commit();
            mDrawerLayout.openDrawer(Gravity.RIGHT);
            Log.d("DEBUG", "adding notiFrag");
            Log.d("DEBUG", "notiFrag is :" + mDrawerLayout.isDrawerOpen(Gravity.RIGHT));

        } else {
            transaction.show(f);
            transaction.commit();
            mDrawerLayout.openDrawer(Gravity.RIGHT);
            Log.d("DEBUG", "showing notiFrag");
        }

    }

    public void chatPageFragmentHandling() {
        // e=0;
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            openChatPageFragment(chatPageFrag);

        } else {
            if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                if (chatPageFrag.isResumed()) {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                    Log.d("DEBUG", "closing chatPageFrag");
                } else {
                    openChatPageFragment(chatPageFrag);
                }
            } else {
                openChatPageFragment(chatPageFrag);
            }
        }

    }

    private Fragment chatPageFrag, notiFrag;
    private DrawerLayout mDrawerLayout;

    public void notificationFragmentHandling() {
        //  e=1;
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            openNotificationFragment(notiFrag);
        } else {
            if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                if (notiFrag.isResumed()) {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                    Log.d("DEBUG", "closing notiFrag");

                } else {
                    openNotificationFragment(notiFrag);
                }
            } else {
                openNotificationFragment(notiFrag);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getTitle().toString()) {
            case "Chat": {
                Toast.makeText(getApplicationContext(), "Chat?", Toast.LENGTH_SHORT).show();
                chatPageFragmentHandling();
                // Set up the drawer.
                break;
            }
            case "Notification": {
                Toast.makeText(getApplicationContext(), "Notification?", Toast.LENGTH_SHORT).show();
                notificationFragmentHandling();
                break;
            }
            case "Search": {
                Intent intent = getIntent();
                if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
                    String query = intent.getStringExtra(SearchManager.QUERY);
                    //     Toast.makeText(getApplicationContext(), query, Toast.LENGTH_LONG).show();

                }

                //    new FetchCountTask().execute();
                break;
            }

        }

        return super.onOptionsItemSelected(item);
    }
    public View.OnClickListener listingItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("child position", String.valueOf(mRecyclerView.getChildLayoutPosition(v)));



            Intent i = new Intent(Search.this, SingleListingActivity.class);
            ArrayList<String> clickedItemDetails = new ArrayList<>();

            i.putExtra("caller","MarketPlace");

            clickedItemDetails.add(0, String.valueOf(mRecyclerView.getChildLayoutPosition(v)));
            i.putStringArrayListExtra("itemClicked", clickedItemDetails);

            //profile picture
            BitmapDrawable bitmapDrawable = (BitmapDrawable)((ImageView) v.findViewById(R.id.pro_img)).getDrawable();
            Bitmap b =bitmapDrawable.getBitmap();
            i.putExtra("profilePic",b);

            startActivity(i);
        }
    };

    class ItemSearch extends AsyncTask<ArrayList<MarketPlaceData>, String, ArrayList<MarketPlaceData>> {
       String tag;





        ItemSearch(String a, Context context) {
            super();
            tag = a;


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayoutEmpty.setRefreshing(true);
                }
            });
        }

        @Override
        protected void onPostExecute(ArrayList<MarketPlaceData> s) {
            super.onPostExecute(s);
            MarketPlaceStaggeredAdapter m = new MarketPlaceStaggeredAdapter();
            stagAdapter2 = new MarketPlaceStaggeredAdapter(Search.this,s,listingItemClickListener);
            mRecyclerView.setAdapter(stagAdapter2);
            mSwipeRefreshLayoutEmpty.setRefreshing(false);
            mSwipeRefreshLayoutEmpty.setVisibility(View.GONE);
//        mCallback.storeTempMarketPlaceData(s);
            mSwipeRefreshLayout.setRefreshing(false);
        }

        @Override
        protected ArrayList<MarketPlaceData> doInBackground(ArrayList<MarketPlaceData>... params) {
            ArrayList<MarketPlaceData> m = new ArrayList<>();
            SoapObject obje = new SoapObject("http://webser/", "SearchBy");
            obje.addProperty("cat", tag);
            obje.addProperty("longi", userdata.mylocation.Longitude);
            obje.addProperty("lat", userdata.mylocation.latitude);
            if (userdata.radius == 0)
                userdata.radius = 25;
            obje.addProperty("rad", userdata.radius);
            Vector vector = MainWebService.getMsg1(obje, "http://73.37.238.238:8084/TDserverWeb/Search?wsdl", "http://webser/Search/SearchByRequest");
            if (vector != null) {
                for (Object a : vector) {
                    ItemResult ir = ItemWebService.getItem(Integer.parseInt(((SoapPrimitive) a).getValue().toString()));
                    obje = new SoapObject("http://webser/", "getusername");
                    obje.addProperty("name", ir.item.getUserid());
                    SoapPrimitive soapPrimitive = MainWebService.getretryMsg(obje, "http://73.37.238.238:8084/TDserverWeb/getUserName?wsdl", "http://webser/getUserName/getusernameRequest", 0);
                    MarketPlaceData data = new MarketPlaceData();
                    ir.username = soapPrimitive.getValue().toString();
                    data.itemImage = "http://73.37.238.238:8084/TDserverWeb/images/items/" + ir.item.getItemid() + "/" + ir.images[0];
                    data.proUsername = ir.username;
                    data.itemTitle = ir.item.getItemname();
                    data.userid = ir.item.getUserid();
                    data.image = new String[ir.images.length];


                    int ij = 0;
                    for (String s : ir.images) {
                        data.image[ij] = "http://73.37.238.238:8084/TDserverWeb/images/items/" + ir.item.getItemid() + "/" + s;

                        ij++;
                    }

                    data.item = ir;
                    Cursor c = Constants.db.rawQuery("select * from fav where itemid=" + data.item.item.getItemid(), null);
                    if (c.getCount() > 0)
                        data.isFav = true;
                    else
                        data.isFav = false;

                    m.add(data);

                }


            }
            return m;
        }
    }
    public class TagsSearch extends AsyncTask<ArrayList<MarketPlaceData>, String, ArrayList<MarketPlaceData>> {
        String tag;
        Context c;
String type;
        public TagsSearch(String a, Context context,String type)
        {
            super();
            tag=a;
            c=context;
this.type=type;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<MarketPlaceData> s) {
            super.onPostExecute(s);
            MarketPlaceStaggeredAdapter m = new MarketPlaceStaggeredAdapter();
            stagAdapter2 = new MarketPlaceStaggeredAdapter(Search.this,s,listingItemClickListener);
            mRecyclerView.setAdapter(stagAdapter2);
            mSwipeRefreshLayoutEmpty.setRefreshing(false);
            mSwipeRefreshLayoutEmpty.setVisibility(View.GONE);
//        mCallback.storeTempMarketPlaceData(s);
            mSwipeRefreshLayout.setRefreshing(false);

        }

        @Override
        protected ArrayList<MarketPlaceData> doInBackground(ArrayList<MarketPlaceData>... params) {
            ArrayList<MarketPlaceData> m = new ArrayList<>();

            Vector vector=null;
            if(type.equals("tags")) {
                SoapObject obje =new SoapObject("http://webser/","SearchByTags");
                obje.addProperty("cat", tag);
                obje.addProperty("longi", userdata.mylocation.Longitude);
                obje.addProperty("lat", userdata.mylocation.latitude);
                obje.addProperty("rad", userdata.radius);
                vector = MainWebService.getMsg1(obje, "http://73.37.238.238:8084/TDserverWeb/Search?wsdl", "http://webser/Search/SearchByTagsRequest");

            }
                else if(type.equals("cat")) {
                SoapObject obje =new SoapObject("http://webser/","SearchByC");
                obje.addProperty("cat", tag);
                obje.addProperty("longi", userdata.mylocation.Longitude);
                obje.addProperty("lat", userdata.mylocation.latitude);
                obje.addProperty("rad", userdata.radius);
                vector = MainWebService.getMsg1(obje, "http://73.37.238.238:8084/TDserverWeb/Search?wsdl", "http://webser/Search/SearchByCRequest");
            }
                else {
                SoapObject obje =new SoapObject("http://webser/","SearchBy");
                obje.addProperty("cat", tag);
                obje.addProperty("longi", userdata.mylocation.Longitude);
                obje.addProperty("lat", userdata.mylocation.latitude);
                obje.addProperty("rad", userdata.radius);
                vector = MainWebService.getMsg1(obje, "http://73.37.238.238:8084/TDserverWeb/Search?wsdl", "http://webser/Search/SearchByRequest");
            }
            SoapObject obje;
            if(vector!=null)
            {

                for(Object a : vector)
                {
                    ItemResult ir= ItemWebService.getItem(Integer.parseInt(((SoapPrimitive) a).getValue().toString()));
                    obje = new SoapObject("http://webser/", "getusername");
                    obje.addProperty("name", ir.item.getUserid());
                    SoapPrimitive soapPrimitive = MainWebService.getretryMsg(obje, "http://73.37.238.238:8084/TDserverWeb/getUserName?wsdl", "http://webser/getUserName/getusernameRequest", 0);
                    MarketPlaceData data = new MarketPlaceData();
                    ir.username = soapPrimitive.getValue().toString();
                    data.itemImage = "http://73.37.238.238:8084/TDserverWeb/images/items/" + ir.item.getItemid() + "/" + ir.images[0];
                    data.proUsername = ir.username;
                    data.itemTitle = ir.item.getItemname();
                    data.userid = ir.item.getUserid();
                    data.image = new String[ir.images.length];


                    int ij = 0;
                    for (String s : ir.images) {
                        data.image[ij] = "http://73.37.238.238:8084/TDserverWeb/images/items/" + ir.item.getItemid() + "/" + s;

                        ij++;
                    }

                    data.item = ir;
                    Cursor c = Constants.db.rawQuery("select * from fav where itemid=" + data.item.item.getItemid(), null);
                    if (c.getCount() > 0)
                        data.isFav = true;
                    else
                        data.isFav = false;

                    m.add(data);

                }


            }
            return m;
        }
    }

}

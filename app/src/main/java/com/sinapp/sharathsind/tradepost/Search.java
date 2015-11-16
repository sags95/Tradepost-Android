package com.sinapp.sharathsind.tradepost;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.drawable.LayerDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;

import Model.BadgeUtils;
import Model.MarketPlaceStaggeredAdapter;

public class Search extends AppCompatActivity {
    private StaggeredGridView mGridView;
    private RecyclerView mRecyclerView;
    private MarketPlaceStaggeredAdapter stagAdapter2;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout,mSwipeRefreshLayoutEmpty;
    private View rootView;
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
        rootView=findViewById(R.id.i);
        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.ColorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


            }
        });

        //
        mSwipeRefreshLayoutEmpty = (SwipeRefreshLayout)rootView.findViewById(R.id.activity_main_swipe_refresh_layout_empty);


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





        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.recylcer_view);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getTitle().toString()){
            case "Chat" :{
                Toast.makeText(getApplicationContext(), "Chat?", Toast.LENGTH_SHORT).show();
         //       chatPageFragmentHandling();
                // Set up the drawer.
                break;
            }
            case "Notification":{
                Toast.makeText(getApplicationContext(), "Notification?", Toast.LENGTH_SHORT).show();
           //     notificationFragmentHandling();
                break;
            }
            case "Search":{

            //    new FetchCountTask().execute();
                break;
            }

        }

        return super.onOptionsItemSelected(item);
    }



    }


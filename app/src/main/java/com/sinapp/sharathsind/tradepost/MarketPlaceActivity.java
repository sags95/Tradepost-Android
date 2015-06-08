package com.sinapp.sharathsind.tradepost;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

import Model.MarketPlaceData;
import Model.MarketPlaceDataAdapter;

/**
 * Created by HenryChiang on 15-05-23.
 */
public class MarketPlaceActivity extends NewToolBar {

    private StaggeredGridView mGridView;
    private MarketPlaceDataAdapter mAdapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_marketplace);

        mGridView = (StaggeredGridView) findViewById(R.id.grid_view);

        mAdapter = new MarketPlaceDataAdapter(this, R.layout.list_item_staggeredgrid, MarketPlaceData.generateSampleData());

        mGridView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.attachToListView(mGridView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListingProcessActivity.class));

            }
        });





    }



}

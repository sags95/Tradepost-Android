package com.sinapp.sharathsind.tradepost;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import Model.CustomPagerAdapter;
import Model.MarketPlaceData;
import Model.MarketPlaceDataAdapter;
import Model.StaggeredAdapter;
import Model.StaggeredAdapter2;

/**
 * Created by HenryChiang on 15-06-25.
 */
public class MarketPlaceFragment  extends Fragment implements ObservableScrollViewCallbacks {

    private MarketPlaceDataAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private StaggeredAdapter stagAdapter;
    private StaggeredAdapter2 stagAdapter2;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private View rootView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private ViewPager mViewPager;
    private final static int NUM_IMAGES_MP = 4;
    private List<ImageView> dots;
    private int[] imageResources = {
            R.drawable.sample_img,
            R.drawable.sample_img2,
            R.drawable.sample_img3,
            R.drawable.sample_img
    };

    private ObservableRecyclerView recyclerview;



    public MarketPlaceFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Tradepost");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_marketplace, container, false);

        /*
        recyclerview = (ObservableRecyclerView) rootView.findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        recyclerview.setScrollViewCallbacks(this);
        stagAdapter2 = new StaggeredAdapter2(MarketPlaceData.generateSampleData(), listingItemClickListener);
        recyclerview.setAdapter(stagAdapter2);
        applyStaggeredGridLayoutManager();
        */
        //Image Pager


        /*
        mCustomPagerAdapter = new CustomPagerAdapter(getActivity());
        mViewPager = (ViewPager)rootView.findViewById(R.id.pager_marketplace);
        mViewPager.setAdapter(mCustomPagerAdapter);
        */


        //SwipeToRefresh
        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.ColorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("SwipeToRefresh","Refreshing");
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        //StaggeredGridView
        /*mRecyclerView = (RecyclerView)rootView.findViewById(R.id.recylcer_view);
        mRecyclerView.setHasFixedSize(true);
        stagAdapter = new StaggeredAdapter(getActivity().getApplicationContext(),MarketPlaceData.generateSampleData(),listingItemClickListener, imageResources);
        mRecyclerView.setAdapter(stagAdapter);
        applyStaggeredGridLayoutManager();
        */

        //StaggeredGridView2

        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.recylcer_view);
        mRecyclerView.setHasFixedSize(true);
        stagAdapter2 = new StaggeredAdapter2(MarketPlaceData.generateSampleData(),listingItemClickListener);
        mRecyclerView.setAdapter(stagAdapter2);
        applyStaggeredGridLayoutManager();



        //Floating Action Button
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(fabOnClickListener);
        fab.attachToRecyclerView(mRecyclerView);

        //Like Button
        ImageView likeBtn = (ImageView) rootView.findViewById(R.id.image_like_btn);
        //likeBtn.setOnClickListener(likeOnClickListener);

        //Location
        View includeView = (View)rootView.findViewById(R.id.marketplace_header);
        includeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog();
            }
        });



        return rootView;

    }


    private void applyStaggeredGridLayoutManager() {
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
    }

    public View.OnClickListener fabOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getActivity(), ListingProcessActivity.class));
        }
    };

    public View.OnClickListener likeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        }
    };

    public View.OnClickListener listingItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("child position", String.valueOf(mRecyclerView.getChildPosition(v)));
            Intent i = new Intent(getActivity(), SingleListingActivity.class);
            ArrayList<String> clickedItemDetails = new ArrayList<>();
            TextView itemTitle = (TextView) rootView.findViewById(R.id.item_title);
            clickedItemDetails.add(0, String.valueOf(mRecyclerView.getChildPosition(v)));
            clickedItemDetails.add(1, itemTitle.getText().toString());

            //Log.d("item details", "item position: " + clickedItemDetails.get(0));
            //Log.d("item details", "item title: " + clickedItemDetails.get(1));

            i.putStringArrayListExtra("itemClicked", clickedItemDetails);
            startActivity(i);
        }
    };

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll,
                                boolean dragging) {
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();

    }

    public void customDialog(){
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView( R.layout.location_dialog );
        dialog.setTitle( "Dialog Details" );

        dialog.show( );

    }


}
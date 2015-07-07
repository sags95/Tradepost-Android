package com.sinapp.sharathsind.tradepost;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import Model.CustomPagerAdapter;
import Model.MarketPlaceData;
import Model.MarketPlaceDataAdapter;
import Model.StaggeredAdapter;

/**
 * Created by HenryChiang on 15-06-25.
 */
public class MarketPlaceFragment  extends Fragment{

    private MarketPlaceDataAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private StaggeredAdapter stagAdapter;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private View rootView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private CustomPagerAdapter mCustomPagerAdapter;
    private ViewPager mViewPager;
    private final static int NUM_IMAGES_MP = 6;
    private List<ImageView> dots;

    public MarketPlaceFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Tradepost");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_marketplace, container, false);



        //Image Pager
        //image slider viewer

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
        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.recylcer_view);
        mRecyclerView.setHasFixedSize(true);
        stagAdapter = new StaggeredAdapter(MarketPlaceData.generateSampleData(),listingItemClickListener);
        mRecyclerView.setAdapter(stagAdapter);
        applyStaggeredGridLayoutManager();


        //Floating Action Button
        FloatingActionButton fab = (FloatingActionButton)rootView.findViewById(R.id.fab);
        fab.setOnClickListener(fabOnClickListener);
        fab.attachToRecyclerView(mRecyclerView);

        //Like Button
        ImageView likeBtn = (ImageView)rootView.findViewById(R.id.image_like_btn);
        //likeBtn.setOnClickListener(likeOnClickListener);



        return rootView;

    }




    private void applyStaggeredGridLayoutManager(){
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
            TextView itemTitle = (TextView)rootView.findViewById(R.id.item_title);
            clickedItemDetails.add(0,String.valueOf(mRecyclerView.getChildPosition(v)));
            clickedItemDetails.add(1,itemTitle.getText().toString());

            //Log.d("item details", "item position: " + clickedItemDetails.get(0));
            //Log.d("item details", "item title: " + clickedItemDetails.get(1));

            i.putStringArrayListExtra("itemClicked", clickedItemDetails);
            startActivity(i);
        }
    };

    public void addDots() {
        dots = new ArrayList<>();
        LinearLayout dotsLayout = (LinearLayout)rootView.findViewById(R.id.dots);

        for(int i = 0; i < NUM_IMAGES_MP; i++) {
            ImageView dot = new ImageView(getActivity().getApplicationContext());
            if(i==0){
                dot.setImageDrawable(getResources().getDrawable(R.drawable.pager_dot_selected));
            }else {
                dot.setImageDrawable(getResources().getDrawable(R.drawable.pager_dot_not_selected));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(3,0,3,0);

            dotsLayout.addView(dot,params);

            // dotsLayout.addView(dot);
            dots.add(dot);
        }

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                selectDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void selectDot(int idx) {
        Resources res = getResources();
        for(int i = 0; i < NUM_IMAGES_MP; i++) {
            int drawableId = (i==idx)?(R.drawable.pager_dot_selected):(R.drawable.pager_dot_not_selected);
            Drawable drawable = res.getDrawable(drawableId);
            dots.get(i).setImageDrawable(drawable);
        }
    }


}

package com.sinapp.sharathsind.tradepost;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

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


    public MarketPlaceFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_marketplace, container, false);

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
}

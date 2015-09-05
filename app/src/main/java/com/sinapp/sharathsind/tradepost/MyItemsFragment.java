package com.sinapp.sharathsind.tradepost;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Model.CustomLinearLayoutManager;
import Model.DividerItemDecoration;
import Model.EmptyRecyclerView;
import Model.MarketPlaceStaggeredAdapter;
import Model.MyItems;
import Model.MyItemsAdapter;
import Model.OfferProcessItem;
import Model.RecyclerViewOnClickListener;
import Model.Variables;
import datamanager.ItemResult;
import datamanager.userdata;

/**
 * Created by HenryChiang on 15-08-10.
 */
public class MyItemsFragment extends Fragment {

    private View rootView,emptyView;
    private EmptyRecyclerView mRecyclerView;
    private MyItemsAdapter myItemsAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private CustomLinearLayoutManager mLayoutManager;
    private ArrayList<MyItems>  myItems  = new ArrayList<MyItems>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = LayoutInflater.from(getActivity());
        View v = inflator.inflate(R.layout.toolbar_custom_title, null);
        TextView title1 = (TextView) v.findViewById(R.id.toolbar_title1);
        TextView title2 = (TextView) v.findViewById(R.id.toolbar_title2);
        title1.setText("My Items");
        title2.setVisibility(View.GONE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setCustomView(v);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_items, container, false);
        emptyView = rootView.findViewById(R.id.myItems_emptyView);
        mRecyclerView = (EmptyRecyclerView)rootView.findViewById(R.id.myItems_recyclerview);
        if(userdata.i!=null) {
            for (int i = 0; i < userdata.i.size(); i++) {
                MyItems myItem = new MyItems(userdata.i.get(i).item.getItemname(), userdata.i.get(i).item.getItemid(), userdata.userid);
                myItems.add(myItem);
            }
        }else{
            myItems = null;
        }

        //SwipeToRefresh
        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.myItems_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.ColorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("SwipeToRefresh", "Refreshing");
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (mRecyclerView == null || mRecyclerView.getChildCount() == 0) ?
                                0 : mRecyclerView.getChildAt(0).getTop();
                mSwipeRefreshLayout.setEnabled(mLayoutManager.findFirstVisibleItemPosition() == 0 && topRowVerticalPosition >= 0);
                super.onScrolled(recyclerView, dx, dy);
            }
        });



        myItemsAdapter = new MyItemsAdapter(getActivity().getApplicationContext(),myItems,myItemClickListener);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setSwipeRefreshLayout(mSwipeRefreshLayout);
        mRecyclerView.setEmptyView(emptyView);
        mRecyclerView.addOnItemTouchListener(
                new RecyclerViewOnClickListener(getActivity(), new RecyclerViewOnClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }
                })
        );

        mRecyclerView.setAdapter(myItemsAdapter);
        applyLinearLayoutManager();


        return rootView;
    }

    private void applyLinearLayoutManager(){
        mLayoutManager = new CustomLinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    public View.OnClickListener myItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("child position", String.valueOf(mRecyclerView.getChildLayoutPosition(v)));


            Intent i = new Intent(getActivity(), SingleListingActivity.class);
            ArrayList<String> clickedItemDetails = new ArrayList<String>();

            i.putExtra("caller", "MyItem");

            clickedItemDetails.add(0, String.valueOf(userdata.i.get(mRecyclerView.getChildLayoutPosition(v)).item.getItemid()));
            clickedItemDetails.add(1, userdata.i.get(mRecyclerView.getChildLayoutPosition(v)).item.getItemname());
            clickedItemDetails.add(2, userdata.i.get(mRecyclerView.getChildLayoutPosition(v)).item.getUsername());
            clickedItemDetails.add(3, userdata.i.get(mRecyclerView.getChildLayoutPosition(v)).item.getDescription());
            clickedItemDetails.add(4, MarketPlaceStaggeredAdapter.daysBetween(userdata.i.get(mRecyclerView.getChildLayoutPosition(v)).item.getDateadded()));
            clickedItemDetails.add(5, String.valueOf(userdata.i.get(mRecyclerView.getChildLayoutPosition(v)).item.getCon()));
            clickedItemDetails.add(6,userdata.i.get(mRecyclerView.getChildLayoutPosition(v)).item.getCategory());

            i.putStringArrayListExtra("myItemClicked", clickedItemDetails);

            i.putExtra("latitude", userdata.i.get(mRecyclerView.getChildLayoutPosition(v)).item.getLatitude());
            i.putExtra("longitude",userdata.i.get(mRecyclerView.getChildLayoutPosition(v)).item.getLongtitude());

            i.putExtra("itemImages", userdata.i.get(mRecyclerView.getChildLayoutPosition(v)).images);
            i.putExtra("itemTags",userdata.i.get(mRecyclerView.getChildLayoutPosition(v)).tags);

            //profile picture
            Bitmap b = Variables.getProfilepic();
            i.putExtra("profilePic",b);

            startActivity(i);
        }
    };
}

package com.sinapp.sharathsind.tradepost;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import Model.CustomLinearLayoutManager;
import Model.DividerItemDecoration;
import Model.EmptyRecyclerView;
import Model.MyOffersAdapter;
import Model.MyOffersItem;


/**
 * Created by HenryChiang on 15-06-27.
 */
public class MyOffersTabOne extends Fragment {

    private View rootView,emptyView;
    private EmptyRecyclerView mRecyclerView;
    private MyOffersAdapter mMyOffersAdapter;
    private CustomLinearLayoutManager mLayoutManager;
    private BitmapFactory.Options options;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.myoffers_pager_1, container, false);
        emptyView = rootView.findViewById(R.id.myoffers_received_emptyView);
        mRecyclerView = (EmptyRecyclerView)rootView.findViewById(R.id.myoffers_received);

        //SwipeToRefresh
        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.myoffers_received_swipe_refresh_layout);
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


        options= new BitmapFactory.Options();
        options.inJustDecodeBounds =true;
        BitmapFactory.decodeResource(getResources(),R.drawable.sample_img,options);


        final List<MyOffersItem> myOffersItems = addItem();



        mMyOffersAdapter = new MyOffersAdapter(myOffersItems, getActivity().getApplicationContext());
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setSwipeRefreshLayout(mSwipeRefreshLayout);
        mRecyclerView.setEmptyView(emptyView);
        mRecyclerView.setAdapter(mMyOffersAdapter);
        applyLinearLayoutManager();

        return rootView;
    }

    public List<MyOffersItem> addItem() {
        List<MyOffersItem> items = new ArrayList<MyOffersItem>();

        items.add(new MyOffersItem("Title1",BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.sample_img5),1));
        items.add(new MyOffersItem("Title2",BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.sample_img6),2));
        items.add(new MyOffersItem("Long long long long title3",BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.sample_img3),3));
        items.add(new MyOffersItem("Long long long long long long long long title4",BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.sample_img2),4));
        items.add(new MyOffersItem("Long long long long long long long long title5",BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.sample_img2),5));
        items.add(new MyOffersItem("Long long long long long long long long title6",BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.sample_img2),5));
        items.add(new MyOffersItem("Long long long long long long long long title7",BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.sample_img2),5));



        return items;
    }

    private void applyLinearLayoutManager() {
        mLayoutManager = new CustomLinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

}





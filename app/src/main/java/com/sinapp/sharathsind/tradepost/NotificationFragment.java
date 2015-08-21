package com.sinapp.sharathsind.tradepost;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import Model.ChatPageItem;
import Model.CustomLinearLayoutManager;
import Model.DividerItemDecoration;
import Model.EmptyRecyclerView;
import Model.NotificationAdapter;
import Model.NotificationItem;
import Model.RecyclerViewOnClickListener;

/**
 * Created by HenryChiang on 15-07-01.
 */
public class NotificationFragment extends Fragment {

    private View rootView,emptyView;
    private EmptyRecyclerView mRecyclerView;
    private CustomLinearLayoutManager mLayoutManager;
    private NotificationAdapter mNotificationAdapter;
    private Fragment chatFrag;
    private ChatPageItem item;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_notification, container, false);
        emptyView = rootView.findViewById(R.id.noti_emptyView);
        mRecyclerView = (EmptyRecyclerView)rootView.findViewById(R.id.noti_list);

        //SwipeToRefresh
        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.noti_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.ColorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("SwipeToRefresh", "Refreshing");
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });



        final List<NotificationItem> notiItem = addItem("Longusername", 0);
        //final List<NotificationItem> notiItem = null;

        mNotificationAdapter = new NotificationAdapter(notiItem);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setEmptyView(emptyView);

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

        mRecyclerView.addOnItemTouchListener(
                new RecyclerViewOnClickListener(getActivity(), new RecyclerViewOnClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.d("viewtype",view.getTag().toString());
                        if(view.getTag().toString().equals("0")){

                        }
                    }
                })
        );

        mRecyclerView.setAdapter(mNotificationAdapter);
        applyLinearLayoutManager();

        return rootView;
    }

    public List<NotificationItem> addItem(String userName, int viewType) {
        List<NotificationItem> items = new ArrayList<NotificationItem>();
        items.add(new NotificationItem(userName, viewType));
        items.add(new NotificationItem("User", 1));
        items.add(new NotificationItem("UserABC",2));
        items.add(new NotificationItem("UserLong",3));
        items.add(new NotificationItem("UserXYZ",4));



        return items;
    }
    private void applyLinearLayoutManager(){
        mLayoutManager = new CustomLinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of NotificationFragment");
        super.onResume();
    }


    @Override
    public void onPause(){
        Log.e("DEBUG", "onPause of NotificationFragment");
        super.onPause();
    }

    @Override
    public void onStop(){
        Log.e("DEBUG", "onStop of NotificationFragment");
        super.onStop();
    }

    @Override
    public void onDestroyView(){
        Log.e("DEBUG", "onDestroyView of NotificationFragment");
        super.onDestroyView();
    }

    @Override
    public void onDestroy(){
        Log.e("DEBUG", "onDestroy of NotificationFragment");
        super.onDestroy();

    }



}
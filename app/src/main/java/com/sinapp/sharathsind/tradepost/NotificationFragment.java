package com.sinapp.sharathsind.tradepost;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Model.ChatPageAdapter;
import Model.ChatPageItem;
import Model.DividerItemDecoration;
import Model.NotificationAdapter;
import Model.NotificationItem;
import Model.RecyclerViewOnClickListener;

/**
 * Created by HenryChiang on 15-07-01.
 */
public class NotificationFragment extends Fragment {

    private View rootView;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private NotificationAdapter mNotificationAdapter;
    private Fragment chatFrag;
    private ChatPageItem item;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_notification, container, false);
        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.noti_list);


        final List<NotificationItem> notiItem = addItem("Longusername", 0);
        mNotificationAdapter = new NotificationAdapter(notiItem);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);

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
        mLayoutManager = new LinearLayoutManager(getActivity());
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
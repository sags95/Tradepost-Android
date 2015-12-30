package com.sinapp.sharathsind.tradepost;


import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;

import Model.ChatPageItem;
import Model.CustomLinearLayoutManager;
import Model.DividerItemDecoration;
import Model.EmptyRecyclerView;
import Model.NotificationAdapter;
import Model.NotificationItem;
import Model.RecyclerViewOnClickListener;
import datamanager.userdata;
import webservices.MainWebService;

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
new AsyncTask<List<NotificationItem>,List<NotificationItem>,List<NotificationItem>>()
        {
    @Override
    protected void onPostExecute(List<NotificationItem> notificationItems) {
        super.onPostExecute(notificationItems);
        mNotificationAdapter = new NotificationAdapter(notificationItems);
        mRecyclerView.setAdapter(mNotificationAdapter);


    }

    @Override
    protected List<NotificationItem> doInBackground(List<NotificationItem>... params) {

        return addItem("Longusername", 0);
    }
}.execute();


        //final List<NotificationItem> notiItem = null;


        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setSwipeRefreshLayout(mSwipeRefreshLayout);
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
                        Log.d("viewtype", view.getTag().toString());
                        if (view.getTag().toString().equals("0")) {

                            int offerid = offer.get(position);
                            SoapObject obje1 = new SoapObject("http://webser/", "getOffer");
                            obje1.addProperty("offerid", offerid);
                            KvmSerializable s = MainWebService.getMsg2(obje1, "http://73.37.238.238:8084/TDserverWeb/OfferWebService?wsdl", "http://webser/OfferWebService/getOfferRequest");
                            Intent i = new Intent(getActivity(), notificationoffertesting.class);
                            i.putExtra("offerid", offerid);
                            i.putExtra("itemname", (((SoapObject) s).getProperty("itemname")).toString());
                            startActivity(i);

                        }

                    }
                })
        );


        applyLinearLayoutManager();

        return rootView;
    }
ArrayList<Integer> offer;
    public List<NotificationItem> addItem(String userName, int viewType) {
        List<NotificationItem> items = new ArrayList<NotificationItem>();
        offer=new ArrayList<>();
        SQLiteDatabase db=getActivity().openOrCreateDatabase("tradepostdb.db", getActivity().MODE_PRIVATE, null);
    //    SQLiteDatabase db=getActivity().openOrCreateDatabase("tradepostdb.db", getActivity().MODE_PRIVATE, null);
        Cursor c=db.rawQuery("select * from notifications",null);
        c.moveToFirst();
        while(!c.isAfterLast())
        {
            String username=c.getString(c.getColumnIndex("msg"));
       int type=c.getInt(c.getColumnIndex("type"));
            offer.add(c.getInt(c.getColumnIndex("offerid")));
items.add(new NotificationItem(username,type));
c.moveToNext();
        }




c.close();
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
package com.sinapp.sharathsind.tradepost;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Model.DividerItemDecoration;
import Model.EmptyRecyclerView;
import Model.MyOffersAdapter;
import Model.MyOffersItem;
import datamanager.ItemResult;
import datamanager.userdata;
import webservices.ItemWebService;

/**
 * Created by HenryChiang on 15-06-27.
 */
public class MyOffersTabTwo extends Fragment {

    private View rootView,emptyView;
    private EmptyRecyclerView mRecyclerView;
    private MyOffersAdapter mMyOffersAdapter;
    private android.support.v7.widget.LinearLayoutManager mLayoutManager;
    private BitmapFactory.Options options;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.myoffers_pager_2, container, false);
        emptyView = rootView.findViewById(R.id.myoffers_sent_emptyView);
        mRecyclerView = (EmptyRecyclerView)rootView.findViewById(R.id.myoffers_sent);

        //SwipeToRefresh
        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.myoffers_sent_swipe_refresh_layout);
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

        /*
        final List<MyOffersItem> myOffersItems =
                addItem("Item Title",
                        BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.sample_img),
                        BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.sample_img),
                        0,
                        1);
        */
        final List<MyOffersItem> myOffersItems = addItem();
        mMyOffersAdapter = new MyOffersAdapter(myOffersItems,getActivity().getApplicationContext());
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
        SQLiteDatabase db=getActivity().openOrCreateDatabase("tradepostdb.db", getActivity().MODE_PRIVATE, null);
        Cursor c=db.rawQuery("select distinct(itemid) from offers where userid="+ userdata.userid,null);
      c.moveToFirst();
        while(!c.isAfterLast())
        {
            int itemid=c.getInt(0);
            ItemResult ir= ItemWebService.getItem(itemid);
            Bitmap bitmap=getBitmapFromURL("http://73.37.238.238:8084/TDserverWeb/images/items/"+itemid+"/0.png");


            int count=0;
            Cursor c1=db.rawQuery("select count(*) from offers where itemid="+itemid ,null);
            c1.moveToFirst();
            count=c1.getInt(0);
            MyOffersItem item=new MyOffersItem(ir.item.getItemname(),bitmap,count);

            c.moveToNext();
            items.add(item);
        }




        return items;
    }
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void applyLinearLayoutManager() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

}

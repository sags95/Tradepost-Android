package com.sinapp.sharathsind.tradepost;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import Model.MyOffersAdapter;
import Model.MyOffersItem;

/**
 * Created by HenryChiang on 15-06-27.
 */
public class MyOffersTabTwo extends Fragment {

    private View rootView;
    private RecyclerView mRecyclerView;
    private MyOffersAdapter mMyOffersAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private BitmapFactory.Options options;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.myoffers_pager_2, container, false);
        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.myoffers_sent);

        options= new BitmapFactory.Options();
        options.inJustDecodeBounds =true;
        BitmapFactory.decodeResource(getResources(),R.drawable.sample_img,options);

        final List<MyOffersItem> myOffersItems =
                addItem("Item Title",
                        BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.sample_img),
                        BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.sample_img),
                        0,
                        1);
        mMyOffersAdapter = new MyOffersAdapter(myOffersItems,getActivity().getApplicationContext());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mMyOffersAdapter);
        applyLinearLayoutManager();

        return rootView;
    }

    public List<MyOffersItem> addItem(String itemTitle, Bitmap userPic, Bitmap itemImg, int itemAction, int offerType) {
        List<MyOffersItem> items = new ArrayList<MyOffersItem>();

        items.add(new MyOffersItem(itemTitle, userPic, BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.sample_img3), 0,1));
        items.add(new MyOffersItem(itemTitle, userPic, BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.sample_img3), 1,1));
        items.add(new MyOffersItem(itemTitle, userPic, BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.sample_img3), -1,1));

        items.add(new MyOffersItem(itemTitle, userPic, itemImg, 0,1));
        items.add(new MyOffersItem(itemTitle, userPic, itemImg, 1,1));
        items.add(new MyOffersItem(itemTitle, userPic, itemImg, -1,1));



        return items;
    }

    private void applyLinearLayoutManager() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

}
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
    private RecyclerView.LayoutManager mLayoutManager;
    private BitmapFactory.Options options;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.myoffers_pager_1, container, false);
        emptyView = rootView.findViewById(R.id.myoffers_received_emptyView);
        mRecyclerView = (EmptyRecyclerView)rootView.findViewById(R.id.myoffers_received);

        options= new BitmapFactory.Options();
        options.inJustDecodeBounds =true;
        BitmapFactory.decodeResource(getResources(),R.drawable.sample_img,options);

        /*
        final List<MyOffersItem> myOffersItems =
                addItem("Item Title",
                        BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.sample_img),
                        BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.sample_img),
                        0,
                        0);
        */
        final List<MyOffersItem> myOffersItems = null;

        mMyOffersAdapter = new MyOffersAdapter(myOffersItems, getActivity().getApplicationContext());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setEmptyView(emptyView);
        mRecyclerView.setAdapter(mMyOffersAdapter);
        applyLinearLayoutManager();

        return rootView;
    }

    public List<MyOffersItem> addItem(String itemTitle, Bitmap userPic, Bitmap itemImg, int itemAction, int offerType) {
        List<MyOffersItem> items = new ArrayList<MyOffersItem>();

        items.add(new MyOffersItem(itemTitle, userPic, BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.sample_img3), 0,0));
        items.add(new MyOffersItem(itemTitle, userPic, BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.sample_img3), 1,0));
        items.add(new MyOffersItem(itemTitle, userPic, BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.sample_img3), -1,0));

        items.add(new MyOffersItem(itemTitle, userPic, itemImg, 0,0));
        items.add(new MyOffersItem(itemTitle, userPic, itemImg, 1,0));
        items.add(new MyOffersItem(itemTitle, userPic, itemImg, -1,0));



        return items;
    }

    private void applyLinearLayoutManager() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

}





package com.sinapp.sharathsind.tradepost;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import Model.CustomLinearLayoutManager;
import Model.EmptyRecyclerView;
import Model.MyOffersDetailsAdapter;

/**
 * Created by HenryChiang on 2015-11-15.
 */
public class MyOffersItemDetailActivity extends AppCompatActivity {

    private EmptyRecyclerView mRecyclerView;
    private MyOffersDetailsAdapter myOffersDetailsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_offers_details);


        mRecyclerView = (EmptyRecyclerView)findViewById(R.id.myOffers_details_rcv);
        LinearLayoutManager mLayoutManager = new CustomLinearLayoutManager(this);

        //adapter
        myOffersDetailsAdapter = new MyOffersDetailsAdapter();
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(myOffersDetailsAdapter);

    }





}

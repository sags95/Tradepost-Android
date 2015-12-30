package com.sinapp.sharathsind.tradepost;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.google.android.gms.analytics.ExceptionReporter;
import com.google.android.gms.analytics.Tracker;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import Model.CustomLinearLayoutManager;
import Model.CustomTextView;
import Model.EmptyRecyclerView;
import Model.MyOffersDetailsAdapter;
import Model.MyOffersItem;
import Model.RecyclerViewOnClickListener;
import datamanager.ItemResult;
import datamanager.userdata;
import webservices.ItemWebService;

/**
 * Created by HenryChiang on 2015-11-15.
 */
public class MyOffersItemDetailActivity extends AppCompatActivity {

    private EmptyRecyclerView mRecyclerView;
    private MyOffersDetailsAdapter myOffersDetailsAdapter;
int offerid;
    ArrayList<Integer>itemid;
    private Tracker mTracker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TradePost application = (TradePost) getApplication();
        mTracker = application.getDefaultTracker();
        //ctivity().getApplication()).getTracker(TrackerName.APP_TRACKER);

// Build and send exception.
        Thread.UncaughtExceptionHandler myHandler = new ExceptionReporter(
                mTracker,                                        // Currently used Tracker.
                Thread.getDefaultUncaughtExceptionHandler(),      // Current default uncaught exception handler.
                this);
        setContentView(R.layout.activity_my_offers_details);

offerid=getIntent().getIntExtra("itemid",0);
 final String of=getIntent().getStringExtra("f");
        mRecyclerView = (EmptyRecyclerView)findViewById(R.id.myOffers_details_rcv);
        LinearLayoutManager mLayoutManager = new CustomLinearLayoutManager(this);
ArrayList<MyOffersItem> a=new ArrayList<>();
        a=addItem();
        //adapter
        myOffersDetailsAdapter = new MyOffersDetailsAdapter(this,a,null,itemid);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(myOffersDetailsAdapter);
        final ArrayList<MyOffersItem> finalA = a;
        mRecyclerView.addOnItemTouchListener(
                new RecyclerViewOnClickListener(this, new RecyclerViewOnClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if(of.equals("one"))
                        {
                            CustomTextView cb=(CustomTextView) view.findViewById(R.id.myOffers_details_status);
                            if(cb.getText().toString().equals("PENDING"))
                            {
                                Intent i=new Intent(MyOffersItemDetailActivity.this,notificationoffertesting.class);
                                i.putExtra("offerid",itemid.get(position));
                                i.putExtra("itemname", finalA.get(position).getOffersItemTitle());
                                startActivity(i);

                            }



                        }
                    }
                })
        );

    }
    public ArrayList<MyOffersItem> addItem() {
       ArrayList <MyOffersItem> items = new ArrayList<MyOffersItem>();
        SQLiteDatabase db=openOrCreateDatabase("tradepostdb.db", MODE_PRIVATE, null);
        Cursor c=db.rawQuery("select * from offers where itemid="+ offerid,null);
        c.moveToFirst();
        itemid=new ArrayList<>();
        while(!c.isAfterLast())
        {

             itemid.add(c.getInt(c.getColumnIndex("Offerid")));
            ItemResult ir= ItemWebService.getItem(offerid);
           Bitmap bitmap=MyOffersTabOne.getBitmapFromURL("http://73.37.238.238:8084/TDserverWeb/images/items/" + itemid + "/0.png");


            int count=0;
//            Cursor c1=db.rawQuery("select count(*) from offers where itemid="+itemid ,null);
  //          c1.moveToFirst();
    //        count=c1.getInt(0);
            MyOffersItem item=new MyOffersItem(ir.item.getItemname(),bitmap,count);

            items.add(item);
            c.moveToNext();
        }

c.close();


        return items;
    }




}

package com.sinapp.sharathsind.tradepost;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.etsy.android.grid.StaggeredGridView;

import Model.MarketPlaceData;
import Model.MarketPlaceDataAdapter;

/**
 * Created by HenryChiang on 15-05-26.
 */
public class ChatActivity extends NewToolBar {

    ImageView attachBtn;
    RelativeLayout sendBar, attachBar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_chat_room);

        attachBtn = (ImageView)findViewById(R.id.attach_btn);
        sendBar = (RelativeLayout)findViewById(R.id.send_bar);
        attachBar = (RelativeLayout)findViewById(R.id.attach_bar);


        RelativeLayout.LayoutParams attachBarparams = (RelativeLayout.LayoutParams)attachBar.getLayoutParams();



        attachBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout.LayoutParams sendBarparams = (RelativeLayout.LayoutParams)sendBar.getLayoutParams();
                RelativeLayout.LayoutParams attachBarparams = (RelativeLayout.LayoutParams)attachBar.getLayoutParams();
                sendBarparams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
                attachBarparams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,1);
                sendBar.setLayoutParams(sendBarparams);
                attachBar.setLayoutParams(attachBarparams);
            }
        });


    }





}


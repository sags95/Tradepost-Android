package com.sinapp.sharathsind.tradepost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;

import Model.OfferListViewAdapter;

/**
 * Created by HenryChiang on 15-06-23.
 */
public class OfferActivity extends AppCompatActivity {

    private Button addCashBtn;
    private EditText addCashEdit;
    private String[] sample = {"item1","item2","item3","item4","item5","item6","item7"};
    private ListView offerListView;
    private Toolbar mToolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        //ToolBar set up
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("Make an Offer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        addCashBtn = (Button)findViewById(R.id.offer_addcash);
        addCashBtn.setOnClickListener(addCashBtnListener);
        addCashEdit = (EditText)findViewById(R.id.offer_addcash_edit);

        offerListView = (ListView)findViewById(R.id.offer_listview);
        offerListView.setAdapter(new OfferListViewAdapter(this,sample));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item;

        item = menu.add("Submit Offer");
        //item.setIcon(R.drawable.ic_toolbar_search);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);
    }

    public View.OnClickListener addCashBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(addCashEdit.getVisibility()==View.GONE) {
                addCashEdit.setVisibility(View.VISIBLE);
                addCashBtn.setText("REMOVE CASH");
            }else{
                addCashEdit.setText("");
                addCashEdit.setVisibility(View.GONE);
                addCashBtn.setText("ADD CASH");
            }


        }
    };


}

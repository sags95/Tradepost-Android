package com.sinapp.sharathsind.tradepost;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Model.CustomPagerAdapter;

/**
 * Created by HenryChiang on 15-06-06.
 */
public class SingleListingActivity extends AppCompatActivity {

    private CustomPagerAdapter mCustomPagerAdapter;
    private final static int NUM_IMAGES = 4;
    private ViewPager mViewPager;
    private List<ImageView> dots;
    private Toolbar toolbar;
    private FloatingActionButton offerFab;
    private TextView itemTitle;

    private int[] imageResources={
            R.drawable.sample_img,
            R.drawable.sample_img2,
            R.drawable.sample_img3,
            R.drawable.sample_img
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_listing_view);

        //floating action button
        offerFab = (FloatingActionButton)findViewById(R.id.offer_fab2);
        offerFab.setOnClickListener(offerFabOnClickListener);


        //image slider viewer
        mCustomPagerAdapter = new CustomPagerAdapter(this,imageResources);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);

        //pass the item details
        //get the passed data
        Intent i = getIntent();
        ArrayList<String> itemDetails = i.getStringArrayListExtra("itemClicked");
        Log.d("item details","item position: " + itemDetails.get(0));
        Log.d("item details","item title: " + itemDetails.get(1));

        //item title
        itemTitle = (TextView)findViewById(R.id.single_listing_item_title);
        itemTitle.setText(itemDetails.get(1));

        //setup actionbar
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

         addDots();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }

    public void addDots() {
        dots = new ArrayList<>();
        LinearLayout dotsLayout = (LinearLayout)findViewById(R.id.dots);

        for(int i = 0; i < NUM_IMAGES; i++) {
            ImageView dot = new ImageView(this);
            if(i==0){
                dot.setImageDrawable(getResources().getDrawable(R.drawable.pager_dot_selected));
            }else {
                dot.setImageDrawable(getResources().getDrawable(R.drawable.pager_dot_not_selected));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                );
            params.setMargins(3,0,3,0);

            dotsLayout.addView(dot,params);

           // dotsLayout.addView(dot);
            dots.add(dot);
        }

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                selectDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void selectDot(int idx) {
        Resources res = getResources();
        for(int i = 0; i < NUM_IMAGES; i++) {
            int drawableId = (i==idx)?(R.drawable.pager_dot_selected):(R.drawable.pager_dot_not_selected);
            Drawable drawable = res.getDrawable(drawableId);
            dots.get(i).setImageDrawable(drawable);
        }
    }

    public View.OnClickListener offerFabOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),OfferProcessActivity.class));
        }
    };


}

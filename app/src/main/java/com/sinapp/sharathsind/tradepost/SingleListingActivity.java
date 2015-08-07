package com.sinapp.sharathsind.tradepost;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import Model.CustomPagerAdapter;
import Model.MarketPlaceData;
import Model.MarketPlaceStaggeredAdapter;

/**
 * Created by HenryChiang on 15-06-06.
 */
public class SingleListingActivity extends AppCompatActivity {

    private CustomPagerAdapter mCustomPagerAdapter;
    private ViewPager mViewPager;
    private List<ImageView> dots;
    private Toolbar toolbar;
    private FloatingActionButton offerFab;
    private TextView itemTitle;
    private RelativeLayout singleListingHeader;

    private Bitmap[] imageResources;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_listing_view);

        //single listing header
        singleListingHeader= (RelativeLayout)findViewById(R.id.single_listing_header);
        singleListingHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
            }
        });

        //floating action button
        offerFab = (FloatingActionButton)findViewById(R.id.offer_fab2);
        offerFab.setOnClickListener(offerFabOnClickListener);
        Intent i = getIntent();
        ArrayList<String> itemDetails = i.getStringArrayListExtra("itemClicked");
        MarketPlaceData m= MarketPlaceStaggeredAdapter.mData.get(Integer.parseInt(itemDetails.get(0)));
        imageResources =new Bitmap[m.image.length];
        int id=0;
        for(String s : m.image)
        {
try {
    URL url = new URL(s);
    URLConnection con = url.openConnection();
    con.setRequestProperty("connection","close");

    InputStream is = con.getInputStream();


  imageResources[id] = BitmapFactory.decodeStream(is);
    is.close();
    (  (HttpURLConnection)con).disconnect();
id++;
}
catch (Exception e)
{
e.printStackTrace();
}


        }


        //image slider viewer
        mCustomPagerAdapter = new CustomPagerAdapter(this,imageResources);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);

        //pass the item details
        //get the passed data


        Log.d("item details","item title: " + itemDetails.get(1));

        //item title
        itemTitle = (TextView)findViewById(R.id.single_listing_item_title);
        itemTitle.setText(itemDetails.get(1));

        //setup actionbar
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.ColorPrimary));
        toolbar.setTitle("Listing");

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

        for(int i = 0; i < imageResources.length; i++) {
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
        for(int i = 0; i < imageResources.length; i++) {
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

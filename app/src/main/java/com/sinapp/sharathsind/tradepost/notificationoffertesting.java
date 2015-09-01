package com.sinapp.sharathsind.tradepost;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Templates;

import Model.CustomPagerAdapter;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HenryChiang on 15-07-22.
 */
public class notificationoffertesting extends AppCompatActivity {


    private View rootView;
    private CustomPagerAdapter mCustomPagerAdapter;
    private ViewPager mViewPager;
    private List<ImageView> dots;
    private LinearLayout dotsLayout;
    private Bitmap[] imageResources;

    final private String singleItem = "An Item";
    final private String multiItems = "Multi-items";
    final private String cash = "$";
    final private String currency = " CAD";

    private TextView myItemPlaceholder, usernamePlaceholder, itemOfferPlaceholder, extraCashPlaceholder;
    private CircleImageView userImgPlaceholder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     setContentView(R.layout.notification_offer);


       // rootView = inflater.inflate(R.layout.notification_offer, container, false);

        //
        myItemPlaceholder = (TextView)findViewById(R.id.noti_offer_myItem_placeholder);

        //
        userImgPlaceholder = (CircleImageView)findViewById(R.id.noti_offer_userImg_placeholder);
        usernamePlaceholder = (TextView)findViewById(R.id.noti_offer_userName_placeholder);
        itemOfferPlaceholder =(TextView)findViewById(R.id.noti_offer_itemOffer_placeholder);
        extraCashPlaceholder = (TextView)findViewById(R.id.noti_offer_extraCash_Placeholder);

        //image slider viewer
        mCustomPagerAdapter = new CustomPagerAdapter(this,imageResources);
        mCustomPagerAdapter.imagesArray=new String[0];
        mViewPager = (ViewPager)findViewById(R.id.noti_offer_viewPager_placeholder);
        mViewPager.setAdapter(mCustomPagerAdapter);

        dotsLayout = (LinearLayout)findViewById(R.id.dots);
        addDots();

        //getting data from server.
        setMyItemPlaceholder("Test Item");



    }


    public void setMyItemPlaceholder(String myItem){
        myItemPlaceholder.setText(myItem);
    }

    public void setUserDetails(Bitmap userImg, String username, int itemOfferCount, int extraCash){
        userImgPlaceholder.setImageBitmap(userImg);
        usernamePlaceholder.setText(username);

        if(itemOfferCount==0){
            itemOfferPlaceholder.setText(singleItem);
        }else{
            itemOfferPlaceholder.setText(multiItems);
        }

        extraCashPlaceholder.setText(cash+extraCash+currency);
    }

    //adding dots below viewpager.
    public void addDots() {
        dots = new ArrayList<>();

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

    //highlight dot below viewpager.
    public void selectDot(int idx) {
        Resources res = getResources();
        for(int i = 0; i < imageResources.length; i++) {
            int drawableId = (i==idx)?(R.drawable.pager_dot_selected):(R.drawable.pager_dot_not_selected);
            Drawable drawable = res.getDrawable(drawableId);
            dots.get(i).setImageDrawable(drawable);
        }
    }
}

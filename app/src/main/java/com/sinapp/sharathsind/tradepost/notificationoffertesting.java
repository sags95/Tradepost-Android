package com.sinapp.sharathsind.tradepost;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import Model.CustomPagerAdapter;

/**
 * Created by HenryChiang on 15-07-22.
 */
public class notificationoffertesting extends Fragment {


    private View rootView;
    private CustomPagerAdapter mCustomPagerAdapter;
    private ViewPager mViewPager;
    private List<ImageView> dots;
    private LinearLayout dotsLayout;
    private int[] imageResources={
            R.drawable.sample_img,
            R.drawable.sample_img2,
            R.drawable.sample_img3,
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.notification_offer, container, false);

        //image slider viewer
        mCustomPagerAdapter = new CustomPagerAdapter(getActivity(),imageResources);
        mViewPager = (ViewPager)rootView.findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);

        dotsLayout = (LinearLayout)rootView.findViewById(R.id.dots);
        addDots();


        return rootView;
    }

    public void addDots() {
        dots = new ArrayList<>();

        for(int i = 0; i < imageResources.length; i++) {
            ImageView dot = new ImageView(getActivity());
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
}

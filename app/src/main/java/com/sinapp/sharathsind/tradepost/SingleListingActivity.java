package com.sinapp.sharathsind.tradepost;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.apmem.tools.layouts.FlowLayout;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import Model.CustomPagerAdapter;
import Model.CustomTextView;
import Model.MarketPlaceData;
import Model.MarketPlaceListAdapter;
import Model.MarketPlaceStaggeredAdapter;
import Model.Variables;
import datamanager.userdata;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HenryChiang on 15-06-06.
 */
public class SingleListingActivity extends AppCompatActivity {

    private CustomPagerAdapter mCustomPagerAdapter;
    private ViewPager mViewPager;
    private List<ImageView> dots;
    private Toolbar toolbar;


    private FloatingActionButton offerFab;
    private CustomTextView itemTitle,itemDescription,itemCondition,itemDateAdded,itemUsername,itemDistance;
    private FlowLayout tagsLayout;
    private CircleImageView proPic;

    private RelativeLayout singleListingHeader;
    MarketPlaceData m;

    private boolean isSelfItem = false;
    private String[] itemTagsToEdit=null;
    private String itemCatToEdit = "";
    private String[] imageResources;
    private String itemId="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_listing_view);
        View includeView = (View)findViewById(R.id.single_listing_main_layout);
GCMService.b=true;

        //single listing header
        singleListingHeader= (RelativeLayout)findViewById(R.id.single_listing_header);
        singleListingHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                ArrayList<String> itemProfileClicked = new ArrayList<>();
                itemProfileClicked.add(0, String.valueOf(m.item.item.getUserid()));
                itemProfileClicked.add(1, m.proUsername);
                i.putStringArrayListExtra("itemProfileClicked", itemProfileClicked);

                //
                i.putExtra("caller","SingleListingActivity");
                Bitmap b = ((BitmapDrawable)proPic.getDrawable()).getBitmap();
                i.putExtra("profilePic", b);

                startActivity(i);
            }
        });

        //item information
        proPic = (CircleImageView)singleListingHeader.findViewById(R.id.single_listing_header_userImg);
        itemTitle = (CustomTextView)singleListingHeader.findViewById(R.id.single_listing_header_itemTitle);
        itemCondition = (CustomTextView)includeView.findViewById(R.id.single_listing_item_condition);
        itemDescription = (CustomTextView)includeView.findViewById(R.id.single_listing_des_input);
        itemDateAdded = (CustomTextView)singleListingHeader.findViewById(R.id.single_listing_header_time);
        tagsLayout = (FlowLayout)includeView.findViewById(R.id.single_listing_tags);
        itemUsername = (CustomTextView)singleListingHeader.findViewById(R.id.single_listing_header_username);
        itemDistance = (CustomTextView)singleListingHeader.findViewById(R.id.single_listing_header_distance);

        //floating action button
        //offerFab = (FloatingActionButton)findViewById(R.id.offer_fab2);
        offerFab = (FloatingActionButton)includeView.findViewById(R.id.fab);
        offerFab.setOnClickListener(offerFabOnClickListener);


        Intent i = getIntent();

        if(getIntent().getStringExtra("caller").equals("MyItem")){
            ArrayList<String> itemInfo = getIntent().getStringArrayListExtra("myItemClicked");
            Bitmap proPicReceived = i.getParcelableExtra("profilePic");

            //item id
            itemId=itemInfo.get(0);
            //item userPic
            Picasso.with(this).load(Uri.parse("http://73.37.238.238:8084/TDserverWeb/images/" + userdata.userid + "/profile.png")).into(proPic);
            //proPic.setImageBitmap(proPicReceived);
            //item title
            itemTitle.setText(itemInfo.get(1));
            //item description
            itemDescription.setText(itemInfo.get(3));
            //item condition
            itemCondition.setText(setCondition(Integer.parseInt(itemInfo.get(5))));
            //item dateAdded
            itemDateAdded.setText(itemInfo.get(4));
            //item username
            itemUsername.setText(itemInfo.get(2));
            //item distance
            itemDistance.setText(String.valueOf(roundedDistance(distance(userdata.mylocation.latitude,userdata.mylocation.Longitude, userdata.mylocation.latitude, userdata.mylocation.Longitude, 'K'))));

            String[] itemImages = getIntent().getStringArrayExtra("itemImages");
            String[] images = new String[itemImages.length];

            for(int j=0;j<itemImages.length;j++){
                images[j]="http://73.37.238.238:8084/TDserverWeb/images/items/" + itemInfo.get(0) +"/"+ itemImages[j];

            }
            String[] itemTags = getIntent().getStringArrayExtra("itemTags");

            //For EditActivity
            itemTagsToEdit = new String[itemTags.length];
            itemTagsToEdit=itemTags;
            itemCatToEdit = itemInfo.get(6);

            imageResources = new String[itemImages.length];
            mCustomPagerAdapter = new CustomPagerAdapter(this,images);
            mViewPager = (ViewPager) findViewById(R.id.pager);
            mViewPager.setAdapter(mCustomPagerAdapter);

            for (String tempTag : itemTags) {
                tagsLayout.addView(addTagsSingleListing(tempTag));
            }
            offerFab.setVisibility(View.GONE);
            isSelfItem=true;



        }else {
            ArrayList<String> itemDetails = i.getStringArrayListExtra("itemClicked");
            m = MarketPlaceStaggeredAdapter.mData.get(Integer.parseInt(itemDetails.get(0)));
            Bitmap proPicReceived = i.getParcelableExtra("profilePic");
            //item userPic
            proPic.setImageBitmap(proPicReceived);
            //item title
            itemTitle.setText(m.item.item.getItemname());
            //item description
            itemDescription.setText(m.item.item.getDescription());
            //item condition
            itemCondition.setText(setCondition(m.item.item.getCon()));
            //item dateAdded
            itemDateAdded.setText(MarketPlaceStaggeredAdapter.daysBetween(m.item.item.getDateadded()));
            //item username
            itemUsername.setText(m.item.username);
            //item distance
            itemDistance.setText(String.valueOf(roundedDistance(distance(m.item.item.getLatitude().doubleValue(), m.item.item.getLongtitude().doubleValue(), userdata.mylocation.latitude, userdata.mylocation.Longitude, 'K'))));

            //item tags
            for (String tempTag : m.item.tags) {
                tagsLayout.addView(addTagsSingleListing(tempTag));

            }

            if(m.item.item.getUserid()==Constants.userid){


                //item distance
                offerFab.setVisibility(View.GONE);
                isSelfItem=true;

                //For EditActivity
                itemTagsToEdit = new String[m.item.tags.length];
                itemTagsToEdit=m.item.tags;
                itemCatToEdit = m.item.item.getCategory();
            }else{

                //item distance
                offerFab.setVisibility(View.VISIBLE);
                isSelfItem=false;
            }

            imageResources = new String[m.image.length];
            mCustomPagerAdapter = new CustomPagerAdapter(this,m.image);
            mViewPager = (ViewPager) findViewById(R.id.pager);
            mViewPager.setAdapter(mCustomPagerAdapter);

        }



        /*
        imageResources =new Bitmap[m.image.length];
        for(int j=0;j<m.image.length;j++){
            try {
                String s= m.image[j];
                URL url = new URL(s);
                URLConnection con = url.openConnection();
                con.setRequestProperty("connection","close");
                InputStream is = con.getInputStream();
                imageResources[j] = BitmapFactory.decodeStream(is);
                is.close();
                ((HttpURLConnection)con).disconnect();

            }catch (Exception e){
            }
        }
        */
        //image slider viewer
        //mCustomPagerAdapter = new CustomPagerAdapter(this,m.image);

        //setup actionbar
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.toolbar_custom_title, null);
        TextView title1 = (TextView) v.findViewById(R.id.toolbar_title1);
        TextView title2 = (TextView) v.findViewById(R.id.toolbar_title2);
        title1.setText("Item Details");
        title2.setVisibility(View.GONE);
        getSupportActionBar().setCustomView(v);
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
        MenuItem item;

        //put logic here
        if(isSelfItem){
            item = menu.add("Edit");
            item.setIcon(R.drawable.ic_action_edit);
            MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
        }


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GCMService.b=true;
    }

    @Override
    protected void onResume() {

        super.onResume();
        GCMService.b=true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        GCMService.b=true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        GCMService.b=false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        GCMService.b=false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getTitle().toString().equals("Edit")){
            Toast.makeText(getApplicationContext(), "Edit", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(),EditListingActivity.class);

            //General Information
            ArrayList<String> editListing = new ArrayList<>();
            editListing.add(0,itemId);
            editListing.add(1,itemTitle.getText().toString());
            editListing.add(2,itemDescription.getText().toString());
            editListing.add(3,itemCondition.getText().toString());
            editListing.add(4,itemCatToEdit);

            i.putStringArrayListExtra("itemToEdit", editListing);

            //Tags
            i.putExtra("tagsToEdit", itemTagsToEdit);
            startActivity(i);
            finish();



        }

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
            Intent intent = new Intent(getApplicationContext(), OfferProcessActivity.class);
            Intent i = getIntent();
            ArrayList<String> itemDetails = i.getStringArrayListExtra("itemClicked");
            ArrayList<String> items = new ArrayList<String>();
            items.add(0,String.valueOf(m.item.item.getItemid()));
            items.add(1,String.valueOf(m.item.item.getItemname()));
            items.add(2,String.valueOf(m.item.item.getUserid()));
            intent.putStringArrayListExtra("itemToOfferProcess", items);
            startActivity(intent);

        }
    };


    public String setCondition(int condition){
        String temp ="";
        switch (condition){
            case 0:
                temp = "POOR";
                break;
            case 1:
                temp = "FAIR";
                break;
            case 2:
                temp = "GREAT";
                break;
            case 3:
                temp = "MINT";
                break;
            case 4:
                temp = "NEW";
                break;
        }

        return temp;
    }

    public CustomTextView addTagsSingleListing(String tag) {

        CustomTextView newTag = new CustomTextView(getApplicationContext());
        newTag.setText(tag.toUpperCase());
        newTag.setTextColor(getResources().getColor(R.color.white));
        newTag.setClickable(true);
        newTag.settingOpenSansLight();
        newTag.setBackgroundResource(R.drawable.tag_btn_shape);
        FlowLayout.LayoutParams lp = new FlowLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,DpToPx(8), DpToPx(8),0);

        newTag.setLayoutParams(lp);



        return newTag;

    }


    public int DpToPx(int requireDp ){
        float d = getResources().getDisplayMetrics().density;
        return (int)(requireDp * d); // margin in pixels

    }

    private double distance(double lat1, double lon1, double lat2, double lon2, char unit) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);

        dist = rad2deg(dist);

        dist = dist * 60 * 1.1515;

        if (unit == 'K') {

            dist = dist * 1.609344;

        } else if (unit == 'N') {

            dist = dist * 0.8684;

        }

        return (dist);

    }

    private double deg2rad(double deg) {

        return (deg * Math.PI / 180.0);

    }

    private double rad2deg(double rad) {

        return (rad * 180 / Math.PI);

    }

    private double roundedDistance(double distance){
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(distance));
    }



}

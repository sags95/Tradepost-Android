package com.sinapp.sharathsind.tradepost;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;


import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import Model.CustomPagerAdapter;
import Model.MarketPlaceData;
import Model.MarketPlaceDataAdapter;
import Model.RecyclerViewOnClickListener;
import Model.StaggeredAdapter;
import Model.StaggeredAdapter2;

/**
 * Created by HenryChiang on 15-06-25.
 */
public class MarketPlaceFragment  extends Fragment {

    private MarketPlaceDataAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private StaggeredAdapter stagAdapter;
    private StaggeredAdapter2 stagAdapter2;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private View rootView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private ViewPager mViewPager;
    private final static int NUM_IMAGES_MP = 4;
    private List<ImageView> dots;
    private int[] imageResources = {
            R.drawable.sample_img,
            R.drawable.sample_img2,
            R.drawable.sample_img3,
            R.drawable.sample_img
    };

    //for dialog
    int location_dialog_layout = R.layout.location_dialog2;
    LayoutInflater li;
    private AlertDialog dialog;
    private RadioButton rBPostalCode, rBLocSer;
    private TextInputLayout postalCodeInput;
    private EditText pCInputEdit;
    private SeekBar seekBar;
    private TextView radiusText, locServiceText, label25Km,label50Km,label75Km,label100Km;
    private LinearLayout seekBarLabel;





    public MarketPlaceFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Tradepost");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_marketplace, container, false);
        li = getActivity().getLayoutInflater();



        /*
        recyclerview = (ObservableRecyclerView) rootView.findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        recyclerview.setScrollViewCallbacks(this);
        stagAdapter2 = new StaggeredAdapter2(MarketPlaceData.generateSampleData(), listingItemClickListener);
        recyclerview.setAdapter(stagAdapter2);
        applyStaggeredGridLayoutManager();
        */
        //Image Pager


        /*
        mCustomPagerAdapter = new CustomPagerAdapter(getActivity());
        mViewPager = (ViewPager)rootView.findViewById(R.id.pager_marketplace);
        mViewPager.setAdapter(mCustomPagerAdapter);
        */


        //SwipeToRefresh
        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.ColorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("SwipeToRefresh","Refreshing");
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        //StaggeredGridView
        /*mRecyclerView = (RecyclerView)rootView.findViewById(R.id.recylcer_view);
        mRecyclerView.setHasFixedSize(true);
        stagAdapter = new StaggeredAdapter(getActivity().getApplicationContext(),MarketPlaceData.generateSampleData(),listingItemClickListener, imageResources);
        mRecyclerView.setAdapter(stagAdapter);
        applyStaggeredGridLayoutManager();
        */

        //StaggeredGridView2

        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.recylcer_view);
        mRecyclerView.setHasFixedSize(true);
        stagAdapter2 = new StaggeredAdapter2(MarketPlaceData.generateSampleData(),listingItemClickListener);
        mRecyclerView.setAdapter(stagAdapter2);
        applyStaggeredGridLayoutManager();
        /*
        mRecyclerView.addOnItemTouchListener(
                new RecyclerViewOnClickListener(getActivity(), new RecyclerViewOnClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(getActivity(), "you click" + position, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getActivity(), SingleListingActivity.class);
                        ArrayList<String> clickedItemDetails = new ArrayList<>();
                        TextView itemTitle = (TextView) mRecyclerView.getChildAt(position).findViewById(R.id.item_title);
                        clickedItemDetails.add(0, String.valueOf(position));
                        clickedItemDetails.add(1, itemTitle.getText().toString());
                        i.putStringArrayListExtra("itemClicked", clickedItemDetails);
                        startActivity(i);
                    }
                })
        );
        */



        //Floating Action Button
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(fabOnClickListener);
        fab.attachToRecyclerView(mRecyclerView);

        //Like Button
        ImageView likeBtn = (ImageView) rootView.findViewById(R.id.image_like_btn);
        //likeBtn.setOnClickListener(likeOnClickListener);

        //Location
        View includeView = (View)rootView.findViewById(R.id.marketplace_header);
        includeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog();
            }
        });



        return rootView;

    }


    private void applyStaggeredGridLayoutManager() {
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
    }

    public View.OnClickListener fabOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getActivity(), ListingProcessActivity.class));
        }
    };

    public View.OnClickListener likeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        }
    };

    public View.OnClickListener listingItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("child position", String.valueOf(mRecyclerView.getChildPosition(v)));
            Intent i = new Intent(getActivity(), SingleListingActivity.class);
            ArrayList<String> clickedItemDetails = new ArrayList<>();
            TextView itemTitle = (TextView) mRecyclerView.getChildAt(mRecyclerView.getChildPosition(v)).findViewById(R.id.item_title);
            clickedItemDetails.add(0, String.valueOf(mRecyclerView.getChildPosition(v)));
            clickedItemDetails.add(1, itemTitle.getText().toString());

            //Log.d("item details", "item position: " + clickedItemDetails.get(0));
            //Log.d("item details", "item title: " + clickedItemDetails.get(1));

            i.putStringArrayListExtra("itemClicked", clickedItemDetails);
            startActivity(i);
        }
    };

    public void customDialog(){

        final View dialogView = li.inflate(location_dialog_layout, null);
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
        builder.setTitle("Set Up Your Location");
        //builder.setMessage("Lorem ipsum dolor ....");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (rBLocSer.isChecked()) {
                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.setView(dialogView);

        rBPostalCode = (RadioButton)dialogView.findViewById(R.id.radioButton_postalCode);
        rBLocSer = (RadioButton)dialogView.findViewById(R.id.radioButton_locService);
        postalCodeInput = (TextInputLayout)dialogView.findViewById(R.id.dialog_postalCode);
        pCInputEdit = (EditText)dialogView.findViewById(R.id.dialog_postalCode_edit);
        seekBar = (SeekBar)dialogView.findViewById(R.id.dialog_seekbar);
        radiusText = (TextView)dialogView.findViewById(R.id.dialog_radius_txt);
        locServiceText = (TextView)dialogView.findViewById(R.id.dialog_loc_text);
        seekBarLabel = (LinearLayout)dialogView.findViewById(R.id.dialog_seekbar_label);
        label25Km = (TextView)dialogView.findViewById(R.id.dialog_seekbar_25km);
        label25Km.setTextColor(getResources().getColor(R.color.ColorPrimaryDark));
        label50Km = (TextView)dialogView.findViewById(R.id.dialog_seekbar_50km);
        label75Km = (TextView)dialogView.findViewById(R.id.dialog_seekbar_75km);
        label100Km = (TextView)dialogView.findViewById(R.id.dialog_seekbar_100km);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch (progress) {
                    case 0:
                        label25Km.setTextColor(getResources().getColor(R.color.ColorPrimaryDark));
                        label25Km.setTypeface(Typeface.DEFAULT_BOLD);
                        setTextStyle(label50Km, label75Km, label100Km);
                        break;
                    case 1:
                        label50Km.setTextColor(getResources().getColor(R.color.ColorPrimaryDark));
                        label50Km.setTypeface(Typeface.DEFAULT_BOLD);
                        setTextStyle(label25Km, label75Km, label100Km);
                        break;
                    case 2:
                        label75Km.setTextColor(getResources().getColor(R.color.ColorPrimaryDark));
                        label75Km.setTypeface(Typeface.DEFAULT_BOLD);
                        setTextStyle(label25Km, label50Km, label100Km);
                        break;
                    case 3:
                        label100Km.setTextColor(getResources().getColor(R.color.ColorPrimaryDark));
                        label100Km.setTypeface(Typeface.DEFAULT_BOLD);
                        setTextStyle(label25Km, label50Km, label75Km);
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        pCInputEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0&&!Character.isLetterOrDigit(s.charAt(pCInputEdit.length()-1))){
                    postalCodeInput.setError("Only Letters or Digits Are allowed");
                    postalCodeInput.setErrorEnabled(true);
                    //pCInputEdit.getText().delete(s.length()-1,s.length());
                }else{
                    postalCodeInput.setErrorEnabled(false);
                }
            }
        });

        rBPostalCode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                rBLocSer.setChecked(!isChecked);
                setPostalCodeInput(1);
                setLocationService(0);

            }
        });
        rBLocSer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                rBPostalCode.setChecked(!isChecked);
                setPostalCodeInput(0);
                setLocationService(1);


            }
        });
        label25Km.setTypeface(Typeface.DEFAULT_BOLD);
        setPostalCodeInput(0);
        setLocationService(0);
        builder.show();


    }

    public void setPostalCodeInput(int visibility){
        if(visibility == 0){
            postalCodeInput.setVisibility(View.GONE);
            pCInputEdit.setVisibility(View.GONE);
            radiusText.setVisibility(View.GONE);
            seekBar.setVisibility(View.GONE);
            seekBarLabel.setVisibility(View.GONE);
        }else{
            postalCodeInput.setVisibility(View.VISIBLE);
            pCInputEdit.setVisibility(View.VISIBLE);
            radiusText.setVisibility(View.VISIBLE);
            seekBar.setVisibility(View.VISIBLE);
            seekBarLabel.setVisibility(View.VISIBLE);

        }

    }

    public void setLocationService(int visibility){
        if(visibility==0){
            locServiceText.setVisibility(View.GONE);
        }else {
            locServiceText.setVisibility(View.VISIBLE);
        }

    }

    public void setTextStyle(TextView a, TextView b, TextView c){

        a.setTextColor(getResources().getColor(R.color.primary_dark_material_light));
        b.setTextColor(getResources().getColor(R.color.primary_dark_material_light));
        c.setTextColor(getResources().getColor(R.color.primary_dark_material_light));

        a.setTypeface(Typeface.DEFAULT);
        b.setTypeface(Typeface.DEFAULT);
        c.setTypeface(Typeface.DEFAULT);





    }

}
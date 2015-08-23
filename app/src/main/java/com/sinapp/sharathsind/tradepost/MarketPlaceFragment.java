package com.sinapp.sharathsind.tradepost;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import com.etsy.android.grid.StaggeredGridView;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import Model.MarketPlaceData;
import Model.MarketPlaceStaggeredAdapter;
import Model.StaggeredAdapterTest;

/**
 * Created by HenryChiang on 15-06-25.
 */
public class MarketPlaceFragment  extends Fragment {

    //private StaggeredGridView mRecyclerView;
    private RecyclerView mRecyclerView;
    private MarketPlaceStaggeredAdapter stagAdapter2;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private View rootView;
    private SwipeRefreshLayout mSwipeRefreshLayout,mSwipeRefreshLayoutEmpty;
    private StaggeredAdapterTest mStaggeredAdapterTest;
    private List<MarketPlaceData> mData;

    private String locationRad="25Km)";
    private int[] imageResources = {
            R.drawable.sample_img,
            R.drawable.sample_img2,
            R.drawable.sample_img3,
            R.drawable.sample_img
    };

    //for dialog
    int location_dialog_layout = R.layout.location_dialog;
    LayoutInflater li;

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private RadioButton rBPostalCode, rBLocSer;
    private TextInputLayout postalCodeInput;
    private EditText pCInputEdit;
    private SeekBar seekBar;
    private TextView headerRadText,radiusText, locServiceText, label25Km,label50Km,label75Km,label100Km;
    private LinearLayout seekBarLabel;
    private List<MarketPlaceData> tempdata;


    public MarketPlaceFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = LayoutInflater.from(getActivity());
        View v = inflator.inflate(R.layout.toolbar_custom_title, null);
        TextView title1 = (TextView) v.findViewById(R.id.toolbar_title1);
        TextView title2 = (TextView) v.findViewById(R.id.toolbar_title2);
        title1.setText("Trade");
        title2.setVisibility(View.VISIBLE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setCustomView(v);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_marketplace, container, false);
        li = getActivity().getLayoutInflater();

        //SwipeToRefresh
        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.ColorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        //
        mSwipeRefreshLayoutEmpty = (SwipeRefreshLayout)rootView.findViewById(R.id.activity_main_swipe_refresh_layout_empty);


        /*
        //StaggeredGridViewTesting
        //For Testing Only
        String[] tempTags= {
                "hello",
                "laptop",
                "android",
                "iphone",
                "whatever"
        };
        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.recylcer_view);
        mStaggeredAdapterTest = new StaggeredAdapterTest(getActivity(),MarketPlaceData.generateSampleDataTest(),listingItemClickListener,tempTags);
        mRecyclerView.setAdapter(mStaggeredAdapterTest);
        applyStaggeredGridLayoutManager();
        */







        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.recylcer_view);
        mRecyclerView.setHasFixedSize(true);
        //stagAdapter2 = new MarketPlaceStaggeredAdapter(getActivity(),MarketPlaceData.generateSampleData(),listingItemClickListener);
        mRecyclerView.setAdapter(stagAdapter2);
        applyStaggeredGridLayoutManager();


        mSwipeRefreshLayout.setVisibility(View.GONE);
        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute();







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
        //fab.attachToListView(mRecyclerView);

        //Like Button
        ImageView likeBtn = (ImageView) rootView.findViewById(R.id.image_like_btn);
        //likeBtn.setOnClickListener(likeOnClickListener);

        //Location
        View includeView = (View)rootView.findViewById(R.id.marketplace_header);
        headerRadText = (TextView)includeView.findViewById(R.id.marketplace_header_rad);
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
            List<MarketPlaceData> tempdata = mData;

            TextView itemTitle = (TextView) mRecyclerView.findViewHolderForPosition(mRecyclerView.getChildPosition(v)).itemView.findViewById(R.id.item_title);
            clickedItemDetails.add(0, String.valueOf(mRecyclerView.getChildPosition(v)));
            clickedItemDetails.add(1, itemTitle.getText().toString());
            clickedItemDetails.add(2, String.valueOf(tempdata.get(mRecyclerView.getChildLayoutPosition(v)).item.item.getItemid()));
            clickedItemDetails.add(3, String.valueOf(tempdata.get(mRecyclerView.getChildLayoutPosition(v)).item.item.getUserid()));
            clickedItemDetails.add(4, String.valueOf(tempdata.get(mRecyclerView.getChildLayoutPosition(v)).item.item.getCon()));
            clickedItemDetails.add(5, String.valueOf(tempdata.get(mRecyclerView.getChildLayoutPosition(v)).item.item.getDescription()));
            clickedItemDetails.add(6, String.valueOf(tempdata.get(mRecyclerView.getChildLayoutPosition(v)).item.item.getDateadded()));
            clickedItemDetails.add(7, String.valueOf(tempdata.get(mRecyclerView.getChildLayoutPosition(v)).proUsername));


            //  clickedItemDetails.add(2,);
            //Log.d("item details", "item position: " + clickedItemDetails.get(0));
            //Log.d("item details", "item title: " + clickedItemDetails.get(1));

            i.putStringArrayListExtra("itemClicked", clickedItemDetails);
            startActivity(i);
        }
    };






    public void customDialog(){

        final View dialogView = li.inflate(location_dialog_layout, null,false);
        builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (rBLocSer.isChecked()) {
                    headerRadText.setText("(Location Service)");
                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                } else if (rBPostalCode.isChecked()) {
                    getRadiusChanged();
                    headerRadText.setVisibility(View.VISIBLE);
                    headerRadText.setText("(Within " + locationRad);
                }
            }
        });


        builder.setNegativeButton("Cancel", null);
        //set custom view.
        builder.setView(dialogView);

        //showing custom dialog.
        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

        //set up the variables
        rBPostalCode = (RadioButton)dialogView.findViewById(R.id.radioButton_postalCode);
        rBLocSer = (RadioButton)dialogView.findViewById(R.id.radioButton_locService);
        postalCodeInput = (TextInputLayout)dialogView.findViewById(R.id.dialog_postalCode);
        pCInputEdit = (EditText)dialogView.findViewById(R.id.dialog_postalCode_edit);
        seekBar = (SeekBar)dialogView.findViewById(R.id.dialog_seekbar);
        radiusText = (TextView)dialogView.findViewById(R.id.dialog_radius_txt);
        locServiceText = (TextView)dialogView.findViewById(R.id.dialog_loc_text);
        seekBarLabel = (LinearLayout)dialogView.findViewById(R.id.dialog_seekbar_label);
        label25Km = (TextView)dialogView.findViewById(R.id.dialog_seekbar_25km);
        label25Km.setTextColor(getResources().getColor(R.color.ColorPrimary));
        label50Km = (TextView)dialogView.findViewById(R.id.dialog_seekbar_50km);
        label75Km = (TextView)dialogView.findViewById(R.id.dialog_seekbar_75km);
        label100Km = (TextView)dialogView.findViewById(R.id.dialog_seekbar_100km);

        //set progress changed on seekbar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch (progress) {
                    case 0:
                        setTypefaceForChosen(label25Km);
                        setTextStyle(label50Km, label75Km, label100Km);
                        break;
                    case 1:
                        setTypefaceForChosen(label50Km);
                        setTextStyle(label25Km, label75Km, label100Km);
                        break;
                    case 2:
                        setTypefaceForChosen(label75Km);
                        setTextStyle(label25Km, label50Km, label100Km);
                        break;
                    case 3:
                        setTypefaceForChosen(label100Km);
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

        //check the zip/postal code input
        pCInputEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setPostalCodeError(s);
            }
        });

        rBPostalCode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                rBLocSer.setChecked(!isChecked);
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                if(pCInputEdit.isFocused()&&pCInputEdit.getEditableText().length()!=0) {
                    setPostalCodeError(pCInputEdit.getEditableText());
                }
                setPostalCodeInput(1);
                setLocationService(0);

            }
        });
        rBLocSer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                rBPostalCode.setChecked(!isChecked);
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                postalCodeInput.setErrorEnabled(false);
                setPostalCodeInput(0);
                setLocationService(1);
            }
        });

        //initialize
        label25Km.setTypeface(Typeface.DEFAULT_BOLD);
        setPostalCodeInput(0);
        setLocationService(0);
    }

    public void setPostalCodeInput(int visibility){
        if(visibility == 0){
            postalCodeInput.setVisibility(View.GONE);
            pCInputEdit.setVisibility(View.GONE);
        }else{
            postalCodeInput.setVisibility(View.VISIBLE);
            pCInputEdit.setVisibility(View.VISIBLE);

        }
    }

    //set different error message according to the input errors.
    public void setPostalCodeError(Editable s){
            if (!isPostalCodeValid(s)) {
                postalCodeInput.setError("Only Letters or Digits Are Allowed.");
                postalCodeInput.setErrorEnabled(true);
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

            } else if (!isPostalCodeValid(s) && pCInputEdit.length() != 6 && pCInputEdit.length() > 0) {
                postalCodeInput.setError("Only Letters or Digits Are Allowed. Zip/Postal Code Too Short.");
                postalCodeInput.setErrorEnabled(true);
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

            } else if (pCInputEdit.length() != 6 && pCInputEdit.length() >=0) {
                postalCodeInput.setError("Zip/Postal Code Too Short.");
                postalCodeInput.setErrorEnabled(true);
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
            } else {
                postalCodeInput.setErrorEnabled(false);
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);

            }

    }

    //check if zip/postal code input contains any not acceptable symbols.
    public boolean isPostalCodeValid(Editable s){
        if(s.length()>0&&!Character.isLetterOrDigit(s.charAt(pCInputEdit.length()-1))){return false;}
        for(int i=0;i<s.length();i++){if(!Character.isLetterOrDigit(s.charAt(i))){return false;}}
        return true;
    }


    public void setLocationService(int visibility){
        if(visibility==0){locServiceText.setVisibility(View.GONE);}
        else {locServiceText.setVisibility(View.VISIBLE);}
    }

    public void setTypefaceForChosen(TextView chosenTextView){
        chosenTextView.setTextColor(getResources().getColor(R.color.ColorPrimary));
        chosenTextView.setTypeface(Typeface.DEFAULT_BOLD);
    }

    public void setTextStyle(TextView a, TextView b, TextView c){
        a.setTextColor(getResources().getColor(R.color.primary_dark_material_light));
        b.setTextColor(getResources().getColor(R.color.primary_dark_material_light));
        c.setTextColor(getResources().getColor(R.color.primary_dark_material_light));
        a.setTypeface(Typeface.DEFAULT);
        b.setTypeface(Typeface.DEFAULT);
        c.setTypeface(Typeface.DEFAULT);

    }

    public void getRadiusChanged(){
        switch (seekBar.getProgress()){
            case 0:
                locationRad = "25Km)";
                break;
            case 1:
                locationRad = "50Km)";
                break;
            case 2:
                locationRad = "75Km)";
                break;
            case 3:
                locationRad = "100Km)";
                break;
        }
    }


    private class AsyncTaskRunner extends AsyncTask<List<MarketPlaceData>, String, List<MarketPlaceData>> {

        @Override
        protected List<MarketPlaceData> doInBackground(List<MarketPlaceData>... params) {

            try {
                mData = MarketPlaceData.generateSampleData();
            }catch (Exception e){
                e.toString();
            }


            return mData;
        }

        @Override
        protected void onPostExecute(List<MarketPlaceData> result) {
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            stagAdapter2 = new MarketPlaceStaggeredAdapter(getActivity(),result,listingItemClickListener);
            mRecyclerView.setAdapter(stagAdapter2);
            mSwipeRefreshLayoutEmpty.setRefreshing(false);
            mSwipeRefreshLayoutEmpty.setVisibility(View.GONE);

            //
            tempdata = result;



        }

        @Override
        protected void onPreExecute() {
            mSwipeRefreshLayoutEmpty.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayoutEmpty.setRefreshing(true);
                }
            });
        }

        @Override
        protected void onProgressUpdate(String... text) {

        }
    }


}


package com.sinapp.sharathsind.tradepost;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.support.v7.app.AlertDialog;

import com.etsy.android.grid.StaggeredGridView;
import com.melnykov.fab.FloatingActionButton;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Model.HideScrollListener;
import Model.MarketPlaceData;
import Model.MarketPlaceListAdapter;
import Model.MarketPlaceStaggeredAdapter;
import Model.StaggeredAdapterTest;
import datamanager.Item;
import datamanager.ItemResult;
import datamanager.userdata;
import services.LocationGPSService;
import services.Mylocation;

/**
 * Created by HenryChiang on 15-06-25.
 */
public class MarketPlaceFragment extends Fragment {

    private StaggeredGridView mGridView;
    private RecyclerView mRecyclerView;
    private MarketPlaceStaggeredAdapter stagAdapter2;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private View rootView;
    private SwipeRefreshLayout mSwipeRefreshLayout, mSwipeRefreshLayoutEmpty;
    private StaggeredAdapterTest mStaggeredAdapterTest;

    private String locationRad = "25Km)";
    // int radius;
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
    private TextView headerRadText, radiusText, locServiceText, label25Km, label50Km, label75Km, label100Km;
    private LinearLayout seekBarLabel;
    TempMarketPlaceDataCallBack mCallback;
    View includeView;
    FloatingActionButton fab;


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
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.ColorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                AsyncTaskRunnerSRL runner = new AsyncTaskRunnerSRL();
                runner.execute();

            }
        });

        //
        mSwipeRefreshLayoutEmpty = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout_empty);


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

        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(fabOnClickListener);
        includeView = (View) rootView.findViewById(R.id.marketplace_header);
        headerRadText = (TextView) includeView.findViewById(R.id.marketplace_header_rad);
        includeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog();
            }
        });
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recylcer_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager d= new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(d);
        mRecyclerView.addOnScrollListener(new HideScrollListener(getContext(),d) {
            @Override
            public void onLoadMore(int current_page) {

            }

            @Override
            public void onHide() {
                includeView.animate().translationY(-includeView.getHeight()).setInterpolator(new AccelerateInterpolator(2));
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) fab.getLayoutParams();
                int fabBottomMargin = lp.bottomMargin;
                fab.animate().translationY(fab.getHeight() + fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
            }

            @Override
            public void onShow() {
                includeView.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
                fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
            }
        });
        applyStaggeredGridLayoutManager();




        /*
        mGridView = (StaggeredGridView)rootView.findViewById(R.id.recylcer_view);
        //MarketPlaceListAdapter adapter = new MarketPlaceListAdapter(getActivity(),R.layout.list_item_marketplace,R.id.looking_for,mData);
        //mGridView.setAdapter(adapter);
        mSwipeRefreshLayout.setVisibility(View.GONE);
        */


        mSwipeRefreshLayout.setVisibility(View.GONE);
        Criteria criteria = new Criteria();
        LocationManager lo = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        String provider = lo.getBestProvider(criteria, false);
        SharedPreferences prefs = getActivity().getSharedPreferences("loctradepost", getActivity().MODE_PRIVATE);
        String restoredText = prefs.getString("postalcode", null);
        userdata.mylocation = new Mylocation();

        userdata.mylocation.latitude = prefs.getFloat("lat", 0);//"No name defined" is the default value.
        userdata.mylocation.Longitude = prefs.getFloat("long", 0); //0 is the default value.
        radius = prefs.getInt("rad", 25);
        userdata.radius = radius;
        if (userdata.mylocation.latitude == 0 && userdata.mylocation.Longitude == 0) {
            Location location = lo.getLastKnownLocation(provider);
            if (location != null) {

                userdata.mylocation.latitude = (float) location.getLatitude();//"No name defined" is the default value.
                userdata.mylocation.Longitude = (float) location.getLongitude();
                Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(userdata.mylocation.Longitude, userdata.mylocation.latitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String cityName = addresses.get(0).getAddressLine(0);
                String stateName = addresses.get(0).getAddressLine(1);
                String countryName = addresses.get(0).getAddressLine(2);
            }
        }
        if (userdata.mylocation.Longitude != 0 && userdata.mylocation.latitude != 0) {
            AsyncTaskRunner runner = new AsyncTaskRunner();
            runner.execute();

        } else {
            customDialog();
        }










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


//        //Floating Action Button
//        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
//        fab.setOnClickListener(fabOnClickListener);
//
//        fab.attachToRecyclerView(mRecyclerView);
        //fab.attachToListView(mGridView);


        //Location



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
            Log.d("child position", String.valueOf(mRecyclerView.getChildLayoutPosition(v)));


            Intent i = new Intent(getActivity(), SingleListingActivity.class);
            ArrayList<String> clickedItemDetails = new ArrayList<>();

            i.putExtra("caller", "MarketPlace");

            clickedItemDetails.add(0, String.valueOf(mRecyclerView.getChildLayoutPosition(v)));
            i.putStringArrayListExtra("itemClicked", clickedItemDetails);

            //profile picture
            BitmapDrawable bitmapDrawable = (BitmapDrawable) ((ImageView) v.findViewById(R.id.pro_img)).getDrawable();
            Bitmap b = bitmapDrawable.getBitmap();
            i.putExtra("profilePic", b);

            startActivity(i);
        }
    };


    public void customDialog() {

        final View dialogView = li.inflate(location_dialog_layout, null, false);
        builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (rBLocSer.isChecked()) {
                    getRadiusChanged();
                    headerRadText.setText("(Location Service)");
                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
//                } else if (rBPostalCode.isChecked()) {
//                    getRadiusChanged();
//                    if (isPostalCodeValid(pCInputEdit.getText())) {
//                        Mylocation mylocation = LocationGPSService.getfromAdress(getActivity(), pCInputEdit.getText().toString());
//                        SharedPreferences.Editor editor = getActivity().getSharedPreferences("loctradepost", getActivity().MODE_PRIVATE).edit();
//                        editor.putInt("rad", radius);
//                        editor.putFloat("lat", mylocation.latitude);
//                        editor.putFloat("long", mylocation.Longitude);
//                        editor.putString("postalcode", pCInputEdit.getText().toString());
//
//                        editor.commit();
//                    } else {
//                        setPostalCodeError(pCInputEdit.getText());
//                    }
//                    headerRadText.setVisibility(View.VISIBLE);
//                    headerRadText.setText("(Within " + locationRad);
//                }
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
        //rBPostalCode = (RadioButton) dialogView.findViewById(R.id.radioButton_postalCode);
        rBLocSer = (RadioButton) dialogView.findViewById(R.id.radioButton_locService);
        //postalCodeInput = (TextInputLayout) dialogView.findViewById(R.id.dialog_postalCode);
        //pCInputEdit = (EditText) dialogView.findViewById(R.id.dialog_postalCode_edit);
        seekBar = (SeekBar) dialogView.findViewById(R.id.dialog_seekbar);
        radiusText = (TextView) dialogView.findViewById(R.id.dialog_radius_txt);
        locServiceText = (TextView) dialogView.findViewById(R.id.dialog_loc_text);
        seekBarLabel = (LinearLayout) dialogView.findViewById(R.id.dialog_seekbar_label);
        label25Km = (TextView) dialogView.findViewById(R.id.dialog_seekbar_25km);
        label25Km.setTextColor(getResources().getColor(R.color.ColorPrimary));
        label50Km = (TextView) dialogView.findViewById(R.id.dialog_seekbar_50km);
        label75Km = (TextView) dialogView.findViewById(R.id.dialog_seekbar_75km);
        label100Km = (TextView) dialogView.findViewById(R.id.dialog_seekbar_100km);

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

//        //check the zip/postal code input
//        pCInputEdit.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                setPostalCodeError(s);
//            }
//        });
//
//        rBPostalCode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                rBLocSer.setChecked(!isChecked);
//                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
//                if (pCInputEdit.isFocused() && pCInputEdit.getEditableText().length() != 0) {
//                    setPostalCodeError(pCInputEdit.getEditableText());
//                }
//                setPostalCodeInput(1);
//                setLocationService(0);
//
//            }
//        });
        rBLocSer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //rBPostalCode.setChecked(!isChecked);
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                //postalCodeInput.setErrorEnabled(false);
                //setPostalCodeInput(0);
                setLocationService(1);
            }
        });

        //initialize
        label25Km.setTypeface(Typeface.DEFAULT_BOLD);
       // setPostalCodeInput(0);
        setLocationService(0);
    }

//    public void setPostalCodeInput(int visibility) {
//        if (visibility == 0) {
//            postalCodeInput.setVisibility(View.GONE);
//            pCInputEdit.setVisibility(View.GONE);
//        } else {
//            postalCodeInput.setVisibility(View.VISIBLE);
//            pCInputEdit.setVisibility(View.VISIBLE);
//
//        }
//    }
//
//    //set different error message according to the input errors.
//    public void setPostalCodeError(Editable s) {
//        if (!isPostalCodeValid(s)) {
//            postalCodeInput.setError("Only Letters or Digits Are Allowed.");
//            postalCodeInput.setErrorEnabled(true);
//            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
//
//        } else if (!isPostalCodeValid(s) && pCInputEdit.length() != 6 && pCInputEdit.length() > 0) {
//            postalCodeInput.setError("Only Letters or Digits Are Allowed. Zip/Postal Code Too Short.");
//            postalCodeInput.setErrorEnabled(true);
//            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
//
//        } else if (pCInputEdit.length() != 6 && pCInputEdit.length() >= 0) {
//            postalCodeInput.setError("Zip/Postal Code Too Short.");
//            postalCodeInput.setErrorEnabled(true);
//            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
//        } else {
//            postalCodeInput.setErrorEnabled(false);
//            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
//
//        }
//
//    }
//
//    //check if zip/postal code input contains any not acceptable symbols.
//    public boolean isPostalCodeValid(Editable s) {
//        if (s.length() > 0 && !Character.isLetterOrDigit(s.charAt(pCInputEdit.length() - 1))) {
//            return false;
//        }
//        for (int i = 0; i < s.length(); i++) {
//            if (!Character.isLetterOrDigit(s.charAt(i))) {
//                return false;
//            }
//        }
//        return true;
//    }


    public void setLocationService(int visibility) {
        if (visibility == 0) {
            locServiceText.setVisibility(View.GONE);
        } else {
            locServiceText.setVisibility(View.VISIBLE);
        }
    }

    public void setTypefaceForChosen(TextView chosenTextView) {
        chosenTextView.setTextColor(getResources().getColor(R.color.ColorPrimary));
        chosenTextView.setTypeface(Typeface.DEFAULT_BOLD);
    }

    public void setTextStyle(TextView a, TextView b, TextView c) {
        a.setTextColor(getResources().getColor(R.color.primary_dark_material_light));
        b.setTextColor(getResources().getColor(R.color.primary_dark_material_light));
        c.setTextColor(getResources().getColor(R.color.primary_dark_material_light));
        a.setTypeface(Typeface.DEFAULT);
        b.setTypeface(Typeface.DEFAULT);
        c.setTypeface(Typeface.DEFAULT);

    }

    int radius;

    public void getRadiusChanged() {
        switch (seekBar.getProgress()) {
            case 0:
                locationRad = "25Km)";
                radius = 25;
                break;
            case 1:
                locationRad = "50Km)";
                radius = 50;
                break;
            case 2:
                locationRad = "75Km)";
                radius = 75;
                break;
            case 3:
                locationRad = "100Km)";
                radius = 100;
                break;
        }
    }


    private class AsyncTaskRunner extends AsyncTask<ArrayList<MarketPlaceData>, String, ArrayList<MarketPlaceData>> {

        @Override
        protected ArrayList<MarketPlaceData> doInBackground(ArrayList<MarketPlaceData>... params) {

            ArrayList<MarketPlaceData> mData;

            if (!mCallback.hasTempData()) {
                mData = MarketPlaceData.generateSampleData(getActivity().getApplicationContext());
                mCallback.setTempDataStatus(true);
            } else {
                mData = mCallback.getTempData();

            }


            return mData;
        }

        @Override
        protected void onPostExecute(ArrayList<MarketPlaceData> result) {


            /*
            MarketPlaceListAdapter adapter = new MarketPlaceListAdapter(getActivity(),R.layout.list_item_marketplace,R.id.looking_for,result,listingItemClickListener);
            mGridView.setAdapter(adapter);
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            mSwipeRefreshLayoutEmpty.setRefreshing(false);
            mSwipeRefreshLayoutEmpty.setVisibility(View.GONE);
            */


            //old


            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            stagAdapter2 = new MarketPlaceStaggeredAdapter(getActivity(), result, listingItemClickListener);
            mRecyclerView.setAdapter(stagAdapter2);
            mSwipeRefreshLayoutEmpty.setRefreshing(false);
            mSwipeRefreshLayoutEmpty.setVisibility(View.GONE);

            mCallback.storeTempMarketPlaceData(result);


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


    private class AsyncTaskRunnerSRL extends AsyncTask<ArrayList<MarketPlaceData>, String, ArrayList<MarketPlaceData>> {

        @Override
        protected ArrayList<MarketPlaceData> doInBackground(ArrayList<MarketPlaceData>... params) {
            ArrayList<MarketPlaceData> mData;
            mData = MarketPlaceData.generateSampleData(getActivity().getApplicationContext());
            mCallback.setTempDataStatus(true);
            return mData;
        }

        @Override
        protected void onPostExecute(ArrayList<MarketPlaceData> result) {

            MarketPlaceStaggeredAdapter m = new MarketPlaceStaggeredAdapter();
            m.updateList(result);
            mCallback.storeTempMarketPlaceData(result);
            mSwipeRefreshLayout.setRefreshing(false);
        }

        @Override
        protected void onPreExecute() {
            mSwipeRefreshLayout.post(new Runnable() {
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

    public interface TempMarketPlaceDataCallBack {

        void storeTempMarketPlaceData(ArrayList<MarketPlaceData> tempData);

        void setTempDataStatus(boolean status);

        boolean hasTempData();

        ArrayList<MarketPlaceData> getTempData();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (TempMarketPlaceDataCallBack) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement TempMarketPlaceDataCallBack");
        }
    }
}


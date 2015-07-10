package com.sinapp.sharathsind.tradepost;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by HenryChiang on 15-07-01.
 */
public class NotificationFragment extends Fragment {

    private View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_notification, container, false);
        return rootView;


    }

    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of NotificationFragment");
        super.onResume();
    }


    @Override
    public void onPause(){
        Log.e("DEBUG", "onPause of NotificationFragment");
        super.onPause();
    }

    @Override
    public void onStop(){
        Log.e("DEBUG", "onStop of NotificationFragment");
        super.onStop();
    }

    @Override
    public void onDestroyView(){
        Log.e("DEBUG", "onDestroyView of NotificationFragment");
        super.onDestroyView();
    }

    @Override
    public void onDestroy(){
        Log.e("DEBUG", "onDestroy of NotificationFragment");
        super.onDestroy();

    }



}
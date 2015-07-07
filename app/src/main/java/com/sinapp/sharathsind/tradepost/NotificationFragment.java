package com.sinapp.sharathsind.tradepost;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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

}
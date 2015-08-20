package com.sinapp.sharathsind.tradepost;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by HenryChiang on 15-08-11.
 */
public class SettingsFragment extends Fragment {


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().getFragmentManager().beginTransaction()
                .replace(R.id.settings_container, new MyPreferenceFragment())
                .commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_settings2, container, false);
        return rootView;

    }


    public static class MyPreferenceFragment extends PreferenceFragment {
        public MyPreferenceFragment() {}

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
            LayoutInflater inflator = LayoutInflater.from(getActivity());
            View v = inflator.inflate(R.layout.toolbar_custom_title, null);
            TextView title1 = (TextView) v.findViewById(R.id.toolbar_title1);
            TextView title2 = (TextView) v.findViewById(R.id.toolbar_title2);
            title1.setText("Settings");
            title2.setVisibility(View.GONE);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setCustomView(v);


        }
    }
}



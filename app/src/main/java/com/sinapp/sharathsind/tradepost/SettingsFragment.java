package com.sinapp.sharathsind.tradepost;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.FacebookActivity;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.plus.Plus;

import java.io.IOException;

import webservices.FavouriteWebService;

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


    public static class MyPreferenceFragment extends PreferenceFragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
        public GoogleApiClient mGoogleApiClient;
        public MyPreferenceFragment() {}


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
            LayoutInflater inflator = LayoutInflater.from(getActivity());
            View v = inflator.inflate(R.layout.toolbar_custom_title, null);
            TextView title1 = (TextView) v.findViewById(R.id.toolbar_title1);
            TextView title2 = (TextView) v.findViewById(R.id.toolbar_title2);
            Preference preference= (Preference) findPreference("Sign Out");
            mGoogleApiClient = new GoogleApiClient.Builder(this.getActivity())
                    .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                    .addOnConnectionFailedListener(this)
                    .addApi(Plus.API)
                    .addScope(Plus.SCOPE_PLUS_LOGIN)
                    .build();
            preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {


                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Constants.db=getActivity().openOrCreateDatabase("tradepostdb.db",getActivity(). MODE_PRIVATE, null);
              Cursor c=Constants.db.rawQuery("select * from login",null);
                    c.moveToFirst();
                    if(c.getString(c.getColumnIndex("itype")).equals("fb")) {

                        LoginManager l = LoginManager.getInstance();
                        l.logOut();


                    }
                    else if(c.getString(c.getColumnIndex("itype")).equals("g+")){
mGoogleApiClient.disconnect();

                    }
                    try {
                        InstanceID.getInstance(getActivity()).deleteInstanceID();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    c.close();
                    Constants.db.close();
                        getActivity().deleteDatabase("tradepostdb.db");
                    getActivity().startActivity(new Intent(getActivity(),Welcome.class));


                    getActivity().finish();
                    return false;
                }
            });
            Preference p= (Preference) findPreference("Invite Friend");
            p.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    FavouriteWebService.invite(getActivity());
                    return false;
                }
            });
            title1.setText("Settings");
            title2.setVisibility(View.GONE);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setCustomView(v);


        }

        @Override
        public void onConnected(Bundle bundle) {

        }

        @Override
        public void onConnectionSuspended(int i) {

        }

        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {

        }
    }
}



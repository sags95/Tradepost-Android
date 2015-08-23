package com.sinapp.sharathsind.tradepost;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


import com.google.android.gms.common.ConnectionResult;


import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

import Model.RegisterWebService;
import Model.Variables;
import datamanager.MyLocationService;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.plus.model.people.Person;

import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class FirstTime extends FragmentActivity implements OnClickListener,
        ConnectionCallbacks, OnConnectionFailedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new MyLocationService(this));

        FbFragment mainFragment;
        FbFragment.f = this;
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks((ConnectionCallbacks) this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
        if (savedInstanceState == null) {
            // Add the fragment on initial activity setup
            mainFragment = new FbFragment(this);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, mainFragment)
                    .commit();
        } else {
            // Or set the fragment from restored state info
            mainFragment = (FbFragment) getSupportFragmentManager()
                    .findFragmentById(android.R.id.content);
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnected()) {
                mGoogleApiClient.reconnect();
            }
        } else {
            FbFragment.b.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void start() {
        startActivity(new Intent(FirstTime.this, NavigationDrawer.class));
        finish();
    }

    private static final int PROFILE_PIC_SIZE = 400;
    private static final int RC_SIGN_IN = 0;


    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;


    /**
     * A flag indicating that a PendingIntent is in progress and prevents us
     * from starting further intents.
     */
    private boolean mIntentInProgress;

    private boolean mSignInClicked;

    private ConnectionResult mConnectionResult;

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // TODO Auto-generated method stub
        if (!mIntentInProgress) {
            if (mSignInClicked && result.hasResolution()) {
                // The user has already clicked 'sign-in' so we attempt to resolve all
                // errors until the user is signed in, or they cancel.
                try {
                    result.startResolutionForResult(this, RC_SIGN_IN);
                    mIntentInProgress = true;
                } catch (IntentSender.SendIntentException e) {
                    // The intent was canceled before it was sent.  Return to the default
                    // state and attempt to connect to get an updated ConnectionResult.
                    mIntentInProgress = false;
                    mGoogleApiClient.connect();
                }
            }
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            String personName = currentPerson.getDisplayName();
            Variables.username = personName;
            String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
            Variables.email = email;
            String personPhoto = currentPerson.getImage().getUrl();
            try {
                URL imageURL = new URL(personPhoto);
                Variables.profilepic = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String personGooglePlusProfile = currentPerson.getUrl();
            new AsyncTask<String,String,String>()
            {
                SQLiteDatabase myDB;
                ContentValues cv;
                @Override
                protected void onPostExecute(String s) {

                    super.onPostExecute(s);
                    Constants.db.insert("login",null,cv);
                    start();
                }

                @Override
                protected void onPreExecute() {


                    super.onPreExecute();

                }

                @Override
                protected String doInBackground(String... params) {

                 cv=   RegisterWebService.signUp(Variables.username, Variables.email, " ", "g+", Variables.profilepic, true, Constants.db);
                    return " ";

                }
            }.execute(null,null,null);

            start();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}

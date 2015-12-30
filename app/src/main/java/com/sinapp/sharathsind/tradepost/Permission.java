package com.sinapp.sharathsind.tradepost;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import datamanager.userdata;
import services.Mylocation;

/**
 * Created by sharathsind on 2015-10-29.
 */
public class Permission implements  ActivityCompat.OnRequestPermissionsResultCallback {
  Activity context;
  LocationManager locationManager
          ;

Permission(Activity c,LocationManager l)
{
    context=c;
    locationManager=l;
}
 public int checkPermission( String permissions)
 {
     int permissionCheck = ContextCompat.checkSelfPermission(context,permissions);
return permissionCheck;
 }
    public void askPermission(String permission[],int MY_PERMISSIONS_REQUEST_READ_CONTACTS){
        ActivityCompat.requestPermissions(context,
                permission,
                MY_PERMISSIONS_REQUEST_READ_CONTACTS);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode)
        {
            case 0:
                if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED&& checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED)
                {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, Welcome.service);
                    Criteria criteria = new Criteria();
                    String provider = locationManager.getBestProvider(criteria, false);
                    Location location = locationManager.getLastKnownLocation(provider);

                    if(location!=null) {
                        userdata.mylocation=new Mylocation();

                        userdata.mylocation.Longitude =(float) location.getLongitude();
                        userdata.mylocation.latitude = (float)location.getLatitude();
                        SharedPreferences.Editor editor = context.getSharedPreferences("loctradepost", context.MODE_PRIVATE).edit();
                        //editor.putInt("rad", radius);
                        editor.putFloat("lat", userdata.mylocation.latitude);
                        editor.putFloat("long",userdata. mylocation.Longitude);
                        editor.commit();



                    }

                }
                else{

                }







        }

    }
}

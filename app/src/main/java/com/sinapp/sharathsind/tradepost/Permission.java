package com.sinapp.sharathsind.tradepost;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by sharathsind on 2015-10-29.
 */
public class Permission {
  Activity context;

Permission(Activity c)
{
    context=c;
}
 public int checkPermission( String permissions)
 {
     int permissionCheck = ContextCompat.checkSelfPermission(context,permissions);
return permissionCheck;
 }
    public void askPermission(String permission,int MY_PERMISSIONS_REQUEST_READ_CONTACTS){
        ActivityCompat.requestPermissions(context,
                new String[]{permission},
                MY_PERMISSIONS_REQUEST_READ_CONTACTS);

    }



}

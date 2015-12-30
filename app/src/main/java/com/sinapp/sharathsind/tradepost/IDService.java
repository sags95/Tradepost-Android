package com.sinapp.sharathsind.tradepost;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.iid.InstanceIDListenerService;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.io.IOException;

import webservices.MainWebService;

public class IDService extends InstanceIDListenerService {
    public IDService() {
    }

    @Override
    public void onTokenRefresh() {

        super.onTokenRefresh();
        if(Constants.db==null||!Constants.db.isOpen())
        {
            Constants.db=openOrCreateDatabase("tradepost.db",MODE_PRIVATE,null);
        }
        InstanceID instanceID=InstanceID.getInstance(getApplicationContext());

    Cursor    c=Constants.db.rawQuery("select * from login", null);
        c.moveToFirst();
        Constants.userid=c.getInt(c.getColumnIndex("userid"));
        c=Constants.db.rawQuery("select * from gcm", null);

        if(c.getCount()>0) {
            c.moveToFirst();
            Constants.GCM_Key = c.getString(0);

        }
        Constants.db.execSQL("truncate gcm");
        String token = null;
        try {
            token = instanceID.getToken("923650940708",
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE);
        } catch (IOException e) {
            e.printStackTrace();
        }
       // Constants.GCM_Key = token;
        ContentValues cv=new ContentValues();
        cv.put("gcmkey", token);
        Constants.db.insert("GCM", null, cv);

        SoapObject soapObject=new SoapObject("http://webser/","change");
        soapObject.addProperty("old",Constants.GCM_Key);
        soapObject.addProperty("new",token);
        soapObject.addProperty("userid",Constants.userid);
        SoapPrimitive p= MainWebService.getMsg(soapObject,"http://73.37.238.238:8084/TDserverWeb/NewWebServi?wsdl","http://webser/NewWebservi/changeRequest");
    }
}

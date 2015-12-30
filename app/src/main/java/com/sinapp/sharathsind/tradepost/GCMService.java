package com.sinapp.sharathsind.tradepost;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;

import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.analytics.ExceptionReporter;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.gcm.GcmListenerService;

import com.google.android.gms.gcm.GcmReceiver;
import com.sinapp.sharathsind.tradepost.ChatFragment;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.util.Vector;

import webservices.MainWebService;


/**
 * Created by sharathsind on 2015-06-01.
 */
public class GCMService extends GcmListenerService {

    public static boolean b;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);
        TradePost application = (TradePost) getApplication();
        Tracker mTracker = application.getDefaultTracker();
        //ctivity().getApplication()).getTracker(TrackerName.APP_TRACKER);

// Build and send exception.
        Thread.UncaughtExceptionHandler myHandler = new ExceptionReporter(
                mTracker,                                        // Currently used Tracker.
                Thread.getDefaultUncaughtExceptionHandler(),      // Current default uncaught exception handler.
                this);                                         // Context of the application.

// Make myHandler the new default uncaught exception handler.
        Thread.setDefaultUncaughtExceptionHandler(myHandler);
String message=data.getString("type");
switch (message)
{
     case "offer":
int offerid=Integer.parseInt(data.getString("offerid"));
         int notid=Integer.parseInt(data.getString("notid"));
 String msg =data.getString("message");

        long res= insertNotification(msg,offerid,0,notid);
         if(res!=-1)
            notifyOffer(offerid,msg);
     break;
    case "aoffer":
         offerid=Integer.parseInt(data.getString("offerid"));
        notid=Integer.parseInt(data.getString("notid"));
         msg =data.getString("message");
     res=   insertNotification(msg,offerid,1,notid);
        if(res!=-1)
        aOffer(offerid,msg);
        break;
    case "doffer":

        offerid=Integer.parseInt(data.getString("offerid"));
         notid=Integer.parseInt(data.getString("notid"));
        msg =data.getString("message");

        res=insertNotification(msg,offerid,2,notid);
        if(res!=-1)
        dOffer(offerid,msg);
        break;
    case "mesage":
        offerid=Integer.parseInt(data.getString("offerid"));
        msg =data.getString("message");

        String pic=null;

        message(offerid, msg,pic,data);

        break;
    case "seen":
        break;
    case "cancel":
        break;
    case "block":
        break;
    case "feedback":
        break;
}
    }

    private void message(int offerid, String msg,String p,Bundle data) {
        int mesgid=Integer.parseInt(data.getString("messageid"));
        SQLiteDatabase db=  openOrCreateDatabase("tradepostdb.db", MODE_PRIVATE, null);
        Cursor cursor= null;
        try {
            cursor = db.rawQuery("select * from m" + offerid + " where msgid = " + mesgid, null);
        } catch (Exception e) {

            db.execSQL("create table m" + offerid + "(msgid INTEGER PRIMARY KEY,msg varchar,msgpath varchar,seen DATETIME,sent DATETIME,userid int(10),ruserid int(10)) ");
            db.execSQL("update offers set status=1 where offerid="+offerid);

        }

        if(cursor!=null&&cursor.getCount()>0)
        if(cursor!=null&&cursor.getCount()>0)
        {
            db.close();
            cursor.close();
            return;
        }
        else {
            ContentValues cv = new ContentValues();
            cv.put("msgid",mesgid);
            cv.put("msg",msg);
            cv.put("ruserid",data.getString("ruserid"));
            cv.put("userid",data.getString("userid"));
            cv.put("sent",data.getString("sent"));
            cv.put("msgpath",data.getString("picture",""));
            cursor.close();
            db.insert("m"+offerid,"seen",cv);
            db.close();
            if(ChatFragment.isAlive)
            {

                sendBroadcast(new Intent("com.sinapp.sharathsind.chat."+offerid));
            }
else{
                Intent i=new Intent(this,ChatFragment.class);
                i.putExtra("offerid",offerid);
                PendingIntent resultPendingIntent =
                        PendingIntent.getActivity(
                                this,
                                0,i,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                db.close();
                buildNotification(resultPendingIntent, msg, 1);
            }


      // /
      // db.execSQL("create table m"+offerid+"(msgid int(10),msg varchar,msgpath varchar,seen DATETIME,sent DATETIME,userid int(10),ruserid int(10)) ");

        }














    }

    public void notifyOffer(int offerid,String msg)
{
    SoapObject obje =new SoapObject("http://webser/","getItemsOffer");
    obje.addProperty("offerid",offerid);
    SoapObject obje1 =new SoapObject("http://webser/","getOffer");
    obje1.addProperty("offerid",offerid);
    KvmSerializable s= MainWebService.getMsg2(obje1,"http://73.37.238.238:8084/TDserverWeb/OfferWebService?wsdl","http://webser/OfferWebService/getOfferRequest");
    ContentValues cv=new ContentValues();
    SQLiteDatabase db=  openOrCreateDatabase("tradepostdb.db",MODE_PRIVATE,null);

    SoapObject offer= (SoapObject) ((SoapObject)s).getProperty("of");
    cv.put("offerid",offer.getPropertyAsString("offerid"));
    cv.put("userid",offer.getPropertyAsString("userid"));
    cv.put("itemid",offer.getPropertyAsString("itemid"));
    cv.put("cash",offer.getPropertyAsString("cash"));
    cv.put("recieveduserid",offer.getPropertyAsString("recieveduserid"));
    cv.put("status",offer.getPropertyAsString("status"));
    cv.put("dir", offer.getPropertyAsString("dir"));
    //cv.put("dir");

        db.insert("offers", null, cv);
if(((SoapObject )s).hasProperty("item"))
{
cv=new ContentValues();
    if(((SoapObject)(((SoapObject) s).getProperty("item"))).getPropertyCount()>0) {
        cv.put("Offerid", offerid);
        cv.put("itemname",((SoapObject)(((SoapObject) s).getProperty("item"))).getPropertyAsString("itemName") );
   db.insert("offeradditionalitems",null,cv);
    }

}
    Vector s1=MainWebService.getMsg1(obje,"http://73.37.238.238:8084/TDserverWeb/OfferWebService?wsdl","http://webser/OfferWebService/getItemsOfferRequest");
for(Object c:s1) {
    cv = new ContentValues();
    cv.put("offerid",offerid);
    cv.put("itemid", Integer.parseInt(c.toString()));
db.insert("offeritems",null,cv);
}
if(!b)
{
    Intent i=new Intent(this,notificationoffertesting.class);
    i.putExtra("offerid",offerid);
    i.putExtra("itemname", (((SoapObject) s).getProperty("itemname")).toString());
    PendingIntent resultPendingIntent =
            PendingIntent.getActivity(
                    this,
                    0,i,
                    PendingIntent.FLAG_CANCEL_CURRENT
            );
db.close();
    buildNotification(resultPendingIntent, msg, 1);
}
    else{





}




}
    public void aOffer(int offerid,String msg)
    {
        SoapObject obje =new SoapObject("http://webser/","getItemsOffer");
        obje.addProperty("offerid",offerid);
        SoapObject obje1 =new SoapObject("http://webser/","getOffer");
        obje1.addProperty("offerid",offerid);
        KvmSerializable s= MainWebService.getMsg2(obje1,"http://73.37.238.238:8084/TDserverWeb/OfferWebService?wsdl","http://webser/OfferWebService/getOfferRequest");
        ContentValues cv=new ContentValues();
        SQLiteDatabase db=  Constants.db=openOrCreateDatabase("tradepostdb.db",MODE_PRIVATE,null);
        db.execSQL("create table m"+offerid+"(msgid INTEGER PRIMARY KEY,msg varchar,msgpath varchar,seen DATETIME,sent DATETIME,userid int(10),ruserid int(10)) ");

        SoapObject offer= (SoapObject) ((SoapObject)s).getProperty("of");
        Cursor c1=db.rawQuery("select * from offers where offerid="+offer.getPropertyAsString("offerid"),null);

        cv.put("offerid",offer.getPropertyAsString("offerid"));
        cv.put("userid",offer.getPropertyAsString("userid"));
        cv.put("itemid",offer.getPropertyAsString("itemid"));
        cv.put("cash",offer.getPropertyAsString("cash"));
        cv.put("recieveduserid",offer.getPropertyAsString("recieveduserid"));
        cv.put("status",offer.getPropertyAsString("status"));
        cv.put("dir", offer.getPropertyAsString("dir"));
        //cv.put("dir");
if(c1.getCount()==0) {
    db.insert("offers", null, cv);
    if (((SoapObject) s).hasProperty("item")) {
        cv = new ContentValues();
        if (((SoapObject) (((SoapObject) s).getProperty("item"))).getPropertyCount() > 0) {
            cv.put("Offerid", offerid);
            cv.put("itemname", ((SoapObject) (((SoapObject) s).getProperty("item"))).getPropertyAsString("itemname"));
            db.insert("offeradditionalitems", null, cv);
        }

    }
    Vector s1 = MainWebService.getMsg1(obje, "http://73.37.238.238:8084/TDserverWeb/OfferWebService?wsdl", "http://webser/OfferWebService/getItemsOfferRequest");
    for (Object c : s1) {
        cv = new ContentValues();
        cv.put("offerid", offerid);
        cv.put("itemid", Integer.parseInt(c.toString()));
        db.insert("offeritems", null, cv);
    }

}
        else{
    db.execSQL("update offers set status=1 where offerid="+offerid);
        }
        if(!b)
        {
            Intent i=new Intent(this,ChatFragment.class);
            i.putExtra("offerid",offerid);
            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            this,
                            0,i,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
           db.close();
            buildNotification(resultPendingIntent,msg,1);
        }
        else{





        }
if(!c1.isClosed())
    c1.close();



    }
    public void dOffer(int offerid,String msg)
    {
        SoapObject obje =new SoapObject("http://webser/","getItemsOffer");
        obje.addProperty("offerid",offerid);
        SoapObject obje1 =new SoapObject("http://webser/","getOffer");
        obje.addProperty("offerid",offerid);
        KvmSerializable s= MainWebService.getMsg2(obje1,"http://73.37.238.238:8084/TDserverWeb/OfferWebService?wsdl","http://webser/OfferWebService/getOfferRequest");
        ContentValues cv=new ContentValues();
        SQLiteDatabase db=  Constants.db=openOrCreateDatabase("tradepostdb.db",MODE_PRIVATE,null);

        SoapObject offer= (SoapObject) ((SoapObject)s).getProperty("of");
        Cursor c1=db.rawQuery("select * from offers where offerid="+offer.getPropertyAsString("offerid"),null);

        cv.put("offerid",offer.getPropertyAsString("offerid"));
        cv.put("userid",offer.getPropertyAsString("userid"));
        cv.put("itemid",offer.getPropertyAsString("itemid"));
        cv.put("cash",offer.getPropertyAsString("cash"));
        cv.put("recieveduserid",offer.getPropertyAsString("recieveduserid"));
        cv.put("status",offer.getPropertyAsString("status"));
        cv.put("dir", offer.getPropertyAsString("dir"));
        //cv.put("dir");
        if(c1.getCount()==0) {
            db.insert("offers", null, cv);
            if (((SoapObject) s).hasProperty("item")) {
                cv = new ContentValues();
                if (((SoapObject) (((SoapObject) s).getProperty("item"))).getPropertyCount() > 0) {
                    cv.put("Offerid", offerid);
                    cv.put("itemname", ((SoapObject) (((SoapObject) s).getProperty("item"))).getPropertyAsString("itemname"));
                    db.insert("offeradditionalitems", null, cv);
                }

            }
            Vector s1 = MainWebService.getMsg1(obje, "http://73.37.238.238:8084/TDserverWeb/OfferWebService?wsdl", "http://webser/OfferWebService/getItemsOfferRequest");
            for (Object c : s1) {
                cv = new ContentValues();
                cv.put("offerid", offerid);
                cv.put("itemid", Integer.parseInt(c.toString()));
                db.insert("offeritems", null, cv);
            }

        }
        else{
            db.execSQL("update set status=2 where offerid="+offerid);
        }

            Intent i=new Intent(this,notificationoffertesting.class);
            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            this,
                            0,i,
                            PendingIntent.FLAG_CANCEL_CURRENT
                    );
db.close();
            buildNotification(null, msg, 1);
if(!c1.isClosed())
    c1.close();











    }

    public void buildNotification(PendingIntent intent,String msg,int id)
{
    NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.tradepost_logo)
                    .setContentTitle("Tradepost").setSound( RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentText(msg).setAutoCancel(true);
    int mNotificationId = id;
if(intent!=null)
{
mBuilder.setContentIntent(intent);

}
// Gets an instance of the NotificationManager service
    NotificationManager mNotifyMgr =
            (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
    mNotifyMgr.notify(mNotificationId, mBuilder.build());

}
    public void buildNotificationmsg(PendingIntent intent,String msg,int id)
    {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.tradepost_logo)
                        .setContentTitle("Tradepost").setSound( RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setContentText(msg).setAutoCancel(true);
        int mNotificationId = id;
        if(intent!=null)
        {
            mBuilder.setContentIntent(intent);

        }
// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());

    }
public  long insertNotification(String msg , int offerid,int type,int id)
{
    SQLiteDatabase db=openOrCreateDatabase("tradepostdb.db",MODE_PRIVATE,null);
Cursor x=db.rawQuery("SELECT *\n" +
        "FROM notifications\n" +
        "ORDER BY notificationid  DESC\n" +
        "LIMIT 1", null);


    ContentValues cv=new ContentValues();
    cv.put("msg", msg);
    cv.put("offerid",offerid);
    cv.put("status", 0);
    cv.put("type", type);
    if(x.getCount()==0)
        cv.put("notificationid",id);
    else {
        x.moveToFirst();
        int i=x.getInt(x.getColumnIndex("notificationid"));
        i++;
                cv.put("notificationid", id);

    }
        long l=db.insert("notifications",null,cv);
    if(!x.isClosed())
        x.close();
    return l;

}

}








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
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;

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
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);

String message=data.getString("type");
switch (message)
{
     case "offer":
int offerid=Integer.parseInt(data.getString("offerid"));
 String msg =data.getString("message");
notifyOffer(offerid,msg);
     break;
    case "aoffer":
         offerid=Integer.parseInt(data.getString("offerid"));
         msg =data.getString("message");
        aOffer(offerid,msg);
        break;
    case "doffer":
        offerid=Integer.parseInt(data.getString("offerid"));
        msg =data.getString("message");
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
        Cursor cursor=db.rawQuery("select * from m"+offerid+" where msgid = "+mesgid,null);

        if(cursor.getCount()>0)
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
    SQLiteDatabase db=  Constants.db=openOrCreateDatabase("tradepostdb.db",MODE_PRIVATE,null);

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
    i.putExtra("itemname",(((SoapObject)s).getProperty("itemname")).toString());
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
        db.execSQL("create table m"+offerid+"(msgid int(10),msg varchar,msgpath varchar,seen DATETIME,sent DATETIME,userid int(10),ruserid int(10)) ");

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












    }

    public void buildNotification(PendingIntent intent,String msg,int id)
{
    NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle("Tradepost")
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
    }








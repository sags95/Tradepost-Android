package com.sinapp.sharathsind.tradepost;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.SoapObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import Model.ChatPageItem;
import webobjects.Message;
import webservices.MainWebService;
import webservices.MessageWebService;
import webservices.NotificationandMessageService;

public class MyService extends WakefulIntentService {
    int userid;
    public MyService() {
        super("Myservice");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        doWakefulWork(intent);
        return START_REDELIVER_INTENT;

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }



@Override
    void doWakefulWork(Intent intent) {
       Thread t=new Thread(new MessageLoader());
        t.start();
Thread t1=new Thread(new NotificationLoader());
        t1.start();
    }
    class MessageLoader  implements Runnable
    {

        @Override
        public void run(){
                searchMesage();
        }
        public  void searchMesage()
        {
            List<ChatPageItem> items = new ArrayList<ChatPageItem>();
            MessageWebService m=new MessageWebService();
            SQLiteDatabase db=openOrCreateDatabase("tradepostdb.db", MODE_PRIVATE, null);
            Cursor cv=db.rawQuery("select * from offers where status=1", null);

                  if(cv.getCount()>0){
            cv.moveToFirst();
            while(!cv.isAfterLast()) {
                ArrayList<Message>m1=new ArrayList<>();
               int offerid= cv.getInt(cv.getColumnIndex("Offerid"));
                Cursor cv1=db.rawQuery("SELECT *\n" +
                        "FROM m" +offerid+
                        " ORDER BY msgid DESC\n" +
                        "LIMIT 1",null);
                if(cv1.getCount()>0)
                {
                    cv1.moveToFirst();
                    int maxid=cv1.getInt(0);
                    int maxserverid=m.maxId(offerid);
                    cv1.close();
                    while (maxid!=maxserverid)
                    {
                        maxid++;
                        SoapObject request=new SoapObject("http://webser/","getmessage");
                        request.addProperty("offerid",offerid);
                        request.addProperty("id",maxid);
                        KvmSerializable soapObject=MainWebService.getMsg2(request,"http://73.37.238.238:8084/TDserverWeb/MessageandNotification?wsdl","http://webser/MessageandNotification/getmessageRequest");
                        if(soapObject!=null)
                        get((SoapObject)soapObject,offerid);
                    }


                }
            }
        }
            cv.close();
            db.close();

        }


        public  void get(SoapObject result,int offerid) {
            ContentValues cv = new ContentValues();
            //   ContentValues cv = new ContentValues();

            SQLiteDatabase db = Constants.db;
            cv.put("msgid", result.getProperty("messageid").toString());
            cv.put("msg", result.getProperty("message").toString());
            cv.put("ruserid", result.getProperty("ruserid").toString());
            cv.put("userid", result.getProperty("userid").toString());
            cv.put("sent", result.getProperty("sent").toString());
            cv.put("msgpath", result.getProperty("messagepath").toString());

            long l= db.insert("m" + offerid, "seen", cv);
            if(l!=-1)
            {
                if(ChatFragment.isAlive)
                {

                    sendBroadcast(new Intent("com.sinapp.sharathsind.chat."+offerid));
                }
                else{
                    Intent i=new Intent(MyService.this,ChatFragment.class);
                    i.putExtra("offerid",offerid);
                    PendingIntent resultPendingIntent =
                            PendingIntent.getActivity(
                                   MyService. this,
                                    0,i,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                            );
                    db.close();
                    buildNotificationmsg(resultPendingIntent, result.getProperty("message").toString(), 1);
                }



            }
            l++;
            // db.close();
        }

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
            buildNotificationmsg(resultPendingIntent, msg, 1);





    }
    public void aOffer(int offerid,String msg)
    {
        SoapObject obje =new SoapObject("http://webser/","getItemsOffer");
        obje.addProperty("offerid",offerid);
        SoapObject obje1 =new SoapObject("http://webser/","getOffer");
        obje1.addProperty("offerid", offerid);
        KvmSerializable s= MainWebService.getMsg2(obje1,"http://73.37.238.238:8084/TDserverWeb/OfferWebService?wsdl","http://webser/OfferWebService/getOfferRequest");
        ContentValues cv=new ContentValues();
        SQLiteDatabase db=  Constants.db=openOrCreateDatabase("tradepostdb.db",MODE_PRIVATE,null);
        db.execSQL("create table m" + offerid + "(msgid INTEGER PRIMARY KEY,msg varchar,msgpath varchar,seen DATETIME,sent DATETIME,userid int(10),ruserid int(10)) ");

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
 c1.close();
            Intent i=new Intent(this,ChatFragment.class);
            i.putExtra("offerid",offerid);
            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            this,
                            0,i,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            db.close();
            buildNotificationmsg(resultPendingIntent,msg,1);





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
        c1.close();
        db.close();
        buildNotificationmsg(null, msg, 1);












    }

class NotificationLoader implements Runnable

{

    @Override
    public void run() {
searchMesage();
    }
    public  void searchMesage()
    {
        List<ChatPageItem> items = new ArrayList<ChatPageItem>();
        NotificationandMessageService m=new NotificationandMessageService();
        SQLiteDatabase db=openOrCreateDatabase("tradepostdb.db", MODE_PRIVATE, null);
                Cursor cv1=db.rawQuery("SELECT *\n" +
                        "FROM notifications" +
                        " ORDER BY notificationid DESC\n" +
                        "LIMIT 1", null);
                if(cv1.getCount()>0)
                {
                    cv1.moveToFirst();
                    int maxid=cv1.getInt(cv1.getColumnIndex("notificationid"));
                    int maxserverid=m.maxId();
                    cv1.close();
                    while (maxid!=maxserverid)
                    {
                        maxid++;
                        SoapObject request=new SoapObject("http://webser/","getNotification");
                        request.addProperty("userid",maxid);
                        request.addProperty("id",Constants.userid);
                        KvmSerializable soapObject=MainWebService.getMsg2(request,"http://73.37.238.238:8084/TDserverWeb/MessageandNotification?wsdl","http://webser/MessageandNotification/getNotificationRequest");
                        try {
                            setnot((SoapObject) soapObject);
                        }
                        catch (Exception e)
                        {

                        }

                    }


                }
        else{
                    int maxid=0;
                    int maxserverid=m.maxId();
                    cv1.close();
                    while (maxid!=maxserverid)
                    {

                        SoapObject request=new SoapObject("http://webser/","getNotification");
                        request.addProperty("userid",Constants.userid);
                        request.addProperty("id",maxid);
                        KvmSerializable soapObject=MainWebService.getMsg2(request,"http://73.37.238.238:8084/TDserverWeb/MessageandNotification?wsdl","http://webser/MessageandNotification/getNotificationRequest");

                     if (soapObject!=null)
                         try {
                             setnot((SoapObject) soapObject);
                         }
                         catch (Exception e)
                         {

                         }
                        maxid++;
                    }
                }
            }


    public  void  setnot(SoapObject result)
    {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = Constants.db;

        cv.put("notificationid", Integer.parseInt(result.getProperty("id").toString()));
        cv.put("offerid", Integer.parseInt(result.getProperty("offerid").toString()));
        cv.put("msg", result.getProperty("message").toString());
        cv.put("type", Integer.parseInt(result.getProperty("type").toString()));


        cv.put("status",Integer.parseInt( result.getProperty("status").toString()));
        long l= db.insert("notifications" , null, cv);
        if(l!=-1)
        {
            int type= Integer.parseInt(result.getProperty("type").toString());
            switch (type)
            {
                case 0:
                    notifyOffer(Integer.parseInt(result.getProperty("offerid").toString()),result.getProperty("message").toString());
                    break;
                case 1:
                    aOffer(Integer.parseInt(result.getProperty("offerid").toString()),result.getProperty("message").toString());

                    break;
                case 2:
                    dOffer(Integer.parseInt(result.getProperty("offerid").toString()),result.getProperty("message").toString());

                    break;
            }
        }
        l++;
    }





}
}

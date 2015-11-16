package services;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.IBinder;

import com.sinapp.sharathsind.tradepost.Constants;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import Model.ChatPageItem;
import datamanager.userdata;
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
        return Service.START_STICKY;
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
        public void run() {
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
               int offerid= cv.getInt(cv.getColumnIndex("offerid"));
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
            l++;
            // db.close();
        }

    }

class NotificationLoader implements Runnable

{

    @Override
    public void run() {

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
                    int maxid=cv1.getInt(0);
                    int maxserverid=m.maxId();
                    cv1.close();
                    while (maxid!=maxserverid)
                    {
                        maxid++;
                        SoapObject request=new SoapObject("http://webser/","getNotification");
                        request.addProperty("userid",Constants.userid);
                        request.addProperty("id",maxid);
                        KvmSerializable soapObject=MainWebService.getMsg2(request,"http://73.37.238.238:8084/TDserverWeb/MessageandNotification?wsdl","http://webser/MessageandNotification/getNotificationRequest");
                        setnot((SoapObject)soapObject);


                    }


                }
            }


    public  void  setnot(SoapObject result)
    {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = Constants.db;
        cv.put("notificationid", result.getProperty("id").toString());
        cv.put("offerid", result.getProperty("offerid").toString());
        cv.put("msg", result.getProperty("message").toString());
        cv.put("type", result.getProperty("type").toString());
        cv.put("status", result.getProperty("status").toString());
        long l= db.insert("notifications" , null, cv);
        l++;
    }





}
}

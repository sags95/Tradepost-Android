package services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.sinapp.sharathsind.tradepost.Constants;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.SoapObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Model.ChatPageItem;
import webobjects.Message;
import webservices.MainWebService;
import webservices.MessageWebService;
import webservices.NotificationandMessageService;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class OffersMessage extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "services.action.FOO";
    private static final String ACTION_BAZ = "services.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "services.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "services.extra.PARAM2";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_REDELIVER_INTENT;
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, OffersMessage.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, OffersMessage.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public OffersMessage() {
        super("OffersMessage");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }
    void doWakefulWork(Intent intent) {

        android.os.Debug.waitForDebugger();
        File sdcard = Environment.getExternalStorageDirectory();
// to this path add a new directory path
        File dir = new File(sdcard.getAbsolutePath()+"/trade/" );
// create this directory if not already created
        dir.mkdir();
// create the file in which we will write the contents
        File file = new File(dir, "tradepost.txt");
        FileOutputStream os= null;
        try {
            os = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String data = "This is the content of my file"+ Calendar.getInstance().toString();
        try {
            os.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread t=new Thread(new MessageLoader());
        t.start();
        Thread t1=new Thread(new NotificationLoader());
        t1.start();

    }
    class MessageLoader  implements Runnable
    {

        @Override
        public void run() {
            while(true) {


                searchMesage();
                searchMessage();
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }



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
                            KvmSerializable soapObject= MainWebService.getMsg2(request, "http://73.37.238.238:8084/TDserverWeb/MessageandNotification?wsdl", "http://webser/MessageandNotification/getmessageRequest");
                            get((SoapObject)soapObject,offerid);


                        }


                    }
                }
            }
            cv.close();
            db.close();

        }
        public  void searchMessage()
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
    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
       doWakefulWork(null);
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

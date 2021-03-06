package com.sinapp.sharathsind.tradepost;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract.CommonDataKinds.Relation;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.squareup.picasso.Picasso;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import Model.MarketPlaceData;
import Model.RegisterWebService;
import Model.Variables;
import datamanager.Item;
import datamanager.ItemResult;
import datamanager.MyLocationService;
import datamanager.userdata;
import services.MarketPlaceIntentService;
import services.Mylocation;
import webservices.MainWebService;


public class Welcome extends Activity implements OnClickListener {

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    Cursor c;
    private TextView title;
    InstanceID instanceID;
    public static LocationManager locationManager;
    public static MyLocationService service;
    public static boolean isDatabaseExist= false;
    public static Context context;
    Permission permission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        context=Welcome.this;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        service=new MyLocationService(this);


        boolean b = doesDatabaseExist(new ContextWrapper(getBaseContext()), "tradepostdb.db");

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        permission=new Permission(this,locationManager);

        if(permission.checkPermission(com.sinapp.sharathsind.tradepost.Manifest.permission.C2D_MESSAGE)!=PackageManager.PERMISSION_GRANTED) {

            permission.askPermission(new String[]{com.sinapp.sharathsind.tradepost.Manifest.permission.C2D_MESSAGE},0);


        }
        if(permission.checkPermission(Manifest.permission.WAKE_LOCK)!=PackageManager.PERMISSION_GRANTED) {

                permission.askPermission(new String[]{Manifest.permission.WAKE_LOCK},0);


        }
        if(permission.checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED||permission.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {

        permission.askPermission(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},0);

        }
        else {

        }


        if(b) {
            isDatabaseExist=true;
            try{
                Constants.db=openOrCreateDatabase("tradepostdb.db",MODE_PRIVATE,null);
                c=Constants.db.rawQuery("select * from login",null);
                c.moveToFirst();
                Constants.userid=c.getInt(c.getColumnIndex("userid"));
                Variables.email=c.getString(c.getColumnIndex("email"));
                Variables.username=c.getString(c.getColumnIndex("username"));
                userdata.name=Variables.username;
                userdata.userid=Constants.userid;

                c=Constants.db.rawQuery("select * from gcm",null);

                if(c.getCount()>0) {
                    c.moveToFirst();
                    Constants.GCM_Key = c.getString(0);

                }

                new AsyncTask<String,String,String>() {
                    ProgressDialog pd;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        pd=new ProgressDialog(Welcome.this);
                        pd.setMessage("Loading...");
                        pd.setCancelable(false);
                        pd.show();

                    }

                    @Override
                    protected void onPostExecute(String s) {

                        super.onPostExecute(s);
                        if(c.getCount()>0)
                        {
                            startActivity(new Intent(Welcome.this, NavigationDrawer.class));
                            c.close();
                            finish();
                            //  locationManager.removeUpdates(service);
                        }
                        pd.dismiss();

                    }

                    @Override
                    protected String doInBackground(String... params) {
                        SoapObject object = new SoapObject("http://webser/", "getuseritems");
                        //SoapObject object = new SoapObject("http://webser/", "getuseritems");
                        object.addProperty("userid",  userdata.userid);
                        Vector object1 = MainWebService.getMsg1(object,"http://73.37.238.238:8084/TDserverWeb/Search?wsdl","http://webser/Search/getuseritemsRequest");
                        userdata.items=new ArrayList<Integer>();


                        if(object1!=null) {
                            for (Object i : object1) {
                                userdata.items.add(Integer.parseInt(((SoapPrimitive) i).getValue().toString()));
                            }
                        }
                    userdata.i=new ArrayList<ItemResult>();

                    for(int i :userdata.items) {

                       SoapObject  obje=new SoapObject("http://webser/","getItembyId");
                        obje.addProperty("itemid", i);
                        KvmSerializable result1= MainWebService.getMsg2(obje,"http://73.37.238.238:8084/TDserverWeb/GetItems?wsdl"
                                ,"http://webser/GetItems/getItembyIdRequest");

                        ItemResult ir= new ItemResult();
                        ir.item=new Item();

                        SoapObject object12=(SoapObject)result1.getProperty(0);
                        //for(int u=0;u<object.getPropertyCount())
                        ir.item.set(object12);
                        //SoapObject o7=(SoapObject)result1;
                        //Object j=       o.getProperty("images");
                        int i1=result1.getPropertyCount();
                        ir.images=new String[i1-1];

                        for(int u1=1;u1<i1;u1++) {
                            ir.images[u1-1]=  result1.getProperty(u1).toString();

                        }
                        obje=new SoapObject("http://webser/","searchbyint");
                        obje.addProperty("name", i);
                        Vector result2= MainWebService.getMsg1(obje, "http://73.37.238.238:8084/TDserverWeb/NewWebService?wsdl"
                                , "http://webser/NewWebService/searchbyintRequest");
                        if(result2!=null) {

                            int index=0;
                            ir.tags=new String[result2.size()];

                            for (Object o:result2 ) {
                                ir.tags[index] = ((SoapPrimitive)o).getValue().toString();
                                index++;

                            }
                        }

                        userdata.i.add(ir);

                        }
                        return null;
                    }
                }.execute(null,null,null);

                //URL url = new URL("http://73.37.238.238:8084/TDserverWeb/images/"+Constants.userid+"/profile.png");

                //Variables.profilepic = Picasso.with(this).load(Uri.parse("http://73.37.238.238:8084/TDserverWeb/images/"+Constants.userid+"/profile.png")).get();
                //Constants.username=c.getString(c.getColumnIndex("username"));


            } catch(Exception e) {
                String s=e.toString();
            }


        }else{
            isDatabaseExist=false;
            try{
                Constants.db=openOrCreateDatabase("tradepostdb.db",MODE_PRIVATE,null);
                try {
                    try {
                        Constants.db.execSQL("Create table IF NOT EXISTS login (" +
                                "  username varchar ," +
                                "  password varchar ," +
                                "  email varchar," +
                                "  itype varchar  ," +
                                "  profilepicture varchar ," +
                                "  emailconfirm varchar ," +
                                "  userid int(10))");
                        Constants.db.execSQL("CREATE TABLE offeritems (\n" +
                                "  offerid int(10) ,\n" +
                                "  itemid int(10) \n" +
                                ")");
                        Constants.db.execSQL("CREATE TABLE fav (\n" +
                                " id int(10) ,\n" +
                                " itemid int(10) \n" +
                                ")");
                        Constants.db.execSQL("CREATE TABLE notifications (\n" +
                                "  offerid int(10) ,\n" +
                                "  notificationid INTEGER PRIMARY KEY   AUTOINCREMENT,msg varchar,type int(10),status int(10) \n" +
                                ")");


                        Constants.db.execSQL("CREATE TABLE offers (\n" +
                                "  Offerid int(10) ,\n" +
                                "  userid  varchar,\n" +
                                "  recieveduserid varchar,\n" +
                                "  status int(10),\n" +
                                "  cash int(10) ,\n" +
                                "  Itemid int(10) ,\n" +
                                "  Dir varchar " +

                                ") ");
                        Constants.db.execSQL("CREATE TABLE offeradditionalitems (\n" +
                                "  OfferId int(11), \n" +
                                "  ItemName varchar(45) \n" +
                                ") ");



                        Constants.db.execSQL("Create table IF NOT EXISTS GCM (gcmkey varchar)");

                        Constants.db.execSQL("Create table IF NOT EXISTS marketplacelisting (" +
                                "  itemid varchar primary key ," +
                                "  itemtitle varchar ," +
                                "  itemdescription varchar," +
                                "  itemcondition varchar  ," +
                                "  itemcategory varchar ," +
                                "  itemtags varchar ," +
                                "  itemdate varchar ," +
                                "  itemlatitude double ," +
                                "  itemlongitude double ," +
                                "  itemusername varchar ," +
                                "  itemimages varchar ," +
                                "  userid int(10)" +
                                "  )");

                        instanceID = InstanceID.getInstance(this);

                        try {
                            new AsyncTask<String,String,String>()
                            {
                                @Override
                                protected String doInBackground(String... params) {
                                    try {
                                        String token = instanceID.getToken("923650940708",
                                                GoogleCloudMessaging.INSTANCE_ID_SCOPE);
                                        Constants.GCM_Key = token;
                                        ContentValues cv=new ContentValues();
                                        cv.put("gcmkey", token);
                                        Constants.db.insert("GCM", null, cv);

                                    } catch(Exception e) {
                                        String s=e.toString();
                                    }
                                        return null;
                                }
                            }.execute(null,null,null);

                        } catch(Exception e) {
                            String s=e.toString();
                        }
                    } catch(Exception e) {
                        String s=e.toString();
                    }
                } catch(Exception e) {
                    String s=e.toString();
                }

            } catch(Exception e) {
                String s=e.toString();
            }

        }


        RelativeLayout r = (RelativeLayout) findViewById(R.id.screen);
        final GestureDetector gestureDetector = new GestureDetector(this, new MyGestureDetector());
        OnTouchListener gestureListener = new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        r.setOnTouchListener(gestureListener);
    }
public boolean ispermitted()
{
    boolean a=false;
    return a;
}

    class MyGestureDetector extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                  if(  permission.checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
                      startActivity(new Intent(Welcome.this, FirstTime.class));
                      finish();
                  }
                  } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }

    @Override
    public void onClick(View arg0) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public static boolean doesDatabaseExist(ContextWrapper context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }
}

package com.sinapp.sharathsind.tradepost;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract.CommonDataKinds.Relation;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import Model.RegisterWebService;
import Model.Variables;
import datamanager.MyLocationService;
import datamanager.userdata;
import webservices.MainWebService;


public class Welcome extends Activity implements OnClickListener {

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private TextView title;
    InstanceID instanceID;
 public static    LocationManager locationManager;
  public static   MyLocationService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
service=new MyLocationService(this);
        boolean b = doesDatabaseExist(new ContextWrapper(getBaseContext()), "tradepostdb.db");
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, service);
        if (b) {
            try{
                Constants.db=openOrCreateDatabase("tradepostdb.db",MODE_PRIVATE,null);

                Cursor c=Constants.db.rawQuery("select * from login",null);
                c.moveToFirst();
               Constants.userid=c.getInt(c.getColumnIndex("userid"));
                Variables.email=c.getString(c.getColumnIndex("email"));
                Variables.username=c.getString(c.getColumnIndex("username"));
                userdata.userid=Constants.userid;
                Criteria criteria = new Criteria();
                String provider = locationManager.getBestProvider(criteria, false);
                Location location = locationManager.getLastKnownLocation(provider);
                userdata.longitude=location.getLongitude();
                userdata.latitude=location.getLatitude();
                new AsyncTask<String,String,String>()
                {

                    @Override
                    protected String doInBackground(String... params) {
                        SoapObject object = new SoapObject("http://webser/", "getuseritems");
                     //   SoapObject object = new SoapObject("http://webser/", "getuseritems");
                        object.addProperty("userid",  userdata.userid);
                  Vector object1=      MainWebService.getMsg1(object,"http://192.168.2.15:8084/TDserverWeb/Search?wsdl","http://webser/Search/getuseritemsRequest");
    userdata.items=new HashSet<Integer>();
                        for(Object i:object1)
                        {
                            userdata.items.add(Integer.getInteger(((SoapPrimitive)i).getValue().toString()));
                        }
                        return null;




                    }
                }.execute(null,null,null);
      //          URL url = new URL("http://192.168.2.15:8084/TDserverWeb/images/"+Constants.userid+"/profile.png");
        //        Variables.profilepic = BitmapFactory.decodeStream(url.openConnection().getInputStream());

//                Constants.username=c.getString(c.getColumnIndex("username"));
if(c.getCount()>0)
{
    startActivity(new Intent(this, NavigationDrawer.class));
    finish();
    //  locationManager.removeUpdates(service);
}



            }catch(Exception e)
            {
                String s=e.toString();
            }

        }
        else
        {
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

                }
                catch(Exception e)
                {
                    String s=e.toString();

                }


            }
            catch(Exception e)
            {
                String s=e.toString();

            }


        }catch(Exception e)
        {
String s=e.toString();
        }

        }

         instanceID = InstanceID.getInstance(this);


        try {
            new AsyncTask<String,String,String>()
            {

                @Override
                protected String doInBackground(String... params) {
                    try {
                        String token = instanceID.getToken("923650940708",
                                GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                        Constants.GCM_Key = token;
                    }
                    catch(Exception e)
                    {
                        String s=e.toString();
                    }
                    return null;
                }
            }.execute(null,null,null);


        }
        catch(Exception e)
        {
            String s=e.toString();

        }
            Typeface type = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        TextView title = (TextView) findViewById(R.id.title);
        title.setTypeface(type);



        RelativeLayout r = (RelativeLayout) findViewById(R.id.screen);
        final GestureDetector gestureDetector = new GestureDetector(this, new MyGestureDetector());
        OnTouchListener gestureListener = new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        r.setOnTouchListener(gestureListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class MyGestureDetector extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    startActivity(new Intent(Welcome.this, FirstTime.class));
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
        // TODO Auto-generated method stub

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private static boolean doesDatabaseExist(ContextWrapper context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }
}

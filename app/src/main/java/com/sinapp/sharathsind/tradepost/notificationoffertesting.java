package com.sinapp.sharathsind.tradepost;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Templates;

import Model.CustomPagerAdapter;
import Model.Variables;
import de.hdodenhof.circleimageview.CircleImageView;
import webservices.MainWebService;

/**
 * Created by HenryChiang on 15-07-22.
 */
public class notificationoffertesting extends AppCompatActivity {


    private View rootView;
    private CustomPagerAdapter mCustomPagerAdapter;
    private ViewPager mViewPager;
    private List<ImageView> dots;
    private LinearLayout dotsLayout;
    private Bitmap[] imageResources;

    final private String singleItem = "An Item";
    final private String multiItems = "Multi-items";
    final private String cash = "$";
    final private String currency = " CAD";
String items[];
    private TextView myItemPlaceholder, usernamePlaceholder, itemOfferPlaceholder, extraCashPlaceholder;
    private CircleImageView userImgPlaceholder;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
     setContentView(R.layout.notification_offer);
Intent i=getIntent();
        final int offerid=i.getIntExtra("offerid", 0);
        SoapObject obje=new SoapObject("http://webser/", "getStatus");
        obje.addProperty("offerid",offerid);
        SoapPrimitive soapPrimitive1=MainWebService.getretryMsg(obje,"http://73.37.238.238:8084/TDserverWeb/OfferWebService?wsdl","http://webser/OfferWebService/sendOfferDeclineRequest",0);
int status=Integer.parseInt(soapPrimitive1.getValue().toString());
if(status==1)
{
    Intent intent=new Intent(this,ChatFragment.class);
    intent.putExtra("offerid",offerid);
    //intent.putExtra("userid",)
    startActivity(intent);
}
        else if(status==2)
{

}
        String itemname=" ";
        if(offerid>0)
        {
       itemname=  i.getStringExtra("itemname");



        }

SQLiteDatabase db=openOrCreateDatabase("tradepostdb.db", MODE_PRIVATE, null);
       // rootView = inflater.inflate(R.layout.notification_offer, container, false);
Cursor c=db.rawQuery("select * from offers where offerid=" + offerid, null);
        c.moveToFirst();

    final int userid =Integer.parseInt(c.getString(c.getColumnIndex("userid")));

     int cash=Integer.parseInt(c.getString(c.getColumnIndex("cash")));
c.close();        //
        c=db.rawQuery("select * from offeritems where offerid="+offerid,null);

        int count =c.getCount();
        c.moveToFirst();
        items=new String[count+1];
      for(int i1=0;i1<count;i1++)
      {
          items[i1]="http://73.37.238.238:8084/TDserverWeb/images/items/"+c.getString(c.getColumnIndex("itemid"))+"/0.png";

c.moveToNext();
      }
    c.close();
        c=db.rawQuery("select * from offeradditionalitems where offerid=" + offerid, null);
        count +=c.getCount();
        c.moveToFirst();
        if(c.getCount()>0)
            items[count-1]="http://73.37.238.238:8084/TDserverWeb/images/Offers/"+offerid+"/0.png";
        myItemPlaceholder = (TextView)findViewById(R.id.noti_offer_myItem_placeholder);

        //
        userImgPlaceholder = (CircleImageView)findViewById(R.id.noti_offer_userImg_placeholder);
        usernamePlaceholder = (TextView)findViewById(R.id.noti_offer_userName_placeholder);
        itemOfferPlaceholder =(TextView)findViewById(R.id.noti_offer_itemOffer_placeholder);
        extraCashPlaceholder = (TextView)findViewById(R.id.noti_offer_extraCash_Placeholder);

        //image slider viewer
        mCustomPagerAdapter = new CustomPagerAdapter(this,imageResources);
        mCustomPagerAdapter.imagesArray=new String[count];
        imageResources=new Bitmap[count];
for(int j=0;j<count;j++)
{
    mCustomPagerAdapter.imagesArray[j]=items[j];
}
c.close();
        db.close();
        mViewPager = (ViewPager)findViewById(R.id.noti_offer_viewPager_placeholder);
        mViewPager.setAdapter(mCustomPagerAdapter);
         obje=new SoapObject();
        obje = new SoapObject("http://webser/", "getusername");
        obje.addProperty("name", userid);
        SoapPrimitive soapPrimitive = MainWebService.getretryMsg(obje, "http://73.37.238.238:8084/TDserverWeb/getUserName?wsdl", "http://webser/getUserName/getusernameRequest", 0);
      //  MarketPlaceData data = new MarketPlaceData();
       String username = soapPrimitive.getValue().toString();
        dotsLayout = (LinearLayout)findViewById(R.id.dots);
        addDots();
        Uri url1 = Uri.parse("http://73.37.238.238:8084/TDserverWeb/images/" + userid + "/profile.png");
        Picasso.with(this)
                .load(url1)
                .placeholder(R.drawable.sample_img3)
                .into(userImgPlaceholder);

        //getting data from server.
        setMyItemPlaceholder(itemname);
setUserDetails(null, username, count, cash);

CircleImageView acept= (CircleImageView) findViewById(R.id.noti_offer_accept);
        CircleImageView decline= (CircleImageView) findViewById(R.id.noti_offer_cancel);
        acept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SQLiteDatabase db=openOrCreateDatabase("tradepostdb.db", MODE_PRIVATE, null);
                    Cursor       c=db.rawQuery("select * from login", null);
                    c.moveToFirst();

                    //Variables.email=c.getString(c.getColumnIndex("email"));
                    String      username=c.getString(c.getColumnIndex("username"));
                    SoapObject obje=new SoapObject("http://webser/", "sendOfferAccept");
                    obje.addProperty("msg",username+" has accepted your offer");
                    obje.addProperty("offerid",offerid);
                    obje.addProperty("userid", userid);
                    obje.addProperty("username", Variables.username);
                    SoapPrimitive soapPrimitive1=MainWebService.getretryMsg(obje,"http://73.37.238.238:8084/TDserverWeb/OfferWebService?wsdl","http://webser/OfferWebService/sendOfferAcceptRequest",0);
                    db.execSQL("update offers set status =1 where offerid =" + offerid);

                    db.execSQL("create table m" + offerid + "(msgid int(10),msg varchar,msgpath varchar,seen DATETIME,sent DATETIME,userid int(10),ruserid int(10) )");


                   c.close();
                    db.close();
                    Intent i=new Intent(notificationoffertesting.this, ChatFragment.class);
                    i.putExtra("offerid",offerid);
                    startActivity(i);
                    finish();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        });
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=openOrCreateDatabase("tradepostdb.db", MODE_PRIVATE, null);
                Cursor       c=Constants.db.rawQuery("select * from login", null);
                c.moveToFirst();

                //Variables.email=c.getString(c.getColumnIndex("email"));
                Variables.username=c.getString(c.getColumnIndex("username"));
                SoapObject obje=new SoapObject("http://webser/", "sendOfferDeclined");
                obje.addProperty("msg",Variables.username+" has declined your offer");
                obje.addProperty("offerid",offerid);
                obje.addProperty("userid",userid);
                obje.addProperty("username", Variables.username);
                SoapPrimitive soapPrimitive1=MainWebService.getretryMsg(obje,"http://73.37.238.238:8084/TDserverWeb/OfferWebService?wsdl","http://webser/OfferWebService/sendOfferDeclineRequest",0);
                db.execSQL("update offers set status =2 where offerid ="+offerid);
               db.close();
                finish();
            }
        });
    }


    public void setMyItemPlaceholder(String myItem){
        myItemPlaceholder.setText(myItem);
    }

    public void setUserDetails(Bitmap userImg, String username, int itemOfferCount, int extraCash){
     //   userImgPlaceholder.setImageBitmap(userImg);
        usernamePlaceholder.setText(username);

        if(itemOfferCount>1){
            itemOfferPlaceholder.setText(singleItem);
        }else{
            itemOfferPlaceholder.setText(multiItems);
        }

        extraCashPlaceholder.setText(cash+extraCash+currency);
    }

    //adding dots below viewpager.
    public void addDots() {
        dots = new ArrayList<>();

        for(int i = 0; i < imageResources.length; i++) {
            ImageView dot = new ImageView(this);
            if(i==0){
                dot.setImageDrawable(getResources().getDrawable(R.drawable.pager_dot_selected));
            }else {
                dot.setImageDrawable(getResources().getDrawable(R.drawable.pager_dot_not_selected));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(3,0,3,0);

            dotsLayout.addView(dot,params);

            // dotsLayout.addView(dot);
            dots.add(dot);
        }

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                selectDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    //highlight dot below viewpager.
    public void selectDot(int idx) {
        Resources res = getResources();
        for(int i = 0; i < imageResources.length; i++) {
            int drawableId = (i==idx)?(R.drawable.pager_dot_selected):(R.drawable.pager_dot_not_selected);
            Drawable drawable = res.getDrawable(drawableId);
            dots.get(i).setImageDrawable(drawable);
        }
    }
}

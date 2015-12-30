package Model;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Base64;

import com.sinapp.sharathsind.tradepost.Constants;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import data.MessageClass;

import datamanager.Item;
import datamanager.ItemResult;
import datamanager.userdata;
import webservices.FavouriteWebService;
import webservices.MainWebService;

/**
 * Created by sharathsind on 2015-06-01.
 */
public class RegisterWebService {
    private static final String SOAP_ACTION = "http://webser/Register/operationRequest";
    private static final String METHOD_NAME = "operation";
    private static final String NAMESPACE = "http://webser/";
    private static final String URL ="http://73.37.238.238:8084/TDserverWeb/Register?wsdl";
private  static final String mname="gcmwebservice";
    private static final String SOAP_ACTION1 = "http://webser/Register/gcmwebserviceRequest";
    public static ContentValues signUp(String username, String email, String s, String fb, Bitmap profilepic, boolean b,SQLiteDatabase db) {
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("username",username);
        request.addProperty("email",email);
        request.addProperty("password",s);
        request.addProperty("type",fb);
        request.addProperty("os","android");
        request.addProperty("confirm",String.valueOf(b));
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        profilepic.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b1=baos.toByteArray();
        ContentValues cv=new ContentValues();
        String temp= Base64.encodeToString(b1, Base64.DEFAULT);
        request.addProperty("imagedata", temp);
        request.addProperty("api",Constants.GCM_Key);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(request);

        HttpTransportSE ht = new HttpTransportSE(URL,10000000);
        try {
            ht.call(SOAP_ACTION, envelope);
            SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
            String res=response.getValue().toString();
            cv.put("username",username);
            cv.put("email",email);
            cv.put("emailconfirm",String.valueOf(b));
            cv.put("itype",fb);
            cv.put("userid",res);
            Constants.userid =Integer.parseInt(res);
            userdata.userid = Integer.parseInt(res);
            cv.put("password",s);
            cv.put("profilepicture", "lib/profile.png");
            request=new SoapObject(NAMESPACE,mname);
            request.addProperty("os", "android");
            request.addProperty("userid", Integer.parseInt(res));
            request.addProperty("apikey",Constants.GCM_Key);
            SoapPrimitive s1=MainWebService.getretryMsg(request,URL,SOAP_ACTION1,0);
            String sj="h";
            getItems();
            getnot();
    //  long l=   db.insert("login",null,cv);
//long k=l;
        }
catch (EOFException e)
{
signUp(username, email, s, fb, profilepic, b, db);
}
        catch (Exception e) {
            e.printStackTrace();
        }

        return cv;
    }
   public  static void getItems()
   {try {
       SoapObject object = new SoapObject("http://webser/", "getuseritems");
       //SoapObject object = new SoapObject("http://webser/", "getuseritems");
       object.addProperty("userid", userdata.userid);
       Vector object1 = MainWebService.getMsg1(object, "http://73.37.238.238:8084/TDserverWeb/Search?wsdl", "http://webser/Search/getuseritemsRequest");
       userdata.items = new ArrayList<Integer>();


       if (object1 != null) {
           for (Object i : object1) {
               userdata.items.add(Integer.parseInt(((SoapPrimitive) i).getValue().toString()));
           }
       }
       userdata.i = new ArrayList<ItemResult>();

       for (int i : userdata.items) {

           SoapObject obje = new SoapObject("http://webser/", "getItembyId");
           obje.addProperty("itemid", i);
           KvmSerializable result1 = MainWebService.getMsg2(obje, "http://73.37.238.238:8084/TDserverWeb/GetItems?wsdl"
                   , "http://webser/GetItems/getItembyIdRequest");

           ItemResult ir = new ItemResult();
           ir.item = new Item();

           SoapObject object12 = (SoapObject) result1.getProperty(0);
           //for(int u=0;u<object.getPropertyCount())
           ir.item.set(object12);
           //SoapObject o7=(SoapObject)result1;
           //Object j=       o.getProperty("images");
           int i1 = result1.getPropertyCount();
           ir.images = new String[i1 - 1];

           for (int u1 = 1; u1 < i1; u1++) {
               ir.images[u1 - 1] = result1.getProperty(u1).toString();

           }
           obje = new SoapObject("http://webser/", "searchbyint");
           obje.addProperty("name", i);
           Vector result2 = MainWebService.getMsg1(obje, "http://73.37.238.238:8084/TDserverWeb/NewWebService?wsdl"
                   , "http://webser/NewWebService/searchbyintRequest");
           if (result2 != null) {

               int index = 0;
               ir.tags = new String[result2.size()];

               for (Object o : result2) {
                   ir.tags[index] = ((SoapPrimitive) o).getValue().toString();
                   index++;

               }
           }

           userdata.i.add(ir);


       }

       FavouriteWebService.getfavouInts();
       int offers[] = getuserOffer();
       if (offers != null) {
           for (int i : offers) {
               setOffer(i);
           }


       }
   }
   catch (Exception e)
   {

   }
   }
public  static void setOffer(int offerid)
{SoapObject obje =new SoapObject("http://webser/","getItemsOffer");
    obje.addProperty("offerid",offerid);
    SoapObject obje1 =new SoapObject("http://webser/","getOffer");
    obje1.addProperty("offerid",offerid);
    KvmSerializable s= MainWebService.getMsg2(obje1,"http://73.37.238.238:8084/TDserverWeb/OfferWebService?wsdl","http://webser/OfferWebService/getOfferRequest");
    ContentValues cv=new ContentValues();
   // SQLiteDatabase db=  Constants.db=openOrCreateDatabase("tradepostdb.db",MODE_PRIVATE,null);

    SoapObject offer= (SoapObject) ((SoapObject)s).getProperty("of");
    int offerid1=Integer.parseInt(offer.getPropertyAsString("offerid"));
    cv.put("offerid",offer.getPropertyAsString("offerid"));
    cv.put("userid",offer.getPropertyAsString("userid"));
    cv.put("itemid",offer.getPropertyAsString("itemid"));
    cv.put("cash",offer.getPropertyAsString("cash"));
    cv.put("recieveduserid",offer.getPropertyAsString("recieveduserid"));
    String status=offer.getPropertyAsString("status");
    if(status.equals("1"))
    {

        Constants.db.execSQL("create table IF NOT EXISTS  m" + offerid + "(msgid INTEGER PRIMARY KEY,msg varchar,msgpath varchar,seen DATETIME,sent DATETIME,userid int(10),ruserid int(10)) ");
SoapObject request=new SoapObject("http://webser/","getMessages");
        request.addProperty("offerid",offerid1);
        Vector soapObject=MainWebService.getMsg1(request,"http://73.37.238.238:8084/TDserverWeb/MessageandNotification?wsdl","http://webser/MessageandNotification/getMessagesRequest");
        if(soapObject!=null)
        {
            for(Object p: soapObject)
            {

                get((SoapObject)p,offerid1);
            }
        }


    }
    cv.put("status",offer.getPropertyAsString("status"));
    cv.put("dir", offer.getPropertyAsString("dir"));
    //cv.put("dir");

   Constants. db.insert("offers", null, cv);
    if(((SoapObject )s).hasProperty("item"))
    {
        cv=new ContentValues();
        if(((SoapObject)(((SoapObject) s).getProperty("item"))).getPropertyCount()>0) {
            cv.put("Offerid", offerid);
            cv.put("itemname",((SoapObject)(((SoapObject) s).getProperty("item"))).getPropertyAsString("itemname") );
      Constants.      db.insert("offeradditionalitems", null, cv);
        }

    }
    Vector s1=MainWebService.getMsg1(obje,"http://73.37.238.238:8084/TDserverWeb/OfferWebService?wsdl","http://webser/OfferWebService/getItemsOfferRequest");
    for(Object c:s1) {
        cv = new ContentValues();
        cv.put("offerid",offerid);
        cv.put("itemid", Integer.parseInt(c.toString()));
     Constants.   db.insert("offeritems", null, cv);
    }

}
    public static void getnot()
    {
      //  Constants.db.execSQL("create table IF NOT EXISTS  m" + offerid + "(msgid int(10),msg varchar,msgpath varchar,seen DATETIME,sent DATETIME,userid int(10),ruserid int(10)) ");
        SoapObject request=new SoapObject("http://webser/","getNotifications");
        request.addProperty("userid",Constants.userid);
        Vector soapObject=MainWebService.getMsg1(request,"http://73.37.238.238:8084/TDserverWeb/MessageandNotification?wsdl","http://webser/MessageandNotification/getNotificationsRequest");
        if(soapObject!=null)
        {
            for(Object p: soapObject)
            {
                setnot((SoapObject)p);

//                get((SoapObject)p,}


        }

    }
    }



public static void  setnot(SoapObject result)
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
    public static void get(SoapObject result,int offerid) {
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
public static int[] getuserOffer()
{
    int[] i=null;
SoapObject    obje=new SoapObject("http://webser/","getuserOffer");
    obje.addProperty("userid", userdata.userid);
    Vector result2= MainWebService.getMsg1(obje, "http://73.37.238.238:8084/TDserverWeb/OfferWebService?wsdl"
            , "http://webser/OfferWebService/getuserOfferRequest");
    if(result2!=null) {

        int index=0;
        i=new int[result2.size()];

        for (Object o:result2 ) {
           i[index] = Integer.parseInt(((SoapPrimitive)o).getValue().toString());
            index++;

        }

    }


return i;
}
    private static final String SOAP_ACTION3 = "http://webser/Register/additemsRequest";
    private static final String METHOD_NAME3 = "additems";
   // private static final String NAMESPACE = "http://webser/";
    //private static final String URL ="http://192.168.43.248:8084/TDserverWeb/AddItems?wsdl";
    public static  SoapPrimitive sendDataToServer(String itemTitle, String descrpition, String[] tags, Object[] images, int condition, int userid, String category) {

        SoapObject object = new SoapObject(NAMESPACE, METHOD_NAME3);
        object.addProperty("itemname", itemTitle);
        object.addProperty("desc",descrpition);
        object.addProperty("latitude", String.format("%.2f",userdata.mylocation.latitude));


        //object.addProperty("tags",tag);
        object.addProperty("longi", String.format("%.2f",userdata.mylocation.Longitude));
        object.addProperty("userid", userdata.userid);
        object.addProperty("category", category);
        object.addProperty("condition",condition);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
        headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
        envelope.setOutputSoapObject(object);
   //    MarshalFloat m=new MarshalFloat();
     //   m.register(envelope);
       // new MarshalBase64().register(envelope);
        System.setProperty("http.keepAlive", "false");
        HttpTransportSE ht = new HttpTransportSE( URL,50000000);

        ht.debug=true;
        try {


            ht.call(SOAP_ACTION3, envelope,headerPropertyArrayList);
            ht.getServiceConnection().setRequestProperty("connection","close");
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            //  String res = response.ge().toString();
            return response;
        }
catch (EOFException ex)
{
    return  sendDataToServer(itemTitle,descrpition,tags,images,condition,userid,category);

}
        catch (Exception e) {
            e.printStackTrace();
        }


        return null;

    }
    public static  SoapPrimitive sendDataEdit(String itemTitle, String descrpition, String[] tags, Object[] images, int condition, int userid, String category,int item) {

        SoapObject object = new SoapObject(NAMESPACE,"saveitem" );
        object.addProperty("itemname", itemTitle);
        object.addProperty("itemid", item);
        object.addProperty("desc",descrpition);
        object.addProperty("latitude", String.format("%.2f",userdata.mylocation.latitude));


        //object.addProperty("tags",tag);
        object.addProperty("longi", String.format("%.2f",userdata.mylocation.Longitude));
        object.addProperty("userid", userdata.userid);
        object.addProperty("category", category);
        object.addProperty("condition",condition);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
        headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
        envelope.setOutputSoapObject(object);
        //    MarshalFloat m=new MarshalFloat();
        //   m.register(envelope);
        // new MarshalBase64().register(envelope);
        System.setProperty("http.keepAlive", "false");
        HttpTransportSE ht = new HttpTransportSE( "http://73.37.238.238:8084/TDserverWeb/EditdeleteItem?wsdl",50000000);

        ht.debug=true;
        try {


            ht.call("http://webser/EditdeleteItem/saveitemRequest", envelope,headerPropertyArrayList);
            ht.getServiceConnection().setRequestProperty("connection","close");
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            //  String res = response.ge().toString();
            return response;
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        return null;

    }

    public static MessageClass sendMsg(String msg,Bitmap picture,int userid,int offerid,Activity activity)
    {
        SoapObject request=null;
        String soapaction,URL;
        URL="http://73.37.238.238:8084/TDserverWeb/MessageWebService?wsdl";
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        if(picture !=null)
        {
            request= new SoapObject(NAMESPACE, "sendpic");

            request.addProperty("userid", Constants.userid);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            picture.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            request.addProperty("picmsg",byteArray);
            request.addProperty("key",Constants.GCM_Key);
            request.addProperty("offerid",offerid);
            request.addProperty("name", msg);
            envelope.setOutputSoapObject(request);
new MarshalBase64().register(envelope);
            HttpTransportSE ht = new HttpTransportSE(URL);
            try {
                ht.call("http://webser/MessageWebService/sendpicRequest", envelope);
                SoapObject result = (SoapObject) envelope.getResponse();
                ContentValues cv=new ContentValues();
                //   ContentValues cv = new ContentValues();

                SQLiteDatabase db=  Constants.db=activity.openOrCreateDatabase("tradepostdb.db", activity.MODE_PRIVATE, null);
                cv.put("msgid",result.getProperty("messageid").toString());
                cv.put("msg",msg);
                cv.put("ruserid",result.getProperty("ruserid").toString());
                cv.put("userid",result.getProperty("userid").toString());
                cv.put("sent",result.getProperty("sent").toString());
                cv.put("msgpath", result.getProperty("messagepath").toString());
                db.insert("m" + offerid, "seen", cv);
                db.close();


            }

            catch (EOFException e)
            {
             return   sendMsg(msg,picture,userid,offerid,activity);
            }
            catch (Exception e)
            {

            }





        }
        else{


            request= new SoapObject(NAMESPACE, "send");

            request.addProperty("userid",Constants.userid);
         //   request.addProperty("uname",Constants.username);
            request.addProperty("key",Constants.GCM_Key);
            request.addProperty("offerid",offerid);
            request.addProperty("name",msg);
            envelope.setOutputSoapObject(request);

            HttpTransportSE ht = new HttpTransportSE(URL);
            try {
                ht.call("http://webser/MessageWebService/sendRequest", envelope);
                SoapObject response = (SoapObject) envelope.getResponse();
                SoapObject result= (SoapObject) response.getProperty("m");
                MessageClass m=new MessageClass();
                ContentValues cv=new ContentValues();
             //   ContentValues cv = new ContentValues();
                SQLiteDatabase db=  Constants.db=activity.openOrCreateDatabase("tradepostdb.db", activity.MODE_PRIVATE, null);
                cv.put("msgid",result.getProperty("messageid").toString());
                cv.put("msg",msg);
                cv.put("ruserid",result.getProperty("ruserid").toString());
                cv.put("userid",result.getProperty("userid").toString());
                cv.put("sent",result.getProperty("sent").toString());
                cv.put("msgpath",result.getProperty("messagepath").toString());
                db.insert("m" + offerid, "seen", cv);
                db.close();
                m.setUserid(Integer.parseInt(result.getProperty("userid").toString()));
                m.setMsg(msg);

              Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                        .parse(result.getProperty("sent").toString());
                m.setSent(date);
                return m;

            }

            catch (EOFException e)
            {
                return        sendMsg(msg,picture,userid,offerid,activity);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }



        }



        return null;
    }



}

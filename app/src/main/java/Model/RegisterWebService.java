package Model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Base64;

import com.sinapp.sharathsind.tradepost.Constants;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import data.StringVector;
import datamanager.userdata;
import webservices.MainWebService;
import webservices.SoapStringVector;

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
            SoapPrimitive s1=MainWebService.getMsg(request,URL,SOAP_ACTION1);
            String sj="h";
    //  long l=   db.insert("login",null,cv);
//long k=l;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cv;
    }
    private static final String SOAP_ACTION3 = "http://webser/Register/additemsRequest";
    private static final String METHOD_NAME3 = "additems";
   // private static final String NAMESPACE = "http://webser/";
    //private static final String URL ="http://192.168.43.248:8084/TDserverWeb/AddItems?wsdl";
    public static  SoapPrimitive sendDataToServer(String itemTitle, String descrpition, String[] tags, Object[] images, int condition, int userid, String category) {

        SoapObject object = new SoapObject(NAMESPACE, METHOD_NAME3);
        object.addProperty("itemname", itemTitle);
        object.addProperty("desc",descrpition);
        object.addProperty("latitude", String.format("%.2f",userdata.latitude));
        PropertyInfo propertyInfo=new PropertyInfo();
        propertyInfo.setValue(userdata.latitude);
        propertyInfo.setName("latitude");
        StringVector tag=new StringVector();
        for(String h: tags)
        tag.add(h);
        object.addProperty("tags",tag);
        object.addProperty("longi", String.format("%.2f",userdata.longitude));
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
        catch (Exception e) {
            e.printStackTrace();
        }


        return MainWebService.getMsg(object, "http://73.37.238.238:8084/TDserverWeb/AddItems?wsdl", "http://webser/AddItems/additemRequest");

    }

    public static String sendMsg(String msg,Bitmap picture,int userid)
    {
        SoapObject request = new SoapObject(NAMESPACE, "hello");
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.PNG,100, baos);



        byte [] b1=baos.toByteArray();
        ContentValues cv=new ContentValues();
        String temp= Base64.encodeToString(b1, Base64.DEFAULT);
        request.addProperty("attachment", temp);
        request.addProperty("userid",Constants.userid);
        request.addProperty("uname",Constants.username);
        request.addProperty("filetype","png");
        request.addProperty("recuserid",userid);
        request.addProperty("msg",msg);





        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(request);

        HttpTransportSE ht = new HttpTransportSE(URL);
        try {
            ht.call("http://webser/Register/helloRequest", envelope);
            SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
            String res=response.getAttribute("return").toString();

        } catch (Exception e) {
            e.printStackTrace();
        }



        return null;
    }


}

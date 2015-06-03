package Model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Base64;

import com.sinapp.sharathsind.tradepost.Constants;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;

/**
 * Created by sharathsind on 2015-06-01.
 */
public class RegisterWebService {
    private static final String SOAP_ACTION = "http://webser/Register/operationRequest";
    private static final String METHOD_NAME = "operation";
    private static final String NAMESPACE = "http://webser/";
    private static final String URL ="http://192.168.2.15:8084/TDserverWeb/Register?wsdl";

    public static String signUp(String username, String email, String s, String fb, Bitmap profilepic, boolean b,SQLiteDatabase db) {
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

        HttpTransportSE ht = new HttpTransportSE(URL);
        try {
            ht.call(SOAP_ACTION, envelope);
            SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
            String res=response.getValue().toString();
    cv.put("username",username);
    cv.put("email",email);
    cv.put("confirm",String.valueOf(b));
    cv.put("type",fb);
    cv.put("userid",res);
    cv.put("password",s);
    cv.put("profilepicture","lib/profile.png");
          db.insert("login",null,cv);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public static String sendMsg(String msg,Bitmap picture)
    {


        return null;
    }


}

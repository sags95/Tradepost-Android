package webservices;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by sharathsind on 2015-07-06.
 */
public class MainWebService {

    public static SoapPrimitive getMsg(SoapObject request,String URL,String SOAP_ACTION ) {
     //  SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
       // request.addProperty("msgid",msgid);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
        headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
        envelope.setOutputSoapObject(request);
        //    MarshalFloat m=new MarshalFloat();
        //   m.register(envelope);
        // new MarshalBase64().register(envelope);
        System.setProperty("http.keepAlive", "false");
        HttpTransportSE ht = new HttpTransportSE( URL,50000000);

        ht.debug=true;
        try {


            ht.call(SOAP_ACTION, envelope,headerPropertyArrayList);
            ht.getServiceConnection().setRequestProperty("connection","close");
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            //  String res = response.ge().toString();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;


    }

    public static Vector getMsg1(SoapObject request,String URL,String SOAP_ACTION ) {
        //  SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        // request.addProperty("msgid",msgid);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
        headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
        envelope.setOutputSoapObject(request);
           MarshalFloat m=new MarshalFloat();
           m.register(envelope);
        // new MarshalBase64().register(envelope);
        System.setProperty("http.keepAlive", "false");
        HttpTransportSE ht = new HttpTransportSE( URL,50000000);

        ht.debug=true;
        try {


            ht.call(SOAP_ACTION, envelope,headerPropertyArrayList);
            ht.getServiceConnection().setRequestProperty("connection","close");
           Vector<Integer> response = (Vector<Integer>) envelope.getResponse();
            //  String res = response.ge().toString();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;


    }
    public static SoapObject getMsg2(SoapObject request,String URL,String SOAP_ACTION ) {
        //  SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        // request.addProperty("msgid",msgid);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
        headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
        envelope.setOutputSoapObject(request);
        //    MarshalFloat m=new MarshalFloat();
        //   m.register(envelope);
        // new MarshalBase64().register(envelope);
        System.setProperty("http.keepAlive", "false");
        HttpTransportSE ht = new HttpTransportSE( URL,50000000);

        ht.debug=true;
        try {


            ht.call(SOAP_ACTION, envelope,headerPropertyArrayList);
            ht.getServiceConnection().setRequestProperty("connection","close");
            SoapObject response = (SoapObject) envelope.getResponse();
            //  String res = response.ge().toString();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;


    }


}

package webservices;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by sharathsind on 2015-07-06.
 */
public class MainWebService {

    public static SoapPrimitive getMsg(SoapObject request,String URL,String SOAP_ACTION ) {
     //   SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
       // request.addProperty("msgid",msgid);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(request);

        HttpTransportSE ht = new HttpTransportSE(URL);
        try {
            ht.call(SOAP_ACTION, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            String res = response.getValue().toString();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;


    }




}

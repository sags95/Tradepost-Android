package webservices;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by sharathsind on 2015-06-01.
 */
public class RegisterWebService {
    private static final String SOAP_ACTION = "http://webser/Chat/getMessageRequest";
    private static final String METHOD_NAME = "getMessage";
    private static final String NAMESPACE = "http://webser/";
    private static final String URL ="http://73.37.238.238:8084/TDserverWeb/Chat?wsdl";
    public static String getMsg(int msgid) {
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("msgid",msgid);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(request);

        HttpTransportSE ht = new HttpTransportSE(URL);
        try {
            ht.call(SOAP_ACTION, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            String res = response.getAttribute("return").toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;


    }
}

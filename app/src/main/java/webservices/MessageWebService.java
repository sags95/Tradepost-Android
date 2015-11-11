package webservices;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import datamanager.userdata;

/**
 * Created by sharathsind on 2015-06-06.
 */
public class MessageWebService {
    public int maxId()
    {
        int id=0;
        SoapObject s=new SoapObject("http://webser/","getLastid");
        s.addProperty("offerid", userdata.userid);
        SoapPrimitive s1=MainWebService.getretryMsg(s,"http://73.37.238.238:8084/TDserverWeb/MessageandNotification","http://webser/OfferWebService/getLastidRequest",0);
        id=Integer.parseInt(s1.getValue().toString());

        return id;
    }
}

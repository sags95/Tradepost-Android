package webservices;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.util.Vector;

import datamanager.userdata;

/**
 * Created by sharathsind on 2015-11-10.
 */
public class NotificationandMessageService {
 public int maxId()
 {
     int id=0;
     SoapObject s=new SoapObject("http://webser/","getLastNotification");
     s.addProperty("offerid", userdata.userid);
     SoapPrimitive s1=MainWebService.getretryMsg(s,"http://73.37.238.238:8084/TDserverWeb/MessageandNotification","http://webser/OfferWebService/getLastNotificationRequest",0);
if(s1==null)
    return 0;
   id=Integer.parseInt(s1.getValue().toString());

     return id;
 }

public void insert()
{
    int id=0;
    SoapObject s=new SoapObject("http://webser/","getLastNotification");
    s.addProperty("offerid", userdata.userid);
    Vector s1=MainWebService.getMsg1(s,"http://73.37.238.238:8084/TDserverWeb/MessageandNotification","http://webser/OfferWebService/getLastNotificationRequest");





}





}

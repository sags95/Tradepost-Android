package webservices;

import android.content.ContentValues;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.sinapp.sharathsind.tradepost.Constants;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import datamanager.userdata;

/**
 * Created by sharathsind on 2015-07-06.
 */
public class OfferWebService {

    //SoapObject s=new SoapObject("http://webser/","get");
    public void sendOffer(int[] itemid, int userid, int ruserid, int foritemid, int cash, String Images, String itemname, String itemA)
    {
        SoapObject s=new SoapObject("http://webser/","sendOffer");
        s.addProperty("userid", userdata.userid);
    s.addProperty("ruserid",ruserid);
        s.addProperty("fori",foritemid);
         s.addProperty("cash",cash);
        SoapPrimitive soapPrimitive = MainWebService.getretryMsg(s, "http://73.37.238.238:8084/TDserverWeb/OfferWebService?wsdl", "http://webser/OfferWebService/sendOfferRequest", 0);
int offerid=Integer.parseInt(soapPrimitive.getValue().toString());
        ContentValues cv=new ContentValues();
        cv.put("offerid",offerid);
        cv.put("userid", userid);
        cv.put("itemid", foritemid);
        cv.put("cash", cash);
        cv.put("recieveduserid", ruserid);
        cv.put("status", 0);
        cv.put("dir", " ");
        Constants.db.insert("offers",null,cv);
        for(int i=0;i<itemid.length;i++)
{
 s=new SoapObject("http://webser/","addofferItem");
    s.addProperty("offerid", offerid);
    s.addProperty("itemid",itemid[i]);

 soapPrimitive = MainWebService.getretryMsg(s, "http://73.37.238.238:8084/TDserverWeb/OfferWebService?wsdl", "http://webser/OfferWebService/addofferItemRequest",0);
    cv=new ContentValues();
    cv.put("offerid", offerid);
    cv.put("itemid", String.valueOf(itemid));
    Constants.db.insert("offeritems", null, cv);



}
if(Images!=null) {
    s = new SoapObject("http://webser/", "addOferImage");
    s.addProperty("offerid", offerid);
    s.addProperty("images", Images);
    s.addProperty("pic", 0);
    soapPrimitive = MainWebService.getretryMsg(s, "http://73.37.238.238:8084/TDserverWeb/OfferWebService?wsdl", "http://webser/OfferWebService/addOferImageRequest", 0);
    s = new SoapObject("http://webser/", "AddAdtionalItem");
    s.addProperty("offerid", offerid);
    s.addProperty("name", itemA);
    //s.addProperty("pic", 0);
    soapPrimitive = MainWebService.getretryMsg(s, "http://73.37.238.238:8084/TDserverWeb/OfferWebService?wsdl", "http://webser/OfferWebService/AddAdtionalItemRequest", 0);

    cv=new ContentValues();
    cv.put("offerid", offerid);
    cv.put("itemname", itemA);
    Constants.db.insert("offeradditionalitems", null, cv);

}


       int number=itemid.length;
if(Images!=null)
{
    number++;
}
        String notification;
        if(number==1)
        {
            notification=userdata.name+" has sent item for  your item "+itemname;

        }
        else{
            notification=userdata.name+" has sent multi-items for your item "+itemname;
        }

        s=new SoapObject("http://webser/","sendOfferNot");
        s.addProperty("offerid", offerid);
        s.addProperty("userid",ruserid);
        s.addProperty("msg",notification);
        s.addProperty("username","useless");
        soapPrimitive = MainWebService.getretryMsg(s, "http://73.37.238.238:8084/TDserverWeb/OfferWebService?wsdl", "http://webser/OfferWebService/sendOfferNotRequest",0);







    }
    public void acceptOffer(int offerid)
    {

    }
    public void declineOffer(int offerid)
    {
        
    }

}

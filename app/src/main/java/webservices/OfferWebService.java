package webservices;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import datamanager.userdata;

/**
 * Created by sharathsind on 2015-07-06.
 */
public class OfferWebService {

    //SoapObject s=new SoapObject("http://webser/","get");
    public void sendOffer(int[]itemid,int userid,int ruserid,int foritemid,int cash,String Images,String itemname)
    {
        SoapObject s=new SoapObject("http://webser/","sendOffer");
        s.addProperty("userid", userdata.userid);
    s.addProperty("ruserid",ruserid);
        s.addProperty("fori",foritemid);
         s.addProperty("cash",cash);
        SoapPrimitive soapPrimitive = MainWebService.getretryMsg(s, "http://104.199.135.162:8084/TDserverWeb/OfferWebService?wsdl", "http://webser/OfferWebService/sendOfferRequest",0);
int offerid=Integer.parseInt(soapPrimitive.getValue().toString());
        for(int i=0;i<itemid.length;i++)
{
 s=new SoapObject("http://webser/","addofferItem");
    s.addProperty("offerid", offerid);
    s.addProperty("itemid",itemid[i]);

 soapPrimitive = MainWebService.getretryMsg(s, "http://104.199.135.162:8084/TDserverWeb/OfferWebService?wsdl", "http://webser/OfferWebService/addofferItemRequest",0);


}
if(Images!=null) {
    s = new SoapObject("http://webser/", "addOferImage");
    s.addProperty("offerid", offerid);
    s.addProperty("images", Images);
    s.addProperty("pic", 0);
    soapPrimitive = MainWebService.getretryMsg(s, "http://104.199.135.162:8084/TDserverWeb/OfferWebService?wsdl", "http://webser/OfferWebService/addOferImageRequest", 0);
}


       int number=itemid.length;
if(Images!=null)
{
    number++;
}
        String notification;
        if(number==1)
        {
            notification=userdata.name+"has sent item for "+itemname;

        }
        else{
            notification=userdata.name+"has sent multi-items for "+itemname;
        }

        s=new SoapObject("http://webser/","sendOfferNot");
        s.addProperty("offerid", offerid);
        s.addProperty("userid",ruserid);
        s.addProperty("msg",notification);
        soapPrimitive = MainWebService.getretryMsg(s, "http://104.199.135.162:8084/TDserverWeb/OfferWebService?wsdl", "http://webser/OfferWebService/sendOfferNotRequest",0);

    }

}

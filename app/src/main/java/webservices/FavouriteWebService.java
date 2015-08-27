package webservices;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import datamanager.userdata;

/**
 * Created by sharathsind on 2015-07-06.
 */
public class FavouriteWebService {
    public static int[] getfavouInts()
    {
        SoapObject s=new SoapObject("http://webser/","get");
        s.addProperty("userid", userdata.userid);

      SoapPrimitive soapPrimitive = MainWebService.getMsg(s, "http://192.168.2.15:8084/TDserverWeb/WebSericeFavourites?wsdl", "http://webser/WebSericeFavourites/getRequest");



        return null;
    }
    public static int[] removefavouInts()
    {
        SoapObject s=new SoapObject("http://webser/","remove");
        s.addProperty("userid", userdata.userid);

        SoapPrimitive soapPrimitive = MainWebService.getMsg(s, "http://192.168.2.15:8084/TDserverWeb/WebSericeFavourites?wsdl", "http://webser/WebSericeFavourites/removeRequest");



        return null;
    }


}

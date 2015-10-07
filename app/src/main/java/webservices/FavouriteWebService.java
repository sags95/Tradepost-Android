package webservices;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;
import com.sinapp.sharathsind.tradepost.Constants;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.util.Vector;

import data.StringVector;
import datamanager.userdata;

/**
 * Created by sharathsind on 2015-07-06.
 */
public class FavouriteWebService {
    public static int[] getfavouInts()
    {

        SoapObject s=new SoapObject("http://webser/","get");
        s.addProperty("userid", userdata.userid);

      Vector soapPrimitive = MainWebService.getMsg1(s, "http://73.37.238.238:8084/TDserverWeb/WebSericeFavourites?wsdl", "http://webser/WebSericeFavourites/getRequest");
if(soapPrimitive!=null) {
    int i = soapPrimitive.size();
    for(int j=0;j<i;j++)
    {
        SoapObject object=(SoapObject)soapPrimitive.get(j);
        int id=Integer.parseInt(object.getProperty("id").toString());
        int itemid=Integer.parseInt(object.getProperty("itemid").toString());
        ContentValues cv =new ContentValues();
        cv.put("id",id);
        cv.put("itemid",id);
        Constants.db.insert("fav",null,cv);
    }


}
        return null;
    }
    public void invite(Activity a)
    {
        String appLinkUrl, previewImageUrl;

        appLinkUrl = "https://www.mydomain.com/myapplink";
        previewImageUrl = "https://www.mydomain.com/my_invite_image.jpg";

        if (AppInviteDialog.canShow()) {
            AppInviteContent content = new AppInviteContent.Builder()
                    .setApplinkUrl(appLinkUrl)
                    .setPreviewImageUrl(previewImageUrl)
                    .build();
            AppInviteDialog.show(a, content);
        }
    }
    public static int[] removefavouInts()
    {
        SoapObject s=new SoapObject("http://webser/","remove");
        s.addProperty("userid", userdata.userid);

        SoapPrimitive soapPrimitive = MainWebService.getMsg(s, "http://73.37.238.238:8084/TDserverWeb/WebSericeFavourites?wsdl", "http://webser/WebSericeFavourites/removeRequest");



        return null;
    }


}

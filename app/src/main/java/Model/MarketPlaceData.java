package Model;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.facebook.share.widget.LikeView;
import com.sinapp.sharathsind.tradepost.Constants;
import com.sinapp.sharathsind.tradepost.Welcome;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Vector;

import datamanager.Item;
import datamanager.ItemResult;
import datamanager.userdata;
import webservices.MainWebService;

/**
 * Created by HenryChiang on 15-05-25.
 */
public class MarketPlaceData implements Serializable {
    public String itemImage;
    public String proUsername;
    public String itemTitle;
    public int userid;
    public Bitmap[] itemImgs = new Bitmap[4];
    public String image[];
    public ItemResult item;
    private static SoapObject obje;
    private static Vector result;
    private static ArrayList<MarketPlaceData> datas = new ArrayList<MarketPlaceData>();

    public static String[] columns = new String[]{ "itemid", "itemtitle", "itemdescription","itemcondition","itemcategory","itemtags","itemdate","itemlatitude","itemlongitude","itemusername","itemimages","userid"};


    public MarketPlaceData() {
    }


    public static ArrayList<MarketPlaceData> generateSampleDataTest() {
        String repeat = " long";
        final ArrayList<MarketPlaceData> dataTest = new ArrayList<MarketPlaceData>();
        for (int i = 0; i < 30; i++) {
            MarketPlaceData data = new MarketPlaceData();
            data.itemImage = "https://jiresal-test.s3.amazonaws.com/deal3.png";
            data.proUsername = "User";
            data.itemTitle = "Awesome title";
            Random ran = new Random();
            int x = ran.nextInt(i + 10);
            for (int j = 0; j < x; j++)
                data.itemTitle += repeat;
            dataTest.add(data);
        }
        return dataTest;

    }


    public static ArrayList<MarketPlaceData> generateSampleData(Context context) {


        /*
        if (Welcome.isDatabaseExist) {
            ArrayList<MarketPlaceData> tempdata = new ArrayList<MarketPlaceData>();


            Constants.db = context.openOrCreateDatabase("tradepostdb.db", Context.MODE_PRIVATE, null);
            Cursor c;
            c = Constants.db.query("marketplacelisting", columns, null, null, null, null, null);
            Log.d("TOTAL", "Number of listing returned: " + c.getCount());

            c.moveToFirst();
            while (!c.isAfterLast()) {
                MarketPlaceData data = new MarketPlaceData();
                ItemResult ir = new ItemResult();
                ir.item = new Item();

                data.item = ir;
                data.item.item.setItemid(Integer.parseInt(c.getString(0)));
                data.item.item.setItemname(c.getString(1));
                data.item.item.setDescription(c.getString(2));
                data.item.item.setCon(Integer.parseInt(c.getString(3)));
                data.item.item.setCategory(c.getString(4));
                data.item.tags = convertStringToArray(c.getString(5));
                data.item.item.setDateadded(new Date());
                data.item.item.setLatitude(BigDecimal.valueOf(c.getDouble(7)));
                data.item.item.setLongtitude(BigDecimal.valueOf(c.getDouble(8)));
                data.item.username = c.getString(9);
                data.image = convertStringToArray(c.getString(10));

                data.item.item.setUserid(c.getInt(11));



                data.proUsername = data.item.username;
                data.itemTitle = data.item.item.getItemname();
                data.userid = data.item.item.getUserid();
                data.item.images = data.image;

                c.moveToNext();
                tempdata.add(data);
            }
            c.close();

            return tempdata;
        } else {
        }
        */


            obje = new SoapObject("http://webser/", "getItems");
            obje.addProperty("lat", userdata.mylocation.latitude);
            obje.addProperty("longi", userdata.mylocation.Longitude);
            obje.addProperty("rad", userdata.radius);
            result = MainWebService.getMsg1(obje, "http://73.37.238.238:8084/TDserverWeb/GetItems?wsdl"
                    , "http://webser/GetItems/getItemsRequest");

            //doAsync();


            ArrayList<MarketPlaceData> tempdata = new ArrayList<MarketPlaceData>();
            if (result != null) {
                for (Object i : result) {
                    obje = new SoapObject("http://webser/", "getItembyId");
                    obje.addProperty("itemid", Integer.parseInt(((SoapPrimitive) i).getValue().toString()));
                    KvmSerializable result1 = MainWebService.getMsg2(obje, "http://73.37.238.238:8084/TDserverWeb/GetItems?wsdl"
                            , "http://webser/GetItems/getItembyIdRequest");

                    ItemResult ir = new ItemResult();
                    ir.item = new Item();

                    if (result1 != null) {
                        SoapObject object = (SoapObject) result1.getProperty(0);
                        //  for(int u=0;u<object.getPropertyCount())
                        ir.item.set(object);
                        //  SoapObject o=(SoapObject)result1;
                        //     Object j=       o.getProperty("images");
                        int i1 = result1.getPropertyCount();
                        ir.images = new String[i1 - 1];
                        for (int u1 = 1; u1 < i1; u1++) {
                            ir.images[u1 - 1] = result1.getProperty(u1).toString();

                        }
                        obje = new SoapObject("http://webser/", "searchbyint");
                        obje.addProperty("name", i);
                        Vector result2 = MainWebService.getMsg1(obje, "http://73.37.238.238:8084/TDserverWeb/NewWebService?wsdl"
                                , "http://webser/NewWebService/searchbyintRequest");
                        if (result2 != null) {

                            int index = 0;
                            ir.tags = new String[result2.size()];
                            for (Object o : result2) {
                                ir.tags[index] = ((SoapPrimitive) o).getValue().toString();
                                index++;

                            }
                        }
                        obje = new SoapObject("http://webser/", "getusername");
                        obje.addProperty("name", ir.item.getUserid());
                        SoapPrimitive soapPrimitive = MainWebService.getretryMsg(obje, "http://73.37.238.238:8084/TDserverWeb/getUserName?wsdl", "http://webser/getUserName/getusernameRequest", 0);
                        MarketPlaceData data = new MarketPlaceData();
                        ir.username = soapPrimitive.getValue().toString();
                        data.itemImage = "http://73.37.238.238:8084/TDserverWeb/images/items/" + ir.item.getItemid() + "/" + ir.images[0];
                        data.proUsername = ir.username;
                        data.itemTitle = ir.item.getItemname();
                        data.userid = ir.item.getUserid();
                        data.image = new String[ir.images.length];

                        int ij = 0;
                        for (String s : ir.images) {
                            data.image[ij] = "http://73.37.238.238:8084/TDserverWeb/images/items/" + ir.item.getItemid() + "/" + s;

                            ij++;
                        }

                        data.item = ir;

                        /*
                        ContentValues cv = new ContentValues();
                        cv.put("itemid", data.item.item.getItemid().toString());
                        cv.put("itemtitle", data.item.item.getItemname());
                        cv.put("itemdescription", data.item.item.getDescription());
                        cv.put("itemcondition", String.valueOf(data.item.item.getCon()));
                        cv.put("itemcategory", data.item.item.getCategory());
                        cv.put("itemtags", convertArrayToString(data.item.tags));
                        cv.put("itemdate", data.item.item.getDateadded().toString());
                        cv.put("itemlatitude", data.item.item.getLatitude().doubleValue());
                        cv.put("itemlongitude", data.item.item.getLongtitude().doubleValue());
                        cv.put("itemusername", data.item.username);
                        cv.put("itemimages", convertArrayToString(data.image));
                        cv.put("userid", data.item.item.getUserid());
                        Constants.db.insert("marketplacelisting", null, cv);
                        */

                        tempdata.add(data);



                    }
                }
            }

            return tempdata;



}


    public static String strSeparator = "__,__";

    public static String convertArrayToString(String[] array){
        String str = "";
        for (int i = 0;i<array.length; i++) {
            str = str+array[i];
            // Do not append comma at the end of last element
            if(i<array.length-1){
                str = str+strSeparator;
            }
        }
        return str;
    }
    public static String[] convertStringToArray(String str){
        String[] arr = str.split(strSeparator);
        return arr;
    }

}

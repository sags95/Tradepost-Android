package Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.facebook.share.widget.LikeView;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import datamanager.Item;
import datamanager.ItemResult;
import datamanager.userdata;
import webservices.MainWebService;

/**
 * Created by HenryChiang on 15-05-25.
 */
public class MarketPlaceData {
    public String itemImage;
    public String proUsername;
    public String itemTitle;
    public int userid;
    public Bitmap[] itemImgs = new Bitmap[4];
    public String image[];
    public ItemResult item;
    private static SoapObject obje;
    private static Vector result;
    private static ArrayList<MarketPlaceData> datas= new ArrayList<MarketPlaceData>();;

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


    public static ArrayList<MarketPlaceData> generateSampleData() {
        obje=new SoapObject("http://webser/","getItems");
        obje.addProperty("lat", userdata.latitude);
        obje.addProperty("longi",userdata.longitude);
        obje.addProperty("rad",25);
        result= MainWebService.getMsg1(obje,"http://104.199.135.162:8084/TDserverWeb/GetItems?wsdl"
                ,"http://webser/GetItems/getItemsRequest");

        //doAsync();


        ArrayList<MarketPlaceData> tempdata = new ArrayList<MarketPlaceData>();
        if(result!=null){
            for (Object i : result) {
                obje=new SoapObject("http://webser/","getItembyId");
                obje.addProperty("itemid", Integer.parseInt(((SoapPrimitive)i).getValue().toString()));
                    KvmSerializable result1= MainWebService.getMsg2(obje, "http://104.199.135.162:8084/TDserverWeb/GetItems?wsdl"
                            , "http://webser/GetItems/getItembyIdRequest");

                    ItemResult ir= new ItemResult();
                    ir.item=new Item();

                    SoapObject object=(SoapObject)result1.getProperty(0);
                    //  for(int u=0;u<object.getPropertyCount())
                    ir.item.set(object);
                    //  SoapObject o=(SoapObject)result1;
                    //     Object j=       o.getProperty("images");
                    int i1=result1.getPropertyCount();
                    ir.images=new String[i1-1];
                    for(int u1=1;u1<i1;u1++)
                    {
                        ir.images[u1-1]=  result1.getProperty(u1).toString();

                    }
                    obje=new SoapObject("http://webser/","searchbyint");
                    obje.addProperty("name", i);
                    Vector result2= MainWebService.getMsg1(obje, "http://104.199.135.162:8084/TDserverWeb/NewWebService?wsdl"
                            , "http://webser/NewWebService/searchbyintRequest");
                    if(result2!=null) {

                        int index=0;
                        ir.tags=new String[result2.size()];
                        for (Object o:result2 ) {
                            ir.tags[index] = ((SoapPrimitive)o).getValue().toString();
                            index++;

                        }
                    }
                    MarketPlaceData data = new MarketPlaceData();
                    data.itemImage = "http://104.199.135.162:8084/TDserverWeb/images/items/"+ir.item.getItemid()+"/"+ir.images[0];
                    data.proUsername = "User";
                    data.itemTitle = ir.item.getItemname();
                    data.userid=ir.item.getUserid();
                    data.image=new String[ir.images.length];
                    int ij=0;
                    URL url;
                    URLConnection con;
                    InputStream is;
                    for(String s:ir.images)
                    {
                        data.image[ij]="http://104.199.135.162:8084/TDserverWeb/images/items/"+ir.item.getItemid()+"/"+s;
                        try {
                            url = new URL("http://104.199.135.162:8084/TDserverWeb/images/items/"+ir.item.getItemid()+"/0.png");
                            con=url.openConnection();
                            is=  con.getInputStream();
                            Bitmap b= BitmapFactory.decodeStream(is);
                            data.itemImgs[ij]=b;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        ij++;
                    }
                    data.item=ir;
            /*
            Random ran = new Random();
            int x = ran.nextInt(i + 10);
            for (int j = 0; j < x; j++)
                data.itemTitle += repeat;
            */

                    tempdata.add(data);
                }}


            return tempdata;

    }

    public static void doAsync(){

            AsyncTaskRunner runner = new AsyncTaskRunner();
            runner.execute();

    }

    private static class AsyncTaskRunner extends AsyncTask<ArrayList<MarketPlaceData>, String, ArrayList<MarketPlaceData>> {

        @Override
        protected ArrayList<MarketPlaceData> doInBackground(ArrayList<MarketPlaceData>... params) {

            ArrayList<MarketPlaceData> tempdata = new ArrayList<MarketPlaceData>();
            if(result!=null){
                for (Object i : result) {
                    obje=new SoapObject("http://webser/","getItembyId");
                    obje.addProperty("itemid", Integer.parseInt(((SoapPrimitive)i).getValue().toString()));

                    KvmSerializable result1= MainWebService.getMsg2(obje, "http://104.199.135.162:8084/TDserverWeb/GetItems?wsdl"
                            , "http://webser/GetItems/getItembyIdRequest");

                    ItemResult ir= new ItemResult();
                    ir.item=new Item();

                    SoapObject object=(SoapObject)result1.getProperty(0);
                    //  for(int u=0;u<object.getPropertyCount())
                    ir.item.set(object);
                    //  SoapObject o=(SoapObject)result1;
                    //     Object j=       o.getProperty("images");
                    int i1=result1.getPropertyCount();
                    ir.images=new String[i1-1];
                    for(int u1=1;u1<i1;u1++)
                    {
                        ir.images[u1-1]=  result1.getProperty(u1).toString();

                    }
                    obje=new SoapObject("http://webser/","searchbyint");
                    obje.addProperty("name", i);
                    Vector result2= MainWebService.getMsg1(obje, "http://104.199.135.162:8084/TDserverWeb/NewWebService?wsdl"
                            , "http://webser/NewWebService/searchbyintRequest");
                    if(result2!=null) {

                        int index=0;
                        ir.tags=new String[result2.size()];
                        for (Object o:result2 ) {
                            ir.tags[index] = ((SoapPrimitive)o).getValue().toString();
                            index++;

                        }
                    }
                    MarketPlaceData data = new MarketPlaceData();
                    data.itemImage = "http://104.199.135.162:8084/TDserverWeb/images/items/"+ir.item.getItemid()+"/"+ir.images[0];
                    data.proUsername = ir.item.getUsername();
                    data.itemTitle = ir.item.getItemname();
                    data.userid=ir.item.getUserid();
                    data.image=new String[ir.images.length];
                    int ij=0;
                    URL url;
                    URLConnection con;
                    InputStream is;
                    for(String s:ir.images)
                    {
                        data.image[ij]="http://104.199.135.162:8084/TDserverWeb/images/items/"+ir.item.getItemid()+"/"+s;
                        try {
                            url = new URL("http://104.199.135.162:8084/TDserverWeb/images/items/"+ir.item.getItemid()+"/0.png");
                            con=url.openConnection();
                            is=  con.getInputStream();
                            Bitmap b= BitmapFactory.decodeStream(is);
                            data.itemImgs[ij]=b;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        ij++;
                    }
                    data.item=ir;
            /*
            Random ran = new Random();
            int x = ran.nextInt(i + 10);
            for (int j = 0; j < x; j++)
                data.itemTitle += repeat;

            */
                    tempdata.add(data);
                }}

            return tempdata;
        }

        @Override
        protected void onPostExecute(ArrayList<MarketPlaceData> result) {
            datas=result;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(String... text) {

        }
    }
}

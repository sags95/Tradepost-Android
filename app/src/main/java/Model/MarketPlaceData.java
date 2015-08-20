package Model;

import com.facebook.share.widget.LikeView;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

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

    public MarketPlaceData() {
    }



    public static ArrayList<MarketPlaceData> generateSampleDataTest() {
        String repeat = " long";
        final ArrayList<MarketPlaceData> dataTest = new ArrayList<MarketPlaceData>();
        for (int i = 0; i < 10; i++) {
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
        String repeat = " repeat";
        SoapObject obje=new SoapObject("http://webser/","getItems");
        obje.addProperty("lat", userdata.latitude);
        obje.addProperty("longi",userdata.longitude);
        obje.addProperty("rad",25);
        Vector result= MainWebService.getMsg1(obje,"http://192.168.2.15:8084/TDserverWeb/GetItems?wsdl"
                ,"http://webser/GetItems/getItemsRequest");

        final ArrayList<MarketPlaceData> datas = new ArrayList<MarketPlaceData>();
            if(result!=null){
        for (Object i : result) {
           obje=new SoapObject("http://webser/","getItembyId");
            obje.addProperty("itemid", Integer.parseInt(((SoapPrimitive)i).getValue().toString()));

         KvmSerializable result1= MainWebService.getMsg2(obje,"http://192.168.2.15:8084/TDserverWeb/GetItems?wsdl"
                    ,"http://webser/GetItems/getItembyIdRequest");

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

            MarketPlaceData data = new MarketPlaceData();
            data.itemImage = "http://192.168.2.15:8084/TDserverWeb/images/items/"+ir.item.getItemid()+"/"+ir.images[0];
            data.proUsername = "User";
            data.itemTitle = ir.item.getItemname();
            data.userid=ir.item.getUserid();
            /*
            Random ran = new Random();
            int x = ran.nextInt(i + 10);
            for (int j = 0; j < x; j++)
                data.itemTitle += repeat;

            */
            datas.add(data);
        }}
        return datas;

    }
}

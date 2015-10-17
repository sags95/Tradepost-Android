package webservices;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.util.ArrayList;
import java.util.Vector;

import datamanager.Item;
import datamanager.ItemResult;
import datamanager.userdata;

/**
 * Created by sharathsind on 2015-07-06.
 */
public class ItemWebService {
    public static ItemResult getItem(int i) {
        // ItemResult ir=new ItemResult();
        SoapObject obje = new SoapObject("http://webser/", "getItembyId");
        obje.addProperty("itemid", i);
        KvmSerializable result1 = MainWebService.getMsg2(obje, "http://73.37.238.238:8084/TDserverWeb/GetItems?wsdl"
                , "http://webser/GetItems/getItembyIdRequest");

        ItemResult ir =null;

        if (result1 != null) {
             ir = new ItemResult();
            ir.item = new Item();
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


        }
        return ir;
    }
    public static ArrayList<Integer> getItems(int userid)
    {
        SoapObject object = new SoapObject("http://webser/", "getuseritems");
        //SoapObject object = new SoapObject("http://webser/", "getuseritems");
        object.addProperty("userid",  userdata.userid);
        Vector object1 = MainWebService.getMsg1(object,"http://73.37.238.238:8084/TDserverWeb/Search?wsdl","http://webser/Search/getuseritemsRequest");
        ArrayList items=new ArrayList<Integer>();


        if(object1!=null) {
            for (Object i : object1) {
                items.add(Integer.parseInt(((SoapPrimitive) i).getValue().toString()));
            }
        }

       return items;
    }
    public void deleteItem(int i)
    {

    }
}

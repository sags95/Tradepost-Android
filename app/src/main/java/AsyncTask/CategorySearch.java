package AsyncTask;

import android.content.Context;
import android.os.AsyncTask;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.util.Vector;

import datamanager.ItemResult;
import datamanager.userdata;
import webservices.ItemWebService;
import webservices.MainWebService;

/**
 * Created by sharathsind on 2015-08-29.
 */
public class CategorySearch extends AsyncTask<String,String,String> {
    String tag;
    Context c;

    CategorySearch(String a,Context context)
    {
        super();
        tag=a;
        c=context;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    }

    @Override
    protected String doInBackground(String... params) {
        SoapObject obje =new SoapObject("http://webser/","SearchByC");
        obje.addProperty("cat", tag);
        obje.addProperty("longi", userdata.mylocation.Longitude);
        obje.addProperty("lat", userdata.mylocation.latitude);
        obje.addProperty("rad", userdata.radius);
        Vector vector= MainWebService.getMsg1(obje, "http://73.37.238.238:8084/TDserverWeb/Search?wsdl", "http://webser/Search/SearchByCRequest");
        if(vector!=null)
        {
            for(Object a : vector)
            {
                ItemResult ir= ItemWebService.getItem(Integer.parseInt(((SoapPrimitive) a).getValue().toString()));


            }

        }
        return null;
    }
}

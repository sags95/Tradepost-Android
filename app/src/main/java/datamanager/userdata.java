package datamanager;

import com.google.android.gms.common.api.GoogleApiClient;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import services.Mylocation;

/**
 * Created by sharathsind on 2015-05-01.
 */
public class userdata {
public static ArrayList<Integer> items,favourites,offers;
    public static int userid;

    public  static int radius;
public static ArrayList<ItemResult>i;
    public static String city;
    public  static Mylocation mylocation;
    public static boolean loc;
    public static String name;
public static GoogleApiClient m;
}

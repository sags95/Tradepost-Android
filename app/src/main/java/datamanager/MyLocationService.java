package datamanager;

import android.app.Activity;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.sinapp.sharathsind.tradepost.MarketPlaceFragment;
import com.sinapp.sharathsind.tradepost.Welcome;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import services.Mylocation;

/**
 * Created by sharathsind on 2015-07-17.
 */
public class MyLocationService implements LocationListener {
Activity activity;

    public MyLocationService(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onLocationChanged(Location loc) {
userdata.mylocation=new Mylocation();
      userdata.mylocation.Longitude=(float) loc.getLongitude();

      userdata.mylocation.latitude = (float) loc.getLatitude();

        SharedPreferences.Editor editor = activity.getSharedPreferences("loctradepost", activity.MODE_PRIVATE).edit();
        //editor.putInt("rad", radius);
        editor.putFloat("lat", userdata.mylocation.latitude);
        editor.putFloat("long",userdata.mylocation.Longitude);
        editor.commit();
        Geocoder gcd = new Geocoder(activity.getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(loc.getLatitude(),
                    loc.getLongitude(), 1);



            userdata.loc=true;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Welcome.locationManager.removeUpdates(this);
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}


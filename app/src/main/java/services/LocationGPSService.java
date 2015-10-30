package services;

import android.app.Activity;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;



import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import datamanager.userdata;

/**
 * Created by sharathsind on 2015-07-10.
 */
public class LocationGPSService implements LocationListener {
    static  Location location;
 SharedPreferences sp;

    public LocationGPSService(SharedPreferences sp) {
        this.sp = sp;
    }

    @Override

    public void onLocationChanged(Location location) {
double lat=location.getLatitude();
double longi=location.getLongitude();

this.location=location;
        SharedPreferences.Editor editor = sp.edit();
        //editor.putInt("rad", radius);
        editor.putFloat("lat", (float)lat);
        editor.putFloat("long",(float)longi);
        editor.commit();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    public static Location getLocation(Activity a,LocationGPSService ser)
    {
      LocationManager mLocationManager = (LocationManager)a.getSystemService(a.LOCATION_SERVICE);

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,
              10, ser);

return location;
    }


    public static  Mylocation getfromAdress(Activity a,String zipCode)
    {
        final Geocoder geocoder = new Geocoder(a);
        final String zip = "90210";
        try {
            List<Address> addresses = geocoder.getFromLocationName(zipCode, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                // Use the address as needed
                String message = String.format("Latitude: %f, Longitude: %f",
                        address.getLatitude(), address.getLongitude());

                Mylocation l=new Mylocation();
                l.city=address.getLocality();
                l.latitude=(float)address.getLatitude();
                l.Longitude=(float)address.getLongitude();
                return l;
                //return new Location(address.getLatitude(), address.getLongitude());
              //  Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            } else {
                // Display appropriate message when Geocoder services are not available
                return null;
                //Toast.makeToast(this, "Unable to geocode zipcode", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            // handle exception
        }
return null;
    }
}

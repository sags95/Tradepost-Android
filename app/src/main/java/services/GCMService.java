package services;

import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;
import com.sinapp.sharathsind.tradepost.ChatFragment;

/**
 * Created by sharathsind on 2015-06-01.
 */
public class GCMService extends GcmListenerService {
    public static boolean b;
    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);
String message=data.getString("message");
        if(message.contains("message"))
        {
            if(ChatFragment.isAlive)
            {





            }
            else{


            }

        }

    }





}

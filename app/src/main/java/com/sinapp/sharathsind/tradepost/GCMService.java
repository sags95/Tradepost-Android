package com.sinapp.sharathsind.tradepost;

<<<<<<< Updated upstream
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
=======
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
>>>>>>> Stashed changes
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;
<<<<<<< Updated upstream
=======
import com.sinapp.sharathsind.tradepost.ChatFragment;
>>>>>>> Stashed changes

/**
 * Created by sharathsind on 2015-06-01.
 */
public class GCMService extends GcmListenerService {
    public static boolean b;
    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);
<<<<<<< Updated upstream
if(data.getString("type").equals("offer")) {
    String type = data.getString("message");

    NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.sample_img)
                    .setContentTitle("TradePost")
                    .setContentText(type);

    Intent notificationIntent = new Intent(this, notificationoffertesting.class);
    Bundle b=new Bundle();
    String images[]=new String[5];
    notificationIntent.putExtra("im",images);
    // set intent so it does not start a new activity
    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
            Intent.FLAG_ACTIVITY_SINGLE_TOP);
    PendingIntent intent =
            PendingIntent.getActivity(this, 0, notificationIntent, 0);

    mBuilder.setContentIntent(intent);
    int mNotificationId = 001;
=======
String message=data.getString("type");
switch (message)
{
     case "offer":
int offerid=Integer.parseInt(data.getString("offerid")0;
 String msg =data.getString("message");
notifyOffer(offerid,msg);
     break;
    case "aoffer":
        break;
    case "doffer":
        break;
    case "message":
        break;
    case "seen":
        break;
    case "cancel":
        break;
    case "block":
        break;
    case "feedback":
        break;
}
    }
public void notifyOffer(int offerid,String msg)
{










}
public void buildNotification(PendingIntent intent,String msg,int id)
{
    NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.notification_icon)
                    .setContentTitle("Tradepost")
                    .setContentText("msg").setContentIntent(intent);
    int mNotificationId = id;
>>>>>>> Stashed changes
// Gets an instance of the NotificationManager service
    NotificationManager mNotifyMgr =
            (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
    mNotifyMgr.notify(mNotificationId, mBuilder.build());
<<<<<<< Updated upstream
}
    }
=======

}
>>>>>>> Stashed changes





<<<<<<< Updated upstream
=======
}


>>>>>>> Stashed changes

}

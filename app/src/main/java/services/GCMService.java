package services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;
import com.sinapp.sharathsind.tradepost.ChatFragment;
import com.sinapp.sharathsind.tradepost.OfferProcessActivity;
import com.sinapp.sharathsind.tradepost.OfferProcessListingFragment;
import com.sinapp.sharathsind.tradepost.R;

/**
 * Created by sharathsind on 2015-06-01.
 */
public class GCMService extends GcmListenerService {
    public static boolean b;
    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);

        String type = data.getString("message");

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.notification_icon)
                            .setContentTitle("Trade Post")
                            .setContentText(data.getString("message"));

            Intent notificationIntent = new Intent(this, OfferProcessActivity.class);
            // set intent so it does not start a new activity
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent intent =
                    PendingIntent.getActivity(this, 0, notificationIntent, 0);

mBuilder.setContentIntent(intent);
            int mNotificationId = 001;
// Gets an instance of the NotificationManager service
            NotificationManager mNotifyMgr =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
            mNotifyMgr.notify(mNotificationId, mBuilder.build());

    }






}

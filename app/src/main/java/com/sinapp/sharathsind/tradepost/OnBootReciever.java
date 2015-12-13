package com.sinapp.sharathsind.tradepost;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.WakefulBroadcastReceiver;

import java.util.Calendar;

public class OnBootReciever extends WakefulBroadcastReceiver {
    private static final int PERIOD=1000 * 30;  // 5 minutes

    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmManager mgr=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i=new Intent(context, CheckNotfication.class);
        PendingIntent pi=PendingIntent.getBroadcast(context, 0,
                i, PendingIntent.FLAG_CANCEL_CURRENT);
        Calendar cal = Calendar.getInstance();
        // start 30 seconds after boot completed
        cal.add(Calendar.SECOND, 30);
        // fetch every 30 seconds
        // InexactRepeating allows Android to optimize the energy consumption
        mgr.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                cal.getTimeInMillis(), PERIOD, pi);


    }
}

package services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

public class OnBootReciever extends BroadcastReceiver {
    private static final int PERIOD=600000;  // 5 minutes

    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmManager mgr=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i=new Intent(context, CheckNotfication.class);
        PendingIntent pi=PendingIntent.getBroadcast(context, 0,
                i, 0);

        mgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime()+60000,
                PERIOD,
                pi);
    }
}

package services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CheckNotfication extends BroadcastReceiver {
    public CheckNotfication() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        WakefulIntentService.acquireStaticLock(context);

        context.startService(new Intent(context, MyService.class));
    }
}

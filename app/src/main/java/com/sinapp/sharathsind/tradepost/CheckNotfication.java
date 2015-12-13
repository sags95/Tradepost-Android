package com.sinapp.sharathsind.tradepost;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

public class CheckNotfication extends WakefulBroadcastReceiver {
    public CheckNotfication() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        WakefulIntentService.acquireStaticLock(context);

        context.startService(new Intent(context, MyService.class));
    }
}

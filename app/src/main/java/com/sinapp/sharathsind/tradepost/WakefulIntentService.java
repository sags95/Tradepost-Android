package com.sinapp.sharathsind.tradepost;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.view.WindowManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

abstract public class WakefulIntentService extends IntentService {
    private static WakefulIntentService wakeLock;

    abstract void doWakefulWork(Intent intent);

    public static final String LOCK_NAME_STATIC="com.commonsware.android.syssvc.AppService.Static";
    private static PowerManager.WakeLock lockStatic=null;

    public static void acquireStaticLock(Context context) {
        getLock(context).acquire();
    }
    public static void release() {
        if (lockStatic != null) lockStatic.release(); lockStatic = null;
    }
    synchronized static PowerManager.WakeLock getLock(Context context) {
        if(lockStatic!=null)
        if (lockStatic.isHeld()) {lockStatic.release();lockStatic=null;}
        if (lockStatic==null) {

            PowerManager mgr=(PowerManager)context.getSystemService(Context.POWER_SERVICE);
            lockStatic=mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK |
                    PowerManager.ACQUIRE_CAUSES_WAKEUP |
                    PowerManager.ON_AFTER_RELEASE, "WakeLock");
            lockStatic.setReferenceCounted(true);
        }

        return(lockStatic);
    }

    public WakefulIntentService(String name) {
        super(name);
    }

    @Override
     protected void onHandleIntent(Intent intent) {
        try {
            doWakefulWork(intent);
        }
        catch (Exception s)
        {



        }
        finally {
            try {
                lockStatic.release();
                lockStatic=null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
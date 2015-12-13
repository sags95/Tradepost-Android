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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

abstract public class WakefulIntentService extends IntentService {
    abstract void doWakefulWork(Intent intent);

    public static final String LOCK_NAME_STATIC="com.commonsware.android.syssvc.AppService.Static";
    private static PowerManager.WakeLock lockStatic=null;

    public static void acquireStaticLock(Context context) {
        getLock(context).acquire();
    }

    synchronized static PowerManager.WakeLock getLock(Context context) {
        if (lockStatic==null) {
            PowerManager mgr=(PowerManager)context.getSystemService(Context.POWER_SERVICE);

            lockStatic=mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    LOCK_NAME_STATIC);
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
            File sdcard = Environment.getExternalStorageDirectory();
// to this path add a new directory path
            File dir = new File(sdcard.getAbsolutePath()+"/trade/" );
// create this directory if not already created
            dir.mkdir();
// create the file in which we will write the contents
            File file = new File(dir, "tradepost.txt");
            FileOutputStream os= null;
            try {
                os = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
           Date d= Calendar.getInstance().getTime();
            String data = "This is the content of my file"+ new SimpleDateFormat("dd-mm-yyyy hh:mm:ss").format(d);
            try {
                os.write(data.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        finally {
            try {
                getLock(this).release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
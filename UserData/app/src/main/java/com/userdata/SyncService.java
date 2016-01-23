package com.userdata;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by eric chaney on 1/20/16.
 */
public class SyncService extends Service {


    Sync alarm = new Sync();
    public void onCreate()
    {
        super.onCreate();

        alarm.SetAlarm(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        alarm.SetAlarm(this);
        return START_STICKY;
    }

//    @Override
//    public void onStart(Intent intent, int startId)
//    {
//        alarm.SetAlarm(this);
//    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

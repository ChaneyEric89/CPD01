package com.userdata;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ericchaney on 1/20/16.
 */
public class Sync extends BroadcastReceiver {

    ArrayList<String> syncedArray;
    @Override
    public void onReceive(Context context, Intent intent) {

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        syncedArray = new ArrayList<>();


        Toast.makeText(context, "Synced", Toast.LENGTH_LONG).show();


        Intent i = new Intent("getSyncedData");
        i.putExtra("extra", syncedArray); // phoneNo is the sent Number
        //sendBroadcast(intent);

        context.sendBroadcast(i);

        wl.release();

    }

    public void SetAlarm(Context context)
    {
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        //Intent i = new Intent(context, Sync.class);
        Intent i = new Intent("com.userdata.START_ALARM");
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 15, pi); // Millisec * Second * Minute
    }

    public void CancelAlarm(Context context)
    {
        Intent intent = new Intent(context, Sync.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }


//    protected boolean isOnline(){
//
//        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = cm.getActiveNetworkInfo();
//
//        if(netInfo != null && netInfo.isConnectedOrConnecting()){
//
//            return true;
//        }else{
//            return false;
//        }
//
//    }

}

package com.userdata;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by eric chaney on 1/20/16.
 */
public class AutoSync extends BroadcastReceiver{
    Sync alarm = new Sync();
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {

            alarm.SetAlarm(context);
        }
    }
}

package com.example.c196.Utility;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import java.text.DateFormat;
import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        //NotificationCompat.Builder nb = notificationHelper.getChannelNotification("","");
        //notificationHelper.getManager().notify(1, nb.build());

        //cancelAlarm(context);
    }

    public static void onTimeSet(long time, String date)
    {
        String[] dateValues = date.split("/");
        Calendar c = Calendar.getInstance();
        c.set(Integer.valueOf(dateValues[0]), Integer.valueOf(dateValues[1]), Integer.valueOf(dateValues[2]));
        c.set(Calendar.HOUR_OF_DAY, 14);
        c.set(Calendar.MINUTE, 34);
        c.set(Calendar.SECOND, 0);

        //updateTimeText();
        //startAlarm();
    }
}

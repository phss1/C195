package com.example.c196.Utility;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver
{
    public static String title;
    public static String message;
    public static String NOTIFICATION_ID;
    public static String NOTIFICATION;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int notificationId = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(notificationId, notification);

        /*NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(title, message);
        notificationHelper.getManager().notify(notificationId, nb.build());*/
    }



    /*
    private static void updateTimeText(Calendar c)
    {
        String timeText = "AlarmReceiver set for: ";
        timeText+= DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());


    }

    private static void startAlarm(Context context, Calendar c)
    {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm(Context context)
    {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);
    }
*/

}

package com.example.c196.Utility;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.c196.R;

public class AlarmReceiver extends BroadcastReceiver
{
    private String title;
    private String message;
    private int alarmId;

    public static String NOTIFICATION_ID;
    public static String NOTIFICATION;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        String alarmTitle = intent.getStringExtra("title");
        String alarmText = intent.getStringExtra("text");

        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(alarmTitle, alarmText);
        notificationHelper.getManager().notify(UtilityMethods.createUniqueId(), nb.build());

        /*NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(alarmTitle)
                .setContentText(alarmText);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(UtilityMethods.createUniqueId(), builder.build());

        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(alarmTitle, alarmText);
        builder.notify(UtilityMethods.createUniqueId(), nb.build());*/


    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }


    /*public static String title;
    public static String message;
    public static String NOTIFICATION_ID;
    public static String NOTIFICATION;

    public static String NOTIFICATION_ID = "notification_id";
    public static String NOTIFICATION = "notification";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        //TODO method 2
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int notificationId = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(notificationId, notification);

        //TODO method 1
        //NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(title, message);
        notificationHelper.getManager().notify(NOTIFICATION_ID, nb.build());
    }*/



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

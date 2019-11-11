package com.example.c196.Utility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver
{
    private static String title;
    private static String message;
    private static int alarmId;

    @Override
    public void onReceive(Context context, Intent intent)
    {

        int id = getAlarmId();
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(id, nb.build());
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

    public static String getTitle() {
        return title;
    }

    public static void setTitle(String title) {
        AlarmReceiver.title = title;
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        AlarmReceiver.message = message;
    }

    public static int getAlarmId() {
        return alarmId;
    }

    public static void setAlarmId(int alarmId) {
        AlarmReceiver.alarmId = alarmId;
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

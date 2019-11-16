package com.example.c196.Utility;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.c196.R;

public class NotificationHelper extends ContextWrapper
{
    public static final String channel_id = "channelId";
    public static final String channel_name = "channel 1";
    public static final String channel2_id = "channe2Id";
    public static final String channel2_name = "channel 2";
    public static final String channel3_id = "channe3Id";
    public static final String channel3_name = "channel 3";
    private NotificationManager manager;

    public NotificationHelper(Context base)
    {
        super(base);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            createChannels();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannels()
    {
        NotificationChannel channel1 = new NotificationChannel(channel_id, channel_name,
                NotificationManager.IMPORTANCE_DEFAULT);
        channel1.enableLights(true);
        channel1.enableVibration(true);
        channel1.setLightColor(R.color.colorPrimary);
        channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel1);

        NotificationChannel channel2 = new NotificationChannel(channel2_id, channel2_name,
                NotificationManager.IMPORTANCE_DEFAULT);
        channel2.enableLights(true);
        channel2.enableVibration(true);
        channel2.setLightColor(R.color.colorPrimary);
        channel2.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel2);

        NotificationChannel channel3 = new NotificationChannel(channel3_id, channel3_name,
                NotificationManager.IMPORTANCE_DEFAULT);
        channel3.enableLights(true);
        channel3.enableVibration(true);
        channel3.setLightColor(R.color.colorPrimary);
        channel3.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel3);
    }

    public NotificationManager getManager()
    {
        if(manager == null)
        {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return manager;
    }

    public NotificationCompat.Builder getChannelNotification(String title, String message, String channel)
    {


        /*NotificationCompat.Builder builder = channel.contains("one") ?
                new NotificationCompat.Builder(getApplicationContext(), channel_id)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_launcher_background) :
                new NotificationCompat.Builder(getApplicationContext(), channel2_id)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(R.drawable.ic_launcher_background);*/

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        if(channel.contains("one"))
        {
            builder = new NotificationCompat.Builder(getApplicationContext(), channel_id)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.ic_launcher_background);
        }
        else if(channel.contains("two"))
        {
            builder = new NotificationCompat.Builder(getApplicationContext(), channel2_id)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.ic_launcher_background);
        }
        else if(channel.contains("three"))
        {
            builder = new NotificationCompat.Builder(getApplicationContext(), channel3_id)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.ic_launcher_background);
        }

        return builder;
    }
}

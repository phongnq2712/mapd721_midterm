package com.mapd721_quocphongngo_midterm.bot.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import com.mapd721_quocphongngo_midterm.bot.MainActivity;

public class NotificationDecorator {

    private static final String TAG = "NotificationDecorator";
    public static final String CHANNEL_ID = "botChatChannel_1";
    private final Context context;
    private final NotificationManager notificationMgr;

    public NotificationDecorator(Context context, NotificationManager notificationManager) {
        this.context = context;
        this.notificationMgr = notificationManager;
        createChannel(notificationManager, CHANNEL_ID);
    }

    private void createChannel(NotificationManager notificationManager, String channelId){
        NotificationChannel notificationChannel = new NotificationChannel(channelId, "BotChat Channel",
                NotificationManager.IMPORTANCE_LOW);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.GREEN);
        notificationChannel.enableVibration(true);
        notificationManager.createNotificationChannel(notificationChannel);
    }

    public void displaySimpleNotification(String title, String contentText) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_IMMUTABLE);

        // notification message
        try {
            Notification noti = new Notification.Builder(context)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle(title)
                    .setContentText(contentText)
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true)
                    .setChannelId(CHANNEL_ID)
                    .build();

            noti.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationMgr.notify(0, noti);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.getMessage());
        }
    }

}

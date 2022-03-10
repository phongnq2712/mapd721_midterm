package com.mapd721_quocphongngo_midterm.bot.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.util.Log;

import com.mapd721_quocphongngo_midterm.bot.Constants;

public class BotService extends Service {
    private static final String TAG = "BotService";
    public static final String CMD = "cmd";
    public static final int CMD_GENERATE = 10;
    public static final int CMD_STOP = 20;
    private int messageOrder = 0;

    private NotificationManager notificationMgr;
//    private NotificationDecorator notificationDecorator;

    public BotService() {
    }

    @Override
    public void onCreate() {
        Log.v(TAG, "onCreate()");
        super.onCreate();
        notificationMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notificationDecorator = new NotificationDecorator(this, notificationMgr);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(TAG, "onStartCommand()");
        super.onStartCommand(intent, flags, startId);
        if (intent != null) {
            Bundle data = intent.getExtras();
            handleData(data);
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void handleData(Bundle data) {
        int command = data.getInt(CMD);
        Log.d(TAG, "-(<- received command data to service: command=" + command);

        if (command == CMD_GENERATE) {
//            notificationDecorator.displaySimpleNotification("Joining Chat...", "Connecting as User: " + myName);
            messageOrder ++;
            sendBroadcastGenerateMsg(messageOrder);
        }
    }

    private void sendBroadcastGenerateMsg(int order) {
        Log.d(TAG, "->(+)<- sending broadcast: BROADCAST_GENERATE_MESSAGE");
        Intent intent = new Intent();
        intent.setAction(Constants.BROADCAST_GENERATE_MESSAGE);
        Bundle data = new Bundle();
        data.putInt(Constants.GENERATE_MESSAGE_ORDER, order);
        intent.putExtras(data);

        sendBroadcast(intent);
    }
}
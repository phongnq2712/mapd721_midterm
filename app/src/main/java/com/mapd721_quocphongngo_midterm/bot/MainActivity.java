package com.mapd721_quocphongngo_midterm.bot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mapd721_quocphongngo_midterm.bot.service.BotService;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = (TextView) findViewById(R.id.tvResult);
        registerServiceStateChangeReceiver();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mServiceStateChangeReceiver);
    }


    public void generateMsg(View v) {
        // Generate message button clicked.
        Bundle data = new Bundle();
        data.putInt(BotService.CMD, BotService.CMD_GENERATE);
        Intent intent = new Intent(this, BotService.class);
        intent.putExtras(data);
        startService(intent);
    }

    public void stopService(View v) {
        // Stop service button clicked.
        // send notification
        tvResult.setText("Stop Service");
        Bundle data = new Bundle();
        data.putInt(BotService.CMD, BotService.CMD_STOP);
        Intent intent = new Intent(this, BotService.class);
        intent.putExtras(data);
        startService(intent);
    }

    private final BroadcastReceiver mServiceStateChangeReceiver = new BroadcastReceiver() {
        private static final String TAG = "BroadcastReceiver";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Bundle data = intent.getExtras();
            Log.d(TAG, "received broadcast message from service: " + action);

            if (Constants.BROADCAST_GENERATE_MESSAGE.equals(action)) {
                int messageOrder = data.getInt(Constants.GENERATE_MESSAGE_ORDER, 1);

                if(messageOrder == 1) {
                    tvResult.setText("Hello Phong!");
                } else if(messageOrder == 2) {
                    tvResult.setText("How are you?");
                } else if(messageOrder == 3) {
                    tvResult.setText("Good Bye Phong!");
                }
//                ChatMessage chatMessage = new ChatMessage("Status: ", bodyMsg, true);
//                displayMessage(chatMessage);
            } else {
                Log.v(TAG, "do nothing for action: " + action);
            }
        }
    };


    private void registerServiceStateChangeReceiver() {
        Log.d(TAG, "registering service state change receiver...");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROADCAST_GENERATE_MESSAGE);

        registerReceiver(mServiceStateChangeReceiver, intentFilter);
    }
}
package com.example.broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * BroadcastReceiver        广播接收器，四大组件之一，用于接受广播信息并作出相应处理
 * 有序广播：只发送到优先级高的接受者，由此接受者继续向优先级低的接受者传播还是终止广播
 * 无序广播：对接受者完全异步的发送广播，几乎大家都能同时接收到广播
 * 静态注册和动态注册
 */
public class MainActivity extends AppCompatActivity {

    private MBroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receiver = new MBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(receiver, filter);

        Button send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendBroadcast(new Intent("com.example.contentprovider.MY_BROADCAST"));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
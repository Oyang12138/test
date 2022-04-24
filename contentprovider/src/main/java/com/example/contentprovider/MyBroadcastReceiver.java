package com.example.contentprovider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("com.example.contentprovider.MY_BROADCAST".equals(intent.getAction())){
            Toast.makeText(context,"已送达",Toast.LENGTH_SHORT).show();
        }
    }
}

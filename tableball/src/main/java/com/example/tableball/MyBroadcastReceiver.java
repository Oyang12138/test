package com.example.tableball;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("abc", "onReceive: ");
        if ("com.example.tableball.Mybroadcast".equals(intent.getAction())){
            Toast.makeText(context,"已送达",Toast.LENGTH_SHORT).show();
        }
    }
}

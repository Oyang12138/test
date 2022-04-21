package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class StartService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this,"Start onCreate",Toast.LENGTH_SHORT);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,"Start onStartCommand",Toast.LENGTH_SHORT);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"Start onDestroy",Toast.LENGTH_SHORT);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this,"Start onBind",Toast.LENGTH_SHORT);
        return null;
    }
}

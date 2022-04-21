package com.example.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class BindService extends Service {
    private NotificationManager mManager;
    private int NOTIFICATION = R.string.local_service_started;
    public final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder{
        public BindService getService(){
            return BindService.this;
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        CharSequence sequence = getText(R.string.local_service_started);
        PendingIntent intent = PendingIntent.getActivity(this,0,new Intent(this,BindService.class),0);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(sequence)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(getText(R.string.local_service_started))
                .setContentText(sequence)
                .setContentIntent(intent)
                .build();
        mManager.notify(NOTIFICATION,notification);
        Log.e("abc", "onCreate: 通知栏已出");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("abc", "onDestroy: ");
        mManager.cancel(NOTIFICATION);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("abc", "onUnbind: ");
        return super.onUnbind(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("abc", "onBind: ");
        return null;
    }
}

package com.example.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class BindActivity extends AppCompatActivity {
    private BindService service;
    private boolean isBind = false;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e("abc", "onServiceConnected: ");
            service = ((BindService.LocalBinder) iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e("abc", "onServiceDisconnected: ");
            service = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind);

        Button bind = findViewById(R.id.bind);
        bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isBind) {
                    Intent intent = new Intent(BindActivity.this, BindService.class);
                    bindService(intent,connection, Context.BIND_AUTO_CREATE);
                    isBind = true;
                }
            }
        });
        Button unBind = findViewById(R.id.unBind);
        unBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBind) {
                    isBind = false;
                    unbindService(connection);
                    service = null;
                }
            }
        });
    }
}
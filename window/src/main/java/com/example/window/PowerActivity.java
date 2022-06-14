package com.example.window;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;
import android.widget.TextView;

public class PowerActivity extends AppCompatActivity {
    private TextView btn1;
    private TextView btn2;
    private TextView btn3;
    private TextView btn4;
    private PowerManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power);

        manager = (PowerManager) getSystemService(Context.POWER_SERVICE);

//        btn1 = (TextView) findViewById(R.id.switch_1);
//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock lock = manager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "111");
//                lock.acquire();
//                lock.release();
//            }
//        });
//
//        btn2 = (TextView) findViewById(R.id.switch_2);
//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock lock = manager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "222");
//                lock.acquire();
//                lock.release();
//            }
//        });
//
//        btn3 = (TextView) findViewById(R.id.switch_3);
//        btn3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock lock = manager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "333");
//                lock.acquire();
//                lock.release();
//            }
//        });
//
//        btn4 = (TextView) findViewById(R.id.switch_4);
//        btn4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock lock = manager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "444");
//                lock.acquire();
//                lock.release();
//            }
//        });
    }
}
package com.example.tableball;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public TextView textView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.tv_1);

        textView.setOnClickListener((view) -> {
            new AlertDialog.Builder(MainActivity.this).setTitle("系统提示")
                    .setMessage("确定进入?")
                    .setPositiveButton("立即进入",
                            (dialogInterface, i) -> {
                                Log.i("桌面台球","进入游戏");
                            }
                    )
                    .setNegativeButton("取消",
                            (dialogInterface, i) -> {
                                Log.i("桌面台球","退出游戏");
                                finish();
                            }
                    )
                    .show();
        });

    }
}
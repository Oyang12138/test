package com.example.window;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView tel;
    private TextView hand;
    private TextView voice;
    private TextView all;
    private TextView normal;
    private TextView exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toast.makeText(MainActivity.this, "当前屏幕宽为 " + getScreenWidth(this) + ",高为 " + getScreenHeight(this), Toast.LENGTH_SHORT).show();

        tel = findViewById(R.id.tel);
        tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TelActivity.class);
                startActivity(intent);
            }
        });

        hand = findViewById(R.id.hand);
        hand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,HandActivity.class);
                startActivity(intent);
            }
        });

        voice = findViewById(R.id.voice);
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AudioActivity.class);
                startActivity(intent);
            }
        });

        all = findViewById(R.id.show_all);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getSupportActionBar().hide();
                all.setVisibility(View.GONE);
                normal.setVisibility(View.VISIBLE);
            }
        });

        normal = findViewById(R.id.show_normal);
        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
                getWindow().setAttributes(params);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                getSupportActionBar().show();
                normal.setVisibility(View.GONE);
                all.setVisibility(View.VISIBLE);
            }
        });

        exit = findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MyService.class);
                intent.putExtra(MyService.OPERATION, MyService.OPERATION_SHOW);
                startService(intent);
                Toast.makeText(getApplication().getBaseContext(), "悬浮窗已开启", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public static int[] getScreenWindow(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        return new int[]{width, height};
    }

    public static int getScreenWidth(Context context) {
        return getScreenWindow(context)[0];
    }

    public static int getScreenHeight(Context context) {
        return getScreenWindow(context)[1];
    }
}
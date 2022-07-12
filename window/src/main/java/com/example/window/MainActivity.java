package com.example.window;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView music;
    private TextView camera;
    private TextView pager2;
    private TextView pager;
    private TextView drawer;
    private TextView power;
    private TextView msg;
    private TextView tel;
    private TextView hand;
    private TextView voice;
    private TextView all;
    private TextView normal;
    private TextView exit;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if ("关于我们".equals(item.getTitle())) {
            Toast.makeText(MainActivity.this, "详情请致电183****8452", Toast.LENGTH_SHORT).show();
        } else if ("帮助".equals(item.getTitle())) {
            Toast.makeText(MainActivity.this, "暂无服务", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toast.makeText(MainActivity.this, "当前屏幕宽为 " + getScreenWidth(this) + ",高为 " + getScreenHeight(this), Toast.LENGTH_SHORT).show();

        music = findViewById(R.id.music);
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MusicActivity.class);
                startActivity(intent);
            }
        });

        camera = findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });

        pager2 = findViewById(R.id.pager2);
        pager2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewPagerActivity.class);
                startActivity(intent);
            }
        });

        pager = findViewById(R.id.pager);
        registerForContextMenu(pager);
        pager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "LongClick", Toast.LENGTH_SHORT).show();
            }
        });

        drawer = findViewById(R.id.drawer);
        drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DrawerActivity.class);
                startActivity(intent);
            }
        });

        power = findViewById(R.id.power);
        power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PowerActivity.class);
                startActivity(intent);
            }
        });

        msg = findViewById(R.id.msg);
        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MsgActivity.class);
                startActivity(intent);
            }
        });

        tel = findViewById(R.id.tel);
        tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TelActivity.class);
                startActivity(intent);
            }
        });

        hand = findViewById(R.id.hand);
        hand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HandActivity.class);
                startActivity(intent);
            }
        });

        voice = findViewById(R.id.voice);
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AudioActivity.class);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.page_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.one:
                intent = new Intent(MainActivity.this, PagerActivity.class);
                break;
            case R.id.two:
                intent = new Intent(MainActivity.this, PagerTitleStripActivity.class);
                break;
            case R.id.three:
                intent = new Intent(MainActivity.this, PagerTabStripActivity.class);
                break;
            case R.id.four:
                intent = new Intent(MainActivity.this, PagerTabHostActivity.class);
                break;
        }
        if (null != intent) {
            startActivity(intent);
        }
        return true;
    }
}
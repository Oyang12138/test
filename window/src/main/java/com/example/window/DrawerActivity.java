package com.example.window;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

public class DrawerActivity extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout layout;
    private FragmentManager manager;
    private RightFragment fragment;
    private Button btn;
    private View bar;
    private final int RED = 110;
    private final int GREEN = 111;
    private final int BLUE = 112;
    private final int YELLOW = 113;
    private final int GRAY = 114;
    private final int CYAN = 115;
    private final int BLACK = 116;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, RED, 0, "深红");
        menu.add(1, GREEN, 0, "青绿");
        menu.add(1, BLUE, 0, "深蓝");
        menu.add(1, YELLOW, 0, "橘黄");
        menu.add(1, GRAY, 0, "暗灰");
        menu.add(1, CYAN, 0, "天蓝");
        menu.add(1, BLACK, 0, "乌黑");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case RED:
                bar.setBackgroundColor(Color.RED);
                break;
            case GREEN:
                bar.setBackgroundColor(Color.GREEN);
                break;
            case BLUE:
                bar.setBackgroundColor(Color.BLUE);
                break;
            case YELLOW:
                bar.setBackgroundColor(Color.YELLOW);
                break;
            case GRAY:
                bar.setBackgroundColor(Color.GRAY);
                break;
            case CYAN:
                bar.setBackgroundColor(Color.CYAN);
                break;
            case BLACK:
                bar.setBackgroundColor(Color.BLACK);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        ContentFragment contentFragment = new ContentFragment("主页", R.color.white);
        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fly_content, contentFragment).commit();

        fragment = (RightFragment) manager.findFragmentById(R.id.menu);

        bar = findViewById(R.id.bar);
        registerForContextMenu(bar);

        btn = findViewById(R.id.btn);
        btn.setOnClickListener(this);

        layout = findViewById(R.id.drawer);
        layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.END);
        layout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.END);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        fragment.setDrawerLayout(layout);
    }

    @Override
    public void onClick(View view) {
        layout.openDrawer(Gravity.RIGHT);
        layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.END);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.blue:
                btn.setTextColor(Color.BLUE);
                break;
            case R.id.green:
                btn.setTextColor(Color.GREEN);
                break;
            case R.id.red:
                btn.setTextColor(Color.RED);
                break;
            case R.id.white:
                btn.setTextColor(Color.WHITE);
                break;
            case R.id.pink:
                btn.setTextColor(Color.parseColor("#DD9EBA"));
                break;
            case R.id.purple:
                btn.setTextColor(getResources().getColor(R.color.purple_200));
                break;
        }
        return true;
    }
}
package com.example.window;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class MyService extends Service {
    public static final String OPERATION = "operation";
    public static final int OPERATION_SHOW = 100;
    public static final int OPERATION_HIDE = 101;
    private static final String TAG = MyService.class.getSimpleName();
    private Button button;
    private boolean isAdd;
    private ActivityManager activityManager;
    private WindowManager windowManager;
    private WindowManager.LayoutParams params;
    private List<String> homeList;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isHome()) {
                if (!isAdd) {
                    windowManager.addView(button, params);
                    isAdd = true;
                }
            } else {
                if (isAdd) {
                    windowManager.removeView(button);
                    isAdd = false;
                }
            }
            handler.post(this);
        }
    };
//    private Runnable click = new Runnable() {
//        @Override
//        public void run() {
//
//        }
//    };

    @Override
    public void onCreate() {
        super.onCreate();
        homeList = getHome();
        createView();
    }

    private void createView() {
        windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        params.format = PixelFormat.RGBA_8888;
        params.width = 150;
        params.height = 150;
        params.gravity = Gravity.LEFT;
        params.x = 200;
        params.y = 200;

        OnClick click = new OnClick();
        button = new Button(getApplicationContext());
        button.setBackground(getResources().getDrawable(R.drawable.background));
//        button.setBackgroundResource(R.mipmap.window);
        button.setOnClickListener(click);
        button.setOnTouchListener(new View.OnTouchListener() {
            int lastX, lastY;
            int paramX, paramY;
            int dx = 0, dy = 0;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = (int) motionEvent.getRawX();
                        lastY = (int) motionEvent.getRawY();
                        paramX = params.x;
                        paramY = params.y;
                        dx = 0;
                        dy = 0;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        dx = (int) (motionEvent.getRawX() - lastX);
                        dy = (int) (motionEvent.getRawY() - lastY);
                        params.x = paramX + dx;
                        params.y = paramY + dy;
                        windowManager.updateViewLayout(button, params);
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        if (0 == dx && 0 == dy) {
                            Intent intent = new Intent(MyService.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                }
                return true;
            }
        });
        windowManager.addView(button, params);
        isAdd = true;
    }

    public boolean isHome() {
        if (activityManager == null) {
            activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        }
        List<ActivityManager.RunningTaskInfo> infos = activityManager.getRunningTasks(1);
        return homeList.contains(infos.get(0).topActivity.getPackageName());
    }

    private List<String> getHome() {
        List<String> names = new ArrayList<>();
        PackageManager manager = this.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> infos = manager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo info : infos) {
            names.add(info.activityInfo.packageName);
        }
        return names;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int operation = intent.getIntExtra(OPERATION, OPERATION_SHOW);
        switch (operation) {
            case OPERATION_SHOW:
                handler.removeCallbacksAndMessages(null);
                handler.post(runnable);
                break;
            case OPERATION_HIDE:
                handler.removeCallbacksAndMessages(null);
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class OnClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {

        }
    }
}

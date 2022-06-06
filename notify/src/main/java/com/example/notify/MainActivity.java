package com.example.notify;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private MyView view;
    private int x, y;
    private int dx, dy;
    private int l, t, r, b;
    private NotificationManager manager;
    private Notification notification;
    private Context context;
    private TextView time;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Calendar calendar = Calendar.getInstance();
            float s = calendar.get(Calendar.SECOND);
            float m = calendar.get(Calendar.MINUTE);
            float h = calendar.get(Calendar.HOUR_OF_DAY);
            String ss = "00", mm = "00", hh = "00";
            if (10 > s) {
                ss = "0" + (int) s;
            } else {
                ss = String.valueOf((int) s);
            }
            if (10 > m) {
                mm = "0" + (int) m;
            } else {
                mm = String.valueOf((int) m);
            }
            if (10 > h) {
                hh = "0" + (int) h;
            } else {
                hh = String.valueOf((int) h);
            }
            time.setText(hh + ":" + mm + ":" + ss);
            handler.postDelayed(runnable, 100);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        time = findViewById(R.id.time);
        view = findViewById(R.id.view);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        x = (int) view.getX();
                        y = (int) view.getY();
                        l = view.getLeft();
                        t = view.getTop();
                        r = view.getRight();
                        b = view.getBottom();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        dx = (int) (view.getX() - x);
                        dy = (int) (view.getY() - y);
                        view.layout(l + dx, t + dy, r + dx, b + dy);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        if (300 > view.getBottom()) {
                            Notification.Builder builder = new Notification.Builder(context);
                            builder.setContentTitle("通知")
                                    .setContentText("震惊！时钟还能这样用")
                                    .setSubText("点击查看详情")
                                    .setTicker("收到一条讯息")
                                    .setWhen(System.currentTimeMillis())
                                    .setSmallIcon(R.mipmap.phone)
                                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                            notification = builder.build();
                            manager.notify(1, notification);
                        }
                }
                return true;
            }
        });
        handler.post(runnable);
    }
}
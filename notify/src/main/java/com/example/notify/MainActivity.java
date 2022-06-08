package com.example.notify;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private MyView view;
    private int x, y;
    private int dx, dy;
    private int l, t, r, b;
    private NotificationManager manager;
    private AlarmManager alarm;
    private Context context;
    private TextView time;
    private Intent intent;
    private PendingIntent pendingIntent;
    private SharedPreferences preferences;
    private static final int NOTIFY_ID = 10086;
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
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        alarm = (AlarmManager) getSystemService(Service.ALARM_SERVICE);
        preferences = context.getSharedPreferences("com.example.notify.ALARM", Context.MODE_PRIVATE);

        intent = new Intent(context, NotifyActivity.class);
        pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        time = findViewById(R.id.time);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotify();

                setAlarm();
            }
        });
        time.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                manager.cancel(NOTIFY_ID);
                return false;
            }
        });

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
                        break;
                }
                return true;
            }
        });

        handler.post(runnable);
    }

    private void setAlarm() {
        Calendar calendar = Calendar.getInstance();
        AlertDialog dialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());
                c.set(Calendar.HOUR_OF_DAY, hour);
                c.set(Calendar.MINUTE, minute);
                alarm.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("hour", hour);
                editor.putInt("minute", minute);
                editor.commit();
                editor.clear();
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        dialog.show();
    }

    private void sendNotify() {
        Notification notification = new NotificationCompat.Builder(this, "channel_id").setContentTitle(getResources().getText(R.string.clock_title))
                .setContentText(getResources().getText(R.string.clock_content))
                .setSubText(getResources().getText(R.string.clock_sub))
                .setTicker(getResources().getText(R.string.clock_ticker))
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.phone)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .build();
        manager.notify(NOTIFY_ID, notification);
    }
}
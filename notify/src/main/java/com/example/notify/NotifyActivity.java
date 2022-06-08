package com.example.notify;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class NotifyActivity extends AppCompatActivity {
    private static final String TAG = NotifyActivity.class.getSimpleName();
    private static Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);

        ImageView imageView;
        imageView = findViewById(R.id.image);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.clock);
        Drawable drawable = new BitmapDrawable(bitmap);
        imageView.setBackground(drawable);

        Calendar calendar = Calendar.getInstance();
        int minute = calendar.get(Calendar.MINUTE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        SharedPreferences preferences = getSharedPreferences("com.example.notify.ALARM", Context.MODE_PRIVATE);

        if (preferences.getInt("hour", -1) == hour && preferences.getInt("minute", -1) == minute) {
            vibrate();
            new AlertDialog.Builder(NotifyActivity.this)
                    .setTitle("闹钟")
                    .setMessage("早上好 ^~^  现在是 " + hour + ":" + minute)
                    .setPositiveButton("关闭闹铃", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            vibrator.cancel();
                        }
                    }).show();
        }
    }

    private static void vibrate() {
        vibrator.cancel();
        vibrator.vibrate(new long[]{100, 200, 100, 200}, 0);
    }
}
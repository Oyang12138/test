package com.example.sync;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private static TextView reduce;
    private static TextView text;
    private static TextView raise;
    private boolean flag = false;
    private int i;
    private static final int WHAT_TOUCH = 0x01;
    private static final int MAX_VALUE = 6000;
    private static long nowTime;
    private static long lastTime;
    private static long sleepTime = 500L;

    Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.what == WHAT_TOUCH) {
                switch (msg.arg1) {
                    case R.id.reduce:
                        if (i > 0 && i <= MAX_VALUE) {
                            i -= 100;
                        } else if (i <= 0) {
                            i = 0;
                        }
                        break;
                    case R.id.raise:
                        if (i >= 0 && i < MAX_VALUE) {
                            i += 100;
                        } else if (i >= MAX_VALUE) {
                            i = MAX_VALUE;
                        }
                        break;
                }
                text.setText(String.valueOf(i));
                Message message = new Message();
                message.what = msg.what;
                message.arg1 = msg.arg1;
                handler.sendMessageDelayed(message,500);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reduce = findViewById(R.id.reduce);
        text = findViewById(R.id.text);
        raise = findViewById(R.id.raise);

        reduce.setOnTouchListener(this);
        raise.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
//        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && flag) {
//            return false;
//        }
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                flag = true;
                i = Integer.parseInt(text.getText().toString());

                    Message message = new Message();
                    message.what = WHAT_TOUCH;
                    message.arg1 = view.getId();
                    handler.sendMessage(message);
                Log.i("TAG", "onTouch: " + i);
                break;
            case MotionEvent.ACTION_UP:
                handler.removeCallbacksAndMessages(null);
                text.setText(String.valueOf(i));
                flag = false;
                break;
        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
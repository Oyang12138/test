package com.example.window;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HandActivity extends AppCompatActivity {
    private TextView button;
    private TextView down;
    private TextView showPress;
    private TextView single;
    private TextView scroll;
    private TextView longPress;
    private TextView fling;
    private GestureDetector detector;
    private MyGestureListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand);

        down = findViewById(R.id.down);
        showPress = findViewById(R.id.show_press);
        single = findViewById(R.id.single);
        longPress = findViewById(R.id.long_press);
        scroll = findViewById(R.id.scroll);
        fling = findViewById(R.id.fling);

        listener = new MyGestureListener();
        detector = new GestureDetector(this, listener);

        button = findViewById(R.id.button);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return detector.onTouchEvent(motionEvent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                down.setTextColor(getResources().getColor(android.R.color.transparent));
                showPress.setTextColor(getResources().getColor(android.R.color.transparent));
                single.setTextColor(getResources().getColor(android.R.color.transparent));
                scroll.setTextColor(getResources().getColor(android.R.color.transparent));
                longPress.setTextColor(getResources().getColor(android.R.color.transparent));
                fling.setTextColor(getResources().getColor(android.R.color.transparent));
            }
        });
    }

    private class MyGestureListener implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            down.setTextColor(getResources().getColor(android.R.color.holo_blue_bright));
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {
            showPress.setTextColor(getResources().getColor(android.R.color.holo_blue_bright));
        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            single.setTextColor(getResources().getColor(android.R.color.holo_blue_bright));
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            scroll.setTextColor(getResources().getColor(android.R.color.holo_blue_bright));
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {
            longPress.setTextColor(getResources().getColor(android.R.color.holo_blue_bright));
        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            fling.setTextColor(getResources().getColor(android.R.color.holo_blue_bright));
            return false;
        }
    }
}
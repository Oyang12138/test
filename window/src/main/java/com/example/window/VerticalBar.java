package com.example.window;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.SeekBar;
import android.widget.Toast;

public class VerticalBar extends SeekBar {
    public VerticalBar(Context context) {
        super(context);
    }

    public VerticalBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    @Override
    protected void onDraw(Canvas c) {
        c.rotate(-90);
        c.translate(-getWidth(), 0);
        super.onDraw(c);
    }

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
        onSizeChanged(getWidth(), getHeight(), 0, 0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                Log.e("abc", "onTouchEvent1: " + event.getY());
                Log.e("abc", "onTouchEvent1: " + getY());
                Log.e("abc", "onTouchEvent2: " + getTop());
//                break;

            case MotionEvent.ACTION_MOVE:

            case MotionEvent.ACTION_UP:
                Log.e("abc", "onTouchEvent: " + getMax());
                setProgress(getMax() - (int) (getMax() * event.getY() / getHeight()));
                onSizeChanged(getHeight(), getWidth(), 0, 0);
                break;

            case MotionEvent.ACTION_CANCEL:
                break;

        }
        return true;
    }
}

package com.example.images;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

public class RightSlideFinishView extends FrameLayout {
    private GestureDetector mGD;
    private Activity mActivity;

    public RightSlideFinishView(@NonNull Context context) {
        super(context);
        mGD = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                int dx = (int) (e2.getX() - e1.getX());
                if (onTouchEvent(e1) && onTouchEvent(e2) && Math.abs(dx) > 600 && Math.abs(velocityX) > Math.abs(velocityY)){
                    if (velocityX > 0){
                        Log.i("rightSlide","rightSlide");
                        mActivity.finish();
                    }else {
                        Log.i("leftSlide","leftSlide");
                    }
                    return true;
                }else {
                    if (velocityY < 0){
                        Log.i("upSlide","upSlide");
                    }else {
                        Log.i("downSlide","downSlide");
                    }
                    return false;
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        mGD.onTouchEvent(motionEvent);
        if (motionEvent.getPointerCount() != 1){
            return false;
        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent){
        return mGD.onTouchEvent(motionEvent);
    }

    public void attachToActivity(Activity activity){
        mActivity = activity;
        TypedArray a = activity.getTheme().obtainStyledAttributes(new int[]{ android.R.attr.windowBackground});
        int background = a.getResourceId(0,0);
        a.recycle();

        ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        decorChild.setBackgroundResource(background);
        decor.removeView(decorChild);
        addView(decorChild);
        decor.addView(this);
    }
}

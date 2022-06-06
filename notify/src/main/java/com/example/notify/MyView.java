package com.example.notify;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;

public class MyView extends View {
    private float hourDegree;
    private float minuteDegree;
    private float secondDegree;
    private Paint paint;
    private Paint mSecondPaint = new Paint();
    private Paint mMinutePaint = new Paint();
    private Paint mHourPaint = new Paint();
    private Canvas canvas;
    private Path mSecondHandPath = new Path();
    private Path mMinuteHandPath = new Path();
    private Path mHourHandPath = new Path();

    public MyView(Context context) {
        super(context);
        init();
    }

    public MyView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public MyView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.purple_200));
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(36);
        paint.setStrokeWidth(5);
        mSecondPaint.setColor(getResources().getColor(android.R.color.holo_orange_light));
        mMinutePaint.setColor(getResources().getColor(android.R.color.holo_green_dark));
        mHourPaint.setColor(getResources().getColor(android.R.color.holo_blue_dark));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawColor(getResources().getColor(android.R.color.darker_gray));
//        canvas.drawCircle(200, 200, 100, paint);
//        canvas.drawRect(0, 0, 200, 100, paint);
//        canvas.drawArc(new RectF(0, 0, 700, 700), 0, 90, true, paint);
//        canvas.drawArc(new RectF(0, 0, 1000, 1000), 0, 90, false, paint);
//        canvas.drawRoundRect(new RectF(500, 100, 200, 100), 15, 15, paint);
//        canvas.drawOval(new RectF(400, 100, 800, 300), paint);
//
//        Path path = new Path();
//        path.moveTo(200, 1000);
//        path.lineTo(300, 1050);
//        path.lineTo(400, 1000);
//        path.lineTo(300, 1000);
//        path.lineTo(200, 1100);
//        path.close();
//        canvas.drawPath(path, paint);

//        paint.setColor(getResources().getColor(R.color.black));
//        canvas.drawCircle(450, 1500, 300, paint);
//        paint.setColor(getResources().getColor(R.color.white));
//        canvas.drawCircle(451, 1501, 297, paint);
//        paint.setColor(getResources().getColor(R.color.black));
//        canvas.drawCircle(450, 1500, 10, paint);
//        canvas.drawRect(448, 1500, 454, 1600, paint);
//        canvas.drawRect(448, 1500, 454, 1300, paint);
//        canvas.drawRect(448, 1200, 454, 1225, paint);
//        canvas.drawText("12", 435, 1255, paint);
//        canvas.drawRect(448, 1775, 454, 1800, paint);
//        canvas.drawText("6", 440, 1770, paint);
//        canvas.drawRect(150, 1498, 175, 1504, paint);
//        canvas.drawText("9", 180, 1512, paint);
//        canvas.drawRect(725, 1498, 750, 1504, paint);
//        canvas.drawText("3", 700, 1512, paint);

        paint.setColor(getResources().getColor(android.R.color.black));
        canvas.drawCircle(530, 1000, 400, paint);
        paint.setColor(getResources().getColor(android.R.color.white));
        canvas.drawCircle(530, 1000, 395, paint);
        paint.setColor(getResources().getColor(android.R.color.black));

        canvas.drawCircle(530, 1000, 10, paint);
        canvas.drawRect(528, 605, 534, 625, paint);
        canvas.drawRect(528, 1375, 534, 1395, paint);
        canvas.drawRect(135, 1004, 155, 998, paint);
        canvas.drawRect(905, 1004, 925, 998, paint);

        canvas.drawText("12", 512, 655, paint);
        canvas.drawText("6", 520, 1370, paint);
        canvas.drawText("9", 160, 1015, paint);
        canvas.drawText("3", 880, 1015, paint);

        paint.setColor(getResources().getColor(android.R.color.holo_blue_light));

        this.canvas = canvas;
        getCurrentTime();
        drawSecond();
        drawMinute();
        drawHour();
        invalidate();
    }

    public void getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        float mill = calendar.get(Calendar.MILLISECOND);
        float s = calendar.get(Calendar.SECOND) + mill / 1000;
        float m = calendar.get(Calendar.MINUTE) + s / 60;
        float h = calendar.get(Calendar.HOUR) + m / 60;
        secondDegree = s / 60 * 360;
        minuteDegree = m / 60 * 360;
        hourDegree = h / 12 * 360;
    }

    public void drawSecond() {
        canvas.save();
        canvas.rotate(secondDegree, 530, 1000);
        mSecondHandPath.reset();
        mSecondHandPath.moveTo(getWidth() / 2, 700f);
        mSecondHandPath.lineTo(getWidth() / 2 - 2f, 900f);
        mSecondHandPath.lineTo(getWidth() / 2 + 2f, 900f);
        mSecondHandPath.close();
        canvas.drawPath(mSecondHandPath, mSecondPaint);
        canvas.restore();
    }

    public void drawMinute() {
        canvas.save();
        canvas.rotate(minuteDegree, 530, 1000);
        mMinuteHandPath.reset();
        mMinuteHandPath.moveTo(getWidth() / 2, 700f);
        mMinuteHandPath.lineTo(getWidth() / 2 - 6f, 850f);
        mMinuteHandPath.lineTo(getWidth() / 2 + 6f, 850f);
        mMinuteHandPath.close();
        canvas.drawPath(mMinuteHandPath, mMinutePaint);
        canvas.restore();
    }

    public void drawHour() {
        canvas.save();
        canvas.rotate(hourDegree, 530, 1000);
        mHourHandPath.reset();
        mHourHandPath.moveTo(getWidth() / 2, 700f);
        mHourHandPath.lineTo(getWidth() / 2 - 10f, 800f);
        mHourHandPath.lineTo(getWidth() / 2 + 10f, 800f);
        mHourHandPath.close();
        canvas.drawPath(mHourHandPath, mHourPaint);
        canvas.restore();
    }
}

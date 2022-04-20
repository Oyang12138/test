package com.example.photo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private TextView text;
    private Button btn;
    private SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss:SSS");
    private MyDatabaseHelper myDatabaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    ContentValues contentValues;
    Cursor cursor;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDatabaseHelper = new MyDatabaseHelper(this, "aaa", null, 1);

        sqLiteDatabase = myDatabaseHelper.getWritableDatabase();
        Drawable drawable = getBaseContext().getResources().getDrawable(R.drawable.icon);
        contentValues = new ContentValues();
        contentValues.put("picValue",getPicture(drawable));
        sqLiteDatabase.insert("pic", null, contentValues);
        contentValues.clear();
        text = findViewById(R.id.text);
        cursor = sqLiteDatabase.rawQuery("select picValue from pic;", null);
        while (cursor.moveToNext()) {
            byte[] bytes = cursor.getBlob(cursor.getColumnIndexOrThrow("picValue"));
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length,null);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
            Drawable drawable1 = bitmapDrawable;
            drawable1.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
            text.setCompoundDrawables(drawable,null,null,null);
        }
        cursor.close();
        sqLiteDatabase.close();
        btn = findViewById(R.id.btn);
        while (cursor.moveToNext()) {
            text.setText(String.valueOf(cursor.getString(0)));
        }

        text.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                text.setTextColor(R.color.purple_200);
//                System.out.println(format.format(new Date()));
                Log.i("testDateTime", "now:" + format.format(new Date()));
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                text.setTextColor(R.color.black);
//                System.out.println(format.format(new Date()));
                String time = format.format(new Date());
                Log.i("testDateTime", "now:" + time);
                sqLiteDatabase = myDatabaseHelper.getWritableDatabase();
                cursor = sqLiteDatabase.rawQuery("select time from currentTime;", null);
                if (cursor != null) {
                    sqLiteDatabase.delete("currentTime", null, null);
                }
                contentValues = new ContentValues();
                contentValues.put("time", time);
                sqLiteDatabase.insert("currentTime", null, contentValues);
                contentValues.clear();
                cursor = sqLiteDatabase.rawQuery("select time from currentTime;", null);
                String sTime = "";
                while (cursor.moveToNext()) {
//                    String t = cursor.getString(0);
//                    char[] cs = new char[t.length()];
//                    for (int i = 0; i < t.length(); i++) {
//                        cs[i] = t.charAt(i);
//                    }
//                    text.setText(cs, 0, cs.length);
                    sTime = cursor.getString(0);
                }
                text.setText(sTime);
                cursor.close();
                sqLiteDatabase.close();
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                intent.putExtra("time", sTime);
                startActivity(intent);
            }
        });
    }

    private byte[] getPicture(Drawable drawable){
        if (drawable == null){
            return null;
        }
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        while (true) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    text.setText(String.valueOf(++i));
                case MotionEvent.ACTION_UP:
                    text.setText(String.valueOf(--i));
            }
            try {
                Thread.sleep(300);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
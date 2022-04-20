package com.example.ice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private ImageView imageView;
    private MyDatabaseHelper myDatabaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    ContentValues contentValues;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        imageView = findViewById(R.id.image);
        myDatabaseHelper = new MyDatabaseHelper(this, "myDataBase", null, 1); //名称
        sqLiteDatabase = myDatabaseHelper.getReadableDatabase();                                          //建库
        Drawable drawable = this.getResources().getDrawable(R.drawable.image);                            //得到
        contentValues = new ContentValues();                                                              //容器
        contentValues.put("picValue",getPicture(drawable));                                               //植入
        sqLiteDatabase.update("pic", contentValues,null,null);                 //增改
        contentValues.clear();                                                                            //清空
        cursor = sqLiteDatabase.rawQuery("select picValue from pic;", null);              //查询
        while (cursor.moveToNext()) {                                                                     //存在
            byte[] bytes = cursor.getBlob(cursor.getColumnIndexOrThrow("picValue"));             //获取
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length,null);   //转型
            Bitmap bt = Bitmap.createScaledBitmap(bitmap, 40, 50, false);   //调整
            BitmapDrawable bitmapDrawable = new BitmapDrawable(bt);                                 //转换
            imageView.setImageDrawable(bitmapDrawable);                                             //放置
        }
        cursor.close();             //关闭
        sqLiteDatabase.close();     //关闭
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
}
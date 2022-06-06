package com.example.notify;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

public class NotifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);

        ImageView imageView;
        imageView = findViewById(R.id.image);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.clock);
        Drawable drawable = new BitmapDrawable(bitmap);
        imageView.setBackground(drawable);
    }
}
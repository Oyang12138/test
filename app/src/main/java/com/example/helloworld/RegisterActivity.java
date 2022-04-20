package com.example.helloworld;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {
    private ImageView imageView;
    private EditText userName;
    private EditText password;
    private Button register;
    private Button cancel;
    private MyDatabaseHelper myDatabaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        cancel = findViewById(R.id.cencle);
        imageView = findViewById(R.id.photo);
        context = this;

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_PICK, null);
//                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "*image/*");
//                startActivityForResult(intent, 1);
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, 2);
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!"".equals(String.valueOf(userName.getText())) && !"".equals(String.valueOf(password.getText()))) {
                    myDatabaseHelper = new MyDatabaseHelper(context, "app", null, 1);
                    sqLiteDatabase = myDatabaseHelper.getReadableDatabase();
                    Cursor cursor = sqLiteDatabase.rawQuery("select name from pic where name = '" + String.valueOf(userName.getText()) + "'", null);
                    if (cursor.moveToNext()) {
                        Toast.makeText(context, "用户名已存在", Toast.LENGTH_SHORT).show();
                    } else {
                        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] bytes = byteArrayOutputStream.toByteArray();
                        try {
                            byteArrayOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("picValue", bytes);
                        contentValues.put("name", String.valueOf(userName.getText()));
                        contentValues.put("pwd", String.valueOf(password.getText()));
                        contentValues.put("status", false);
                        sqLiteDatabase.insert("pic", null, contentValues);
                        contentValues.clear();
                        cursor.close();
                        sqLiteDatabase.close();
                        Toast.makeText(context, "注册成功，请登录", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Toast.makeText(context, "请输入账号密码", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, 2);
                } else {
                    Toast.makeText(this, "你拒绝了该权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 2:
                if(data != null){
                    Uri uri = data.getData();
                    if (uri != null){
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(),uri);
                            Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap,40,50,false);
                            BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap1);
                            imageView.setImageDrawable(bitmapDrawable);
//                            imageView.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            default:
                break;
        }
    }
}
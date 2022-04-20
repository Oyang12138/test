package com.example.helloworld;

import android.content.Context;
import android.content.Intent;
import android.database.AbstractWindowedCursor;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //    声明控件
    private Button myBtnLogin;
    private Button myBtnRegister;
    private EditText mEtUser;
    private EditText mEtPassword;
    private ImageView imageView;
    private MyDatabaseHelper myDatabaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        找到控件
        myBtnLogin = findViewById(R.id.btn_login);
        imageView = findViewById(R.id.image);
        context = this;
        mEtUser = findViewById(R.id.et_1);
        mEtPassword = findViewById(R.id.et_2);
        myBtnRegister = findViewById(R.id.btn_region);
        myBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        mEtUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void afterTextChanged(Editable editable) {
                String username = mEtUser.getText().toString();
                myDatabaseHelper = new MyDatabaseHelper(context, "app", null, 1);
                sqLiteDatabase = myDatabaseHelper.getReadableDatabase();
                Cursor cursor = sqLiteDatabase.rawQuery("select picValue from pic where name = '" + username + "'", null);
//                CursorWindow cursorWindow = new CursorWindow("test",5000);
//                AbstractWindowedCursor abstractWindowedCursor = (AbstractWindowedCursor) cursor;
//                abstractWindowedCursor.setWindow(cursorWindow);
                if (cursor.moveToNext()) {
                    byte[] bytes = cursor.getBlob(0);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);    //调整
                    BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);                                 //转换
                    imageView.setImageDrawable(bitmapDrawable);
                }else{
                    imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.studio));
                }
                cursor.close();
                sqLiteDatabase.close();
            }
        });
        myBtnLogin.setOnClickListener(this);
    }

    //    需要获取输入的用户名和密码
    public void onClick(View view) {
        String username = mEtUser.getText().toString();
        String password = mEtPassword.getText().toString();
        Intent intent = null;

        myDatabaseHelper = new MyDatabaseHelper(context, "app", null, 1);
        sqLiteDatabase = myDatabaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select pwd from pic where name = '" + username + "'", null);
        if (cursor.moveToNext()) {
            String selectPwd = cursor.getString(0);
            if (selectPwd.equals(password)) {
//            如果符合则跳转
                intent = new Intent(MainActivity.this, SlideActivity.class);
                intent.putExtra("name",username);
                startActivity(intent);
//               登陆成功 normal
                Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
            } else {
//                否则提示用户名或密码错误 居中提示
                Toast toastCenterShow = Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_SHORT);
                toastCenterShow.setGravity(Gravity.CENTER, 0, 0);
                toastCenterShow.show();
            }
        } else {
            if ("".equals(username)){
                Toast.makeText(getApplicationContext(), "请输入用户名", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(), "用户名不存在", Toast.LENGTH_SHORT).show();
            }
        }
        cursor.close();
        sqLiteDatabase.close();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
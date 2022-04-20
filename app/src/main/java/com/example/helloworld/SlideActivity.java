package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SlideActivity extends AppCompatActivity {

    private ImageView mIvHead;
    private TextView mText;
    private TextView mExit;
    private SlideMenu slideMenu;
    private Button mBtnSu;
    private Button mBtnHappy;
    private Button mBtnTable;
    private Button mBtnGun;
    private MyDatabaseHelper myDatabaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        Intent i = getIntent();
        String username = i.getStringExtra("name");

        mIvHead = findViewById(R.id.iv_head);
        mText = findViewById(R.id.titleText);
        mExit = findViewById(R.id.exit);
        mText.setText(String.valueOf(username));
        slideMenu = findViewById(R.id.slideMenu);
        mBtnSu = findViewById(R.id.btn_main_1);
        mBtnHappy = findViewById(R.id.btn_main_2);
        mBtnTable = findViewById(R.id.btn_main_3);
        mBtnGun = findViewById(R.id.btn_main_4);
        context = this;

        myDatabaseHelper = new MyDatabaseHelper(context, "app", null, 1);
        sqLiteDatabase = myDatabaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select picValue from pic where name = '" + username + "'", null);
        if (cursor.moveToNext()) {
            byte[] bytes = cursor.getBlob(0);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);    //调整
            BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);                                 //转换
            mIvHead.setImageDrawable(bitmapDrawable);
        }

        mExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mIvHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slideMenu.switchMenu();
            }
        });

        setListener();
    }

    private void setListener() {
        OnClick onClick = new OnClick();

        mBtnSu.setOnClickListener(onClick);
        mBtnHappy.setOnClickListener(onClick);
        mBtnTable.setOnClickListener(onClick);
        mBtnGun.setOnClickListener(onClick);

    }

    private class OnClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = null;
            PackageManager packageManager = context.getPackageManager();
            switch (view.getId()) {
                case R.id.btn_main_1:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        // 检查该权限是否已经获取
                        // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                        List<String> permissionList = new ArrayList<>();
                        if (ContextCompat.checkSelfPermission(context, Manifest.
                                permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
                        }
                        if (ContextCompat.checkSelfPermission(context, Manifest.
                                permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                            permissionList.add(Manifest.permission.READ_PHONE_STATE);
                        }
                        if (ContextCompat.checkSelfPermission(context, Manifest.
                                permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        }
                        if (!permissionList.isEmpty()) {
                            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
                            ActivityCompat.requestPermissions(SlideActivity.this, permissions, 1);
                        } else {

                        }
                    }
                    //只有安装包名
                    String archiveFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/images-release.apk";
                    PackageInfo info = packageManager.getPackageArchiveInfo(archiveFilePath,PackageManager.GET_ACTIVITIES);
                    if (info != null){
                        ApplicationInfo applicationInfo = info.applicationInfo;
                        String packageName = applicationInfo.packageName;
                        intent = packageManager.getLaunchIntentForPackage(packageName);
                    }
                    break;
                case R.id.btn_main_2:
                    intent = new Intent(SlideActivity.this, HappyActivity.class);
                    break;
                case R.id.btn_main_3:
                    //通过包名和类名启动
                    intent = new Intent();
                    ComponentName componentName = new ComponentName("com.tencent.mobileqq","com.tencent.mobileqq.activity.SplashActivity");
                    intent.setComponent(componentName);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent = packageManager.getLaunchIntentForPackage("com.tencent.mobileqq");
                    break;
                case R.id.btn_main_4:
//                    通过包名启动
//                    intent = packageManager.getLaunchIntentForPackage("com.tencent.tmgp.pubgmhd");
                    intent = new Intent();
                    intent.setComponent(new ComponentName("com.tencent.tmgp.pubgmhd","com.epicgames.ue4.SplashActivity"));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    break;

                default:
                    break;
            }
            if (intent != null){
                startActivity(intent);
            }
        }
    }
}
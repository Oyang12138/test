package com.example.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Service是Android四大组件之一，它可以在后台执行长时间运行操作而没有用户界面的应用组件
 * Service的启动方式有两种:
 *      1.startService启动     通过该方式启动Service，访问者与Service之间没有关联，即使访问者退出了，Service也仍然运行。
 *              显式启动通过类名称来启动，需要在Intent中指明Service所在的类，并调用startService (Intent)启动service
 *              显式启动停止Service，需要将启动Service的Intent传递给stopService (Intent)函数
 *      2.bindService启动      通过该方式启动Service，访问者与Service绑定在一起，访问者一旦退出了，Service也就终止了。
 *              绑定模式使用bindService()方法启动Service。  bindService(Intent service,ServiceConnection conn,int flags);
 *                      service:该参数通过Intent指定需要启动的service
 *                      conn:该参数是ServiceConnection对象，当绑定成功后，系统将调用serviceConnection的onServiceConnected ()方法，当绑定意外断开后，系统将调用ServiceConnection中的onServiceDisconnected方法。
 *                      flags:该参数指定绑定时是否自动创建Service。如果指定为BIND_AUTO_CREATE，则自动创建，指定为0，则不自动创建。
 *              使用unbindService()方法取消绑定
 *                      取消绑定仅需要使用unbindService()方法，并将ServiceConnection传递给unbindService()方法。
 *                      通过unbindService()函数取消绑定Service时，onUnbind()函数将被调用。如果onUnbind()函数返回true，则表示重新绑定服务时，onRebind (）函数将被调用。
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button start = findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });
    }
}
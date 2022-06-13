package com.example.window;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TelActivity extends AppCompatActivity {
    private TextView number;
    private TextView version;
    private TextView code;
    private TextView name;
    private TextView type;
    private TextView location;
    private TextView country;
    private TextView cardNum;
    private TextView status;
    private TextView strength;
    private TextView phone;
    private TelephonyManager manager;
    private MyPhoneStateListener listener;
    private String[] types = {"未知", "移动", "电信", "网络电话"};
    private String[] states = {"未知", "无SIM卡", "被PIN加锁", "被PUK加锁", "被NetWork PIN加锁", "已准备好"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tel);

        listener = new MyPhoneStateListener();
        manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        manager.listen(listener, 290);

        number = (TextView) findViewById(R.id.number);
        version = (TextView) findViewById(R.id.version);
        code = (TextView) findViewById(R.id.code);
        name = (TextView) findViewById(R.id.name);
        type = (TextView) findViewById(R.id.type);
        location = (TextView) findViewById(R.id.location);
        country = (TextView) findViewById(R.id.country);
        cardNum = (TextView) findViewById(R.id.card_num);
        status = (TextView) findViewById(R.id.status);
        strength = (TextView) findViewById(R.id.strength);
        phone = (TextView) findViewById(R.id.phone);
        phone.setText("\n来电号码");

        try {
            number.append(Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        version.append(manager.getDeviceSoftwareVersion() != null ? manager.getDeviceSoftwareVersion() : "未知版本");
        code.append(manager.getNetworkOperator());
        name.append(manager.getNetworkOperatorName());
        type.append(types[manager.getPhoneType()]);
        location.append(manager.getCellLocation() != null ? (CharSequence) manager.getCellLocation() : "未知位置");
        country.append(manager.getSimCountryIso());
        try {
            Class c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            cardNum.append(get.invoke(c, "ro.serialno").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        status.append(states[manager.getSimState()]);

        PhoneStateListener phoneStateListener = new PhoneStateListener(){
            @Override
            public void onCallStateChanged(int state, String phoneNumber) {
                switch (state){
                    case TelephonyManager.CALL_STATE_RINGING:
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        phone.append(phoneNumber + "  dateTime-->" + format.format(new Date()));
                        break;

                }
                super.onCallStateChanged(state, phoneNumber);
            }
        };
        manager.listen(phoneStateListener,PhoneStateListener.LISTEN_CALL_STATE);
    }

    private class MyPhoneStateListener extends PhoneStateListener {
        private int asu = 0;
        private int lastSignal = 0;

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            asu = signalStrength.getGsmSignalStrength();
            lastSignal = -113 + 2 * asu;
            strength.setText("信号强度：");
            strength.append(lastSignal + "dBm");
            super.onSignalStrengthsChanged(signalStrength);
        }
    }
}
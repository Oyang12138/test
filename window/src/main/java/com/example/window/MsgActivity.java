package com.example.window;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MsgActivity extends AppCompatActivity {
    private TextView flag;
    private EditText telephone;
    private EditText message;
    private Button send;
    private boolean isSend = false;
    private SmsManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);

        manager = SmsManager.getDefault();
        flag = (TextView) findViewById(R.id.flag);
        telephone = (EditText) findViewById(R.id.telephone);
        Drawable background = telephone.getBackground();
        telephone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                Toast.makeText(MsgActivity.this,"" + editable.toString(),Toast.LENGTH_SHORT).show();
                if (("").equals(editable.toString()) || null == editable.toString()) {
                    flag.setVisibility(View.GONE);
                }
                if (PhoneNumberUtils.isGlobalPhoneNumber(editable.toString())) {
                    flag.setText("√");
                    flag.setTextColor(getResources().getColor(android.R.color.holo_green_light));
                    flag.setVisibility(View.VISIBLE);
                    isSend = true;
                } else {
                    flag.setText("×");
                    flag.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                    flag.setVisibility(View.VISIBLE);
                    isSend = false;
                }
            }
        });
        telephone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    telephone.setBackground(getDrawable(R.drawable.background_edit));
                } else {
                    telephone.setBackground(background);
                }
            }
        });

        message = (EditText) findViewById(R.id.message);
        message.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    message.setBackground(getDrawable(R.drawable.background_edit));
                } else {
                    message.setBackground(background);
                }
            }
        });

        Intent sendIntent = new Intent("SENT_SMS_ACTION");
        PendingIntent sendPendingIntent = PendingIntent.getBroadcast(this, 0, sendIntent, 0);
        this.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(MsgActivity.this, "success", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SENT_SMS_ACTION"));

        Intent deliverIntent = new Intent("DELIVERED_SMS_ACTION");
        PendingIntent deliverPendingIntent = PendingIntent.getBroadcast(this, 0, deliverIntent, 0);
        this.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(MsgActivity.this, "get", Toast.LENGTH_SHORT).show();
            }
        }, new IntentFilter("DELIVERED_SMS_ACTION"));

        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSend) {
                    manager.sendTextMessage(telephone.getText().toString(), null, message.getText().toString(), sendPendingIntent, deliverPendingIntent);
                    message.setText("");
                } else {
                    Toast.makeText(MsgActivity.this, "I'm sorry,message is null", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
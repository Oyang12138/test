package com.example.window;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class AudioActivity extends AppCompatActivity {
    private Button mAlarm;
    private Button mMusic;
    private Button mRing;
    private Button mSystem;
    private Button mDtmf;
    private Button mNotify;
    private Button mVoice;
    private Button mSetting;
    private Button[] mBtns;
    private AlertDialog dialog;
    private AudioManager manager;
    private SeekBar progress;
    private TextView num;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        init();
    }

    private void init() {
        mAlarm = (Button) findViewById(R.id.alarm);
        mMusic = (Button) findViewById(R.id.music);
        mRing = (Button) findViewById(R.id.ring);
        mSystem = (Button) findViewById(R.id.system);
        mDtmf = (Button) findViewById(R.id.dtmf);
        mNotify = (Button) findViewById(R.id.notify);
        mVoice = (Button) findViewById(R.id.voice);

        mBtns = new Button[]{mAlarm, mMusic, mRing, mSystem, mDtmf, mNotify, mVoice};
        mRing.setEnabled(false);
        mSystem.setEnabled(false);
        mDtmf.setEnabled(false);
        mNotify.setEnabled(false);
        setOnClickListener();

        context = this;
        manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mSetting = (Button) findViewById(R.id.setting);
        mSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                String ringerMode = "";
                switch (manager.getRingerMode()) {
                    case AudioManager.RINGER_MODE_SILENT:
                        ringerMode = "静音模式";
                        break;
                    case AudioManager.RINGER_MODE_NORMAL:
                        ringerMode = "普通模式";
                        break;
                    case AudioManager.RINGER_MODE_VIBRATE:
                        ringerMode = "震动模式";
                        break;
                }
                String mode = "";
                switch (manager.getMode()) {
                    case AudioManager.MODE_NORMAL:
                        mode = "普通模式";
                        break;
                    case AudioManager.MODE_RINGTONE:
                        mode = "响铃模式";
                        break;
                    case AudioManager.MODE_IN_CALL:
                        mode = "电话模式";
                        break;
                    case AudioManager.MODE_IN_COMMUNICATION:
                        mode = "通话模式";
                        break;
                }
                Toast.makeText(AudioActivity.this, "当前为" + ringerMode + "," + mode, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setOnClickListener() {
        for (Button button : mBtns) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialog(button);
                    if (button == mAlarm) {
                        num.setText(String.valueOf(manager.getStreamVolume(AudioManager.STREAM_ALARM)));
                        progress.setProgress(manager.getStreamVolume(AudioManager.STREAM_ALARM));
                        progress.setMax(manager.getStreamMaxVolume(AudioManager.STREAM_ALARM));
                    } else if (button == mMusic) {
                        num.setText(String.valueOf(manager.getStreamVolume(AudioManager.STREAM_MUSIC)));
                        progress.setProgress(manager.getStreamVolume(AudioManager.STREAM_MUSIC));
                        progress.setMax(manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
                    } else if (button == mRing) {
                        num.setText(String.valueOf(manager.getStreamVolume(AudioManager.STREAM_RING)));
                        progress.setProgress(manager.getStreamVolume(AudioManager.STREAM_RING));
                        progress.setMax(manager.getStreamMaxVolume(AudioManager.STREAM_RING));
                    } else if (button == mSystem) {
                        num.setText(String.valueOf(manager.getStreamVolume(AudioManager.STREAM_SYSTEM)));
                        progress.setProgress(manager.getStreamVolume(AudioManager.STREAM_SYSTEM));
                        progress.setMax(manager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM));
                    } else if (button == mDtmf) {
                        num.setText(String.valueOf(manager.getStreamVolume(AudioManager.STREAM_DTMF)));
                        progress.setProgress(manager.getStreamVolume(AudioManager.STREAM_DTMF));
                        progress.setMax(manager.getStreamMaxVolume(AudioManager.STREAM_DTMF));
                    } else if (button == mNotify) {
                        num.setText(String.valueOf(manager.getStreamVolume(AudioManager.STREAM_NOTIFICATION)));
                        progress.setProgress(manager.getStreamVolume(AudioManager.STREAM_NOTIFICATION));
                        progress.setMax(manager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION));
                    } else if (button == mVoice) {
                        num.setText(String.valueOf(manager.getStreamVolume(AudioManager.STREAM_VOICE_CALL)));
                        progress.setProgress(manager.getStreamVolume(AudioManager.STREAM_VOICE_CALL));
                        progress.setMax(manager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL));
                    }
                }
            });
        }
    }

    public void showDialog(Button button) {
        View v = LayoutInflater.from(context).inflate(R.layout.dialog, null, false);
        TextView title = (TextView) v.findViewById(R.id.title);
        title.setText(button.getText().toString());
        num = (TextView) v.findViewById(R.id.num);
        progress = (SeekBar) v.findViewById(R.id.progress);
        progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                num.setText(String.valueOf(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Button confirm = (Button) v.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button == mAlarm) {
                    manager.setStreamVolume(AudioManager.STREAM_ALARM, progress.getProgress(), AudioManager.FLAG_SHOW_UI);
                } else if (button == mMusic) {
                    manager.setStreamVolume(AudioManager.STREAM_MUSIC, progress.getProgress(), AudioManager.FLAG_SHOW_UI);
                } else if (button == mRing) {
                    manager.setStreamVolume(AudioManager.STREAM_RING, progress.getProgress(), AudioManager.FLAG_SHOW_UI);
                } else if (button == mSystem) {
                    manager.setStreamVolume(AudioManager.STREAM_SYSTEM, progress.getProgress(), AudioManager.FLAG_SHOW_UI);
                } else if (button == mDtmf) {
                    manager.setStreamVolume(AudioManager.STREAM_DTMF, progress.getProgress(), AudioManager.FLAG_SHOW_UI);
                } else if (button == mNotify) {
                    manager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, progress.getProgress(), AudioManager.FLAG_SHOW_UI);
                } else if (button == mVoice) {
                    manager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, progress.getProgress(), AudioManager.FLAG_SHOW_UI);
                }
                dialog.dismiss();
            }
        });
        dialog = new AlertDialog.Builder(AudioActivity.this).setView(v).setCancelable(true).create();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = 200;
        params.height = 100;
        dialog.getWindow().setAttributes(params);
        dialog.show();
    }

    public void setDialog(TextView view, SeekBar bar) {
        for (int i = 0; mBtns.length > i; i++) {
            switch (i) {
                case AudioManager.STREAM_ALARM:

                    break;
                case AudioManager.STREAM_MUSIC:
                    view.setText(String.valueOf(manager.getStreamVolume(AudioManager.STREAM_MUSIC)));
                    bar.setProgress(manager.getStreamVolume(AudioManager.STREAM_MUSIC));
                    bar.setMax(manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
                    break;
                case AudioManager.STREAM_RING:
                    view.setText(String.valueOf(manager.getStreamVolume(AudioManager.STREAM_RING)));
                    bar.setProgress(manager.getStreamVolume(AudioManager.STREAM_RING));
                    bar.setMax(manager.getStreamMaxVolume(AudioManager.STREAM_RING));
                    break;
                case AudioManager.STREAM_SYSTEM:
                    view.setText(String.valueOf(manager.getStreamVolume(AudioManager.STREAM_SYSTEM)));
                    bar.setProgress(manager.getStreamVolume(AudioManager.STREAM_SYSTEM));
                    bar.setMax(manager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM));
                    break;
                case AudioManager.STREAM_DTMF:
                    view.setText(String.valueOf(manager.getStreamVolume(AudioManager.STREAM_DTMF)));
                    bar.setProgress(manager.getStreamVolume(AudioManager.STREAM_DTMF));
                    bar.setMax(manager.getStreamMaxVolume(AudioManager.STREAM_DTMF));
                    break;
                case AudioManager.STREAM_NOTIFICATION:
                    view.setText(String.valueOf(manager.getStreamVolume(AudioManager.STREAM_NOTIFICATION)));
                    bar.setProgress(manager.getStreamVolume(AudioManager.STREAM_NOTIFICATION));
                    bar.setMax(manager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION));
                    break;
                case AudioManager.STREAM_VOICE_CALL:
                    view.setText(String.valueOf(manager.getStreamVolume(AudioManager.STREAM_VOICE_CALL)));
                    bar.setProgress(manager.getStreamVolume(AudioManager.STREAM_VOICE_CALL));
                    bar.setMax(manager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL));
                    break;
            }
        }
    }
}
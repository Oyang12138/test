package com.example.dialogbar;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.Equalizer;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {
    private SeekBar seekBar;
    private MediaPlayer mMediaPlayer;
    private Equalizer mEqualizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource("/storage/emulated/0/qqmusic/song/北京室内男声合唱团 - 亮剑-中国军魂 [mqms2].mp3");
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setEq();
    }

    private void setEq() {
        mEqualizer = new Equalizer(0, mMediaPlayer.getAudioSessionId());
        mEqualizer.setEnabled(true);

        short bands = mEqualizer.getNumberOfBands();

        final short minEqualizer = mEqualizer.getBandLevelRange()[0];
        final short maxEqualizer = mEqualizer.getBandLevelRange()[1];

        for (int i = 0; i < bands; i++) {
            final short band = (short) i;
            System.out.println(mEqualizer.getBandLevel(band));
            System.out.println(i);

            TextView title = findViewById(R.id.title);
            title.setText((mEqualizer.getCenterFreq(band) / 1000) + "HZ");
            Log.e("abc", "setEq: " + title.getText());

            TextView min = findViewById(R.id.tv1);
            min.setText((minEqualizer / 100) + " dB");
            TextView max = findViewById(R.id.tv2);
            max.setText((maxEqualizer / 100) + " dB");

            SeekBar seekbar = findViewById(R.id.seekBar);
            seekbar.setMax(maxEqualizer - minEqualizer);
            seekbar.setProgress(mEqualizer.getBandLevel(band));
            seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    mEqualizer.setBandLevel(band,
                            (short) (progress + minEqualizer));
                    Log.e("abc", "onProgressChanged: " + mEqualizer.getBandLevel(band));
                }
            });
        }
    }
}
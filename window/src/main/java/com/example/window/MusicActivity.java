package com.example.window;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.media.MediaPlayer;
import android.media.audiofx.Equalizer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicActivity extends AppCompatActivity {
    private static final String MUSIC_PATH = "/storage/emulated/0/qqmusic/song/";
    private List<File> mFileList;
    private ListView mContent;
    private MediaPlayer mPlayer;
    private Equalizer mEqualizer;
    private AlertDialog mDialog;
    private TextView close;
    private TextView closeBtn;
    private TextView intelBtn;
    private TextView customBtn;
    private TextView classicalBtn;
    private TextView popularBtn;
    private TextView voiceBtn;
    private TextView jazzBtn;
    private TextView rockBtn;
    private TextView firstBar;
    private TextView secondBar;
    private TextView thirdBar;
    private TextView forthBar;
    private TextView fifthBar;
    private TextView reset;
    private LinearLayout background;
    private VerticalBar mFirstVerticalBar;
    private VerticalBar mSecondVerticalBar;
    private VerticalBar mThirdVerticalBar;
    private VerticalBar mForthVerticalBar;
    private VerticalBar mFifthVerticalBar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.eq, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if ("均衡器设置".equals(item.getTitle())) {
            View view = LayoutInflater.from(this).inflate(getResources().getLayout(R.layout.eq_dialog_content), null, false);
            mDialog = new AlertDialog.Builder(MusicActivity.this).setView(view).setCancelable(true).create();

            background = view.findViewById(R.id.ll_rg);
            reset = view.findViewById(R.id.reset);
            firstBar = view.findViewById(R.id.first_bar);
            secondBar = view.findViewById(R.id.second_bar);
            thirdBar = view.findViewById(R.id.third_bar);
            forthBar = view.findViewById(R.id.forth_bar);
            fifthBar = view.findViewById(R.id.fifth_bar);

            mEqualizer = new Equalizer(0,mPlayer.getAudioSessionId());
            mEqualizer.setEnabled(true);

            short band = mEqualizer.getNumberOfBands();
            final short minEq = mEqualizer.getBandLevelRange()[0];
            final short maxEq = mEqualizer.getBandLevelRange()[1];

            mFirstVerticalBar = view.findViewById(R.id.vertical_first);
            mFirstVerticalBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    firstBar.setText(String.valueOf(progress) + "dB");
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            mSecondVerticalBar = view.findViewById(R.id.vertical_second);
            mSecondVerticalBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    secondBar.setText(String.valueOf(progress) + "dB");
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            mThirdVerticalBar = view.findViewById(R.id.vertical_third);
            mThirdVerticalBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    thirdBar.setText(String.valueOf(progress) + "dB");
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            mForthVerticalBar = view.findViewById(R.id.vertical_forth);
            mForthVerticalBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    forthBar.setText(String.valueOf(progress) + "dB");
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            mFifthVerticalBar = view.findViewById(R.id.vertical_fifth);
            mFifthVerticalBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    fifthBar.setText(String.valueOf(progress) + "dB");
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            close = view.findViewById(R.id.close);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDialog != null) {
                        mDialog.dismiss();
                    }
                }
            });
            closeBtn = view.findViewById(R.id.close_btn);
            closeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setBg(closeBtn);
                    setValue(1);
                }
            });
            intelBtn = view.findViewById(R.id.intelligence_btn);
            intelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setBg(intelBtn);
                    setValue(2);
                }
            });
            customBtn = view.findViewById(R.id.custom_btn);
            customBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setBg(customBtn);
                    setValue(3);
                }
            });
            classicalBtn = view.findViewById(R.id.classical_btn);
            classicalBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setBg(classicalBtn);
                    setValue(4);
                }
            });
            popularBtn = view.findViewById(R.id.popular_btn);
            popularBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setBg(popularBtn);
                    setValue(5);
                }
            });
            voiceBtn = view.findViewById(R.id.voice_btn);
            voiceBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setBg(voiceBtn);
                    setValue(6);
                }
            });
            jazzBtn = view.findViewById(R.id.jazz_btn);
            jazzBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setBg(jazzBtn);
                    setValue(7);
                }
            });
            rockBtn = view.findViewById(R.id.rock_btn);
            rockBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setBg(rockBtn);
                    setValue(8);
                }
            });
            WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();
            params.height = 900;
            params.width = 920;
            mDialog.getWindow().setAttributes(params);
            mDialog.show();
            classicalBtn.performClick();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        mFileList = new ArrayList<>();
        getMusic(MUSIC_PATH);
        MusicAdapter adapter = new MusicAdapter(this);
        mContent = findViewById(R.id.content);
        mContent.setAdapter(adapter);
    }

    public void getMusic(String path) {
        File file = new File(path);
        if (file == null || !file.exists() || !file.isDirectory()) {
            return;
        }
        File[] files = file.listFiles();
        System.out.println(files.length);
        for (File f : files) {
            System.out.println(f);
            if (f.isFile() && f.getName().contains(".mp3")) {
                mFileList.add(f);
                continue;
            }
            if (f.isDirectory()) {
                getMusic(f.getPath());
            }
        }
    }

    public void setBg(TextView btn) {
        closeBtn.setBackgroundResource(R.drawable.bg_btn);
        intelBtn.setBackgroundResource(R.drawable.bg_btn);
        customBtn.setBackgroundResource(R.drawable.bg_btn);
        classicalBtn.setBackgroundResource(R.drawable.bg_btn);
        popularBtn.setBackgroundResource(R.drawable.bg_btn);
        voiceBtn.setBackgroundResource(R.drawable.bg_btn);
        jazzBtn.setBackgroundResource(R.drawable.bg_btn);
        rockBtn.setBackgroundResource(R.drawable.bg_btn);
        btn.setBackgroundResource(R.color.click);
    }

    public void setValue(int flag) {
        switch (flag) {
            case 1:

            case 2:
                background.setVisibility(View.INVISIBLE);
                reset.setVisibility(View.INVISIBLE);
                break;

            case 3:
                background.setVisibility(View.VISIBLE);
                reset.setVisibility(View.VISIBLE);
                firstBar.setText("0dB");
                secondBar.setText("0dB");
                thirdBar.setText("0dB");
                forthBar.setText("0dB");
                fifthBar.setText("0dB");
                mFirstVerticalBar.setProgress(0);
                mSecondVerticalBar.setProgress(0);
                mThirdVerticalBar.setProgress(0);
                mForthVerticalBar.setProgress(0);
                mFifthVerticalBar.setProgress(0);
                break;

            case 4:
                background.setVisibility(View.VISIBLE);
                reset.setVisibility(View.INVISIBLE);
                firstBar.setText("2dB");
                secondBar.setText("2dB");
                thirdBar.setText("0dB");
                forthBar.setText("3dB");
                fifthBar.setText("4dB");
                mFirstVerticalBar.setProgress(2);
                mSecondVerticalBar.setProgress(2);
                mThirdVerticalBar.setProgress(0);
                mForthVerticalBar.setProgress(3);
                mFifthVerticalBar.setProgress(4);
                break;

            case 5:
                background.setVisibility(View.VISIBLE);
                reset.setVisibility(View.INVISIBLE);
                firstBar.setText("4dB");
                secondBar.setText("-2dB");
                thirdBar.setText("4dB");
                forthBar.setText("2dB");
                fifthBar.setText("0dB");
                mFirstVerticalBar.setProgress(4);
                mSecondVerticalBar.setProgress(-2);
                mThirdVerticalBar.setProgress(4);
                mForthVerticalBar.setProgress(2);
                mFifthVerticalBar.setProgress(0);
                break;

            case 6:
                background.setVisibility(View.VISIBLE);
                reset.setVisibility(View.INVISIBLE);
                firstBar.setText("-4dB");
                secondBar.setText("2dB");
                thirdBar.setText("2dB");
                forthBar.setText("2dB");
                fifthBar.setText("-4dB");
                mFirstVerticalBar.setProgress(-4);
                mSecondVerticalBar.setProgress(2);
                mThirdVerticalBar.setProgress(2);
                mForthVerticalBar.setProgress(2);
                mFifthVerticalBar.setProgress(-4);
                break;

            case 7:
                background.setVisibility(View.VISIBLE);
                reset.setVisibility(View.INVISIBLE);
                firstBar.setText("0dB");
                secondBar.setText("0dB");
                thirdBar.setText("4dB");
                forthBar.setText("4dB");
                fifthBar.setText("0dB");
                mFirstVerticalBar.setProgress(0);
                mSecondVerticalBar.setProgress(0);
                mThirdVerticalBar.setProgress(4);
                mForthVerticalBar.setProgress(4);
                mFifthVerticalBar.setProgress(0);
                break;

            case 8:
                background.setVisibility(View.VISIBLE);
                reset.setVisibility(View.INVISIBLE);
                firstBar.setText("6dB");
                secondBar.setText("0dB");
                thirdBar.setText("2dB");
                forthBar.setText("0dB");
                fifthBar.setText("6dB");
                mFirstVerticalBar.setProgress(6);
                mSecondVerticalBar.setProgress(0);
                mThirdVerticalBar.setProgress(2);
                mForthVerticalBar.setProgress(0);
                mFifthVerticalBar.setProgress(6);
                break;
        }
    }

    public String getName(String name, int i) {
        String[] strings = name.split("-");
        return strings[i].contains(" [mqms2]") ? strings[i].split(" [mqms2]")[0] : strings[i];
    }

    public class MusicAdapter extends BaseAdapter {
        private Context mContext;
        private LayoutInflater mInflater;

        public MusicAdapter(Context mContext) {
            this.mContext = mContext;
            this.mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return mFileList.size();
        }

        @Override
        public Object getItem(int position) {
            return mFileList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.music_item, null);
            }
            TextView musicName = convertView.findViewById(R.id.music_name);
            musicName.setText(getName(mFileList.get(position).getName(), 1).length() > 10 ? getName(mFileList.get(position).getName(), 1).split(" ")[1] : getName(mFileList.get(position).getName(), 1));
            System.out.println(musicName.getText().toString());
            TextView singerName = convertView.findViewById(R.id.singer_name);
            singerName.setText(getName(mFileList.get(position).getName(), 0));
            ImageButton start = convertView.findViewById(R.id.start);
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPlayer != null) {
                        mPlayer.stop();
                        mPlayer.release();
                    }
                    mPlayer = new MediaPlayer();
                    try {
                        mPlayer.setDataSource(mFileList.get(position).getPath());
                        mPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mPlayer.start();
                }
            });
            return convertView;
        }
    }
}
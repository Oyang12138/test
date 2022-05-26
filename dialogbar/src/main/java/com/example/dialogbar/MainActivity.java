package com.example.dialogbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ImageView panda;
    private SeekBar seekBar;
    private static int process;
    private ProgressBar progressBar;
    private TextView start;
    private TextView end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        panda = findViewById(R.id.panda);
        panda.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_content, null, false);
                AlertDialog alert = new AlertDialog.Builder(MainActivity.this).setView(v).setCancelable(true).create();
                WindowManager.LayoutParams layoutParams = alert.getWindow().getAttributes();
                layoutParams.height = 200;
                layoutParams.width = 100;
                alert.getWindow().setAttributes(layoutParams);
                alert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                alert.show();
                progressBar = v.findViewById(R.id.progress);
                start = v.findViewById(R.id.start);
                end = v.findViewById(R.id.end);
                seekBar = v.findViewById(R.id.seekBar);
//                seekBar.setMax(100);
//                seekBar.setMin(0);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                        process = progress;
                        progressBar.setProgress(process);
                        TextView value = v.findViewById(R.id.value);
                        value.setText(String.valueOf(progress));
                        value.setPadding((int)( progress * 6),0,0,0);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        Toast.makeText(MainActivity.this, "start", Toast.LENGTH_SHORT);
                        start.setVisibility(View.INVISIBLE);
                        end.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        Toast.makeText(MainActivity.this, "stop", Toast.LENGTH_SHORT);
                        start.setVisibility(View.VISIBLE);
                        end.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }
}
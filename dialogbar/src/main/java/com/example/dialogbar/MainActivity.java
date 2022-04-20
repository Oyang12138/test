package com.example.dialogbar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView panda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        panda = findViewById(R.id.panda);
        panda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_content,null,false);
                AlertDialog alert = new AlertDialog.Builder(MainActivity.this).setView(v).setCancelable(true).create();
                WindowManager.LayoutParams layoutParams = alert.getWindow().getAttributes();
                layoutParams.height = 300;
                layoutParams.width = 200;
                alert.getWindow().setAttributes(layoutParams);
                alert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                alert.show();
            }
        });
    }
}
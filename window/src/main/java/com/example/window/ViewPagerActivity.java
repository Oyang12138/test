package com.example.window;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewPagerActivity extends AppCompatActivity {
    private TextView recycler;
    private TextView recycler2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        recycler = findViewById(R.id.recycler);
        recycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewPagerActivity.this, HorizontalActivity.class);
                startActivity(intent);
            }
        });
        recycler2 = findViewById(R.id.recycler2);
        recycler2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewPagerActivity.this, HorizontalActivity.class);
                intent.putExtra("vertical", 0);
                startActivity(intent);
            }
        });
    }
}
package com.example.myadapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRv = findViewById(R.id.rv_1);

        mRv.setLayoutManager(new LinearLayoutManager(this));

        mRv.setAdapter(new PlayAdapter(MainActivity.this, new PlayAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                Toast.makeText(MainActivity.this,"click-->" + pos , Toast.LENGTH_SHORT).show();
            }
        }));
    }
}
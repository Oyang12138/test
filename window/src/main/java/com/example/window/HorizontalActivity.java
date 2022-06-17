package com.example.window;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

public class HorizontalActivity extends AppCompatActivity {
    private ViewPager2 pager2;
    private RecyclerView.Recycler recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal);

//        recycler = new RecyclerView.Recycler();
        pager2 = findViewById(R.id.pager2);
        HorizontalAdapter adapter = new HorizontalAdapter(this);
        pager2.setAdapter(adapter);

        Intent intent = getIntent();
        int vertical = intent.getIntExtra("vertical", -1);
        if (0 == vertical) {
            pager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        }
        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }
}
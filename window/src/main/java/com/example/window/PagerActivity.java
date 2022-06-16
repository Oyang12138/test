package com.example.window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

public class PagerActivity extends AppCompatActivity {
    private ViewPager pager;
    private ArrayList<View> list;
    private MyPageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        pager = (ViewPager) findViewById(R.id.pager);
        list = new ArrayList<>();
        LayoutInflater inflater = getLayoutInflater();
        list.add(inflater.inflate(R.layout.page_view_1, null, false));
        list.add(inflater.inflate(R.layout.page_view_2, null, false));
        list.add(inflater.inflate(R.layout.page_view_3, null, false));
        adapter = new MyPageAdapter(list);
        pager.setAdapter(adapter);
    }
}
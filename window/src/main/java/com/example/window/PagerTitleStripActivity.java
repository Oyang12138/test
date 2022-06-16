package com.example.window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

public class PagerTitleStripActivity extends AppCompatActivity {
    private ViewPager pager;
    private ArrayList<View> views;
    private ArrayList<String> strings;
    private MySecondPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_title_strip);

        pager = (ViewPager) findViewById(R.id.pager_two);
        views = new ArrayList<>();
        LayoutInflater inflater = getLayoutInflater();
        views.add(inflater.inflate(R.layout.page_view_1, null, false));
        views.add(inflater.inflate(R.layout.page_view_2, null, false));
        views.add(inflater.inflate(R.layout.page_view_3, null, false));
        strings = new ArrayList<>();
        strings.add("萨摩");
        strings.add("寻回");
        strings.add("边牧");
        adapter = new MySecondPagerAdapter(views, strings);
        pager.setAdapter(adapter);
    }
}
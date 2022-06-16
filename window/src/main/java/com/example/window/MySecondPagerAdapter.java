package com.example.window;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class MySecondPagerAdapter extends PagerAdapter {
    private ArrayList<View> views;
    private ArrayList<String> strings;

    public MySecondPagerAdapter(ArrayList<View> views, ArrayList<String> strings) {
        this.views = views;
        this.strings = strings;
    }

    public MySecondPagerAdapter() {
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return strings.get(position);
    }
}

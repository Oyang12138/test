package com.example.window;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class RightFragment extends Fragment implements View.OnClickListener {
    private DrawerLayout drawerLayout;
    private FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.drawer_layout, container, false);
        view.findViewById(R.id.main).setOnClickListener(this);
        view.findViewById(R.id.btn1).setOnClickListener(this);
        view.findViewById(R.id.btn2).setOnClickListener(this);
        view.findViewById(R.id.btn3).setOnClickListener(this);
        fragmentManager = getActivity().getSupportFragmentManager();
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main:
                ContentFragment fragment = new ContentFragment("主页", R.color.white);
                fragmentManager.beginTransaction().replace(R.id.fly_content, fragment).commit();
                drawerLayout.closeDrawer(Gravity.END);
                break;
            case R.id.btn1:
                ContentFragment fragment1 = new ContentFragment("页面一", android.R.color.holo_blue_bright);
                fragmentManager.beginTransaction().replace(R.id.fly_content, fragment1).commit();
                drawerLayout.closeDrawer(Gravity.END);
                break;
            case R.id.btn2:
                ContentFragment fragment2 = new ContentFragment("页面二", R.color.purple_200);
                fragmentManager.beginTransaction().replace(R.id.fly_content, fragment2).commit();
                drawerLayout.closeDrawer(Gravity.END);
                break;
            case R.id.btn3:
                ContentFragment fragment3 = new ContentFragment("页面三", R.color.teal_700);
                fragmentManager.beginTransaction().replace(R.id.fly_content, fragment3).commit();
                drawerLayout.closeDrawer(Gravity.END);
                break;
        }
    }

    public void setDrawerLayout(DrawerLayout layout) {
        this.drawerLayout = layout;
    }
}

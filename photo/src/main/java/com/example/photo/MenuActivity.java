package com.example.photo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tab_photo;
    private TextView tab_image;

    private FragmentPhoto fragmentPhoto;
    private FragmentImage fragmentImage;
    private FragmentManager fragmentManager;
    private FrameLayout frameLayout;

    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        time = intent.getStringExtra("time");

        fragmentManager = getFragmentManager();

        tab_photo = findViewById(R.id.tab_photo);
        tab_image = findViewById(R.id.tab_image);
        frameLayout = findViewById(R.id.fram_content);

        tab_photo.setOnClickListener(this);
        tab_image.setOnClickListener(this);

        tab_photo.performClick();
    }

    //重置所有文本的选中状态
    private void setSelected(){
        tab_photo.setSelected(false);
        tab_image.setSelected(false);
        tab_photo.setTextColor(Color.BLACK);
        tab_image.setTextColor(Color.BLACK);
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (fragmentImage != null)
            fragmentTransaction.hide(fragmentImage);
        if (fragmentPhoto != null)
            fragmentTransaction.hide(fragmentPhoto);
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fragmentTransaction);
        switch (view.getId()){
            case R.id.tab_photo:
                setSelected();
                tab_photo.setSelected(true);
                tab_photo.setTextColor(Color.BLUE);
                if (fragmentPhoto == null){
                    fragmentPhoto = new FragmentPhoto();
                    fragmentTransaction.add(R.id.fram_content,fragmentPhoto);
                }else {
                    fragmentTransaction.show(fragmentPhoto);
                }
                break;
            case R.id.tab_image:
                setSelected();
                tab_image.setSelected(true);
                tab_image.setTextColor(Color.BLUE);
                if (fragmentImage == null){
                    fragmentImage = new FragmentImage();
                    fragmentTransaction.add(R.id.fram_content,fragmentImage);
                }else {
                    fragmentTransaction.show(fragmentImage);
                }
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
    }
}
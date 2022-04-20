package com.example.ivi;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private AnimationDrawable anim;
    private ImageView img_show;
    private View testInclude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testinclude);

        testInclude = findViewById(R.id.testInclude);

        img_show = testInclude.findViewById(R.id.img_show);
        anim = (AnimationDrawable) img_show.getBackground();
        anim.start();
    }
}
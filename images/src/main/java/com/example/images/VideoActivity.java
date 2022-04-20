package com.example.images;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class VideoActivity extends AppCompatActivity {
    private ImageView video;
    private ImageView player;
    private RightSlideFinishView layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        video = findViewById(R.id.video);
        player = findViewById(R.id.player);

        Intent intent = getIntent();
        String path = intent.getStringExtra("path");
        int pos = intent.getIntExtra("pos",0);
        Log.i("pathttttt","p:" + path);
        ArrayList<Bitmap> bitmaps = GetFiles.getMovie(path);
        int index = bitmaps.size() - 1 - pos;
        Bitmap bitmap = bitmaps.get(index);

        video.setImageBitmap(bitmap);

        layout = new RightSlideFinishView(this);
        layout.attachToActivity(this);

        player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"我被点击了", Toast.LENGTH_SHORT).show();
                String uri = GetFiles.getPath(path).get(index);
                Intent intent1 = new Intent(VideoActivity.this,PlayerActivity.class);
                intent1.putExtra("uri",uri);
                finish();
                startActivity(intent1);
                overridePendingTransition(R.anim.bm_enter,R.anim.bm_exit);
            }
        });
    }
}
package com.example.images;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ImageActivity extends AppCompatActivity {
    private RecyclerView mRv;
    private ImageView mRe;
    private TextView mTe;
    private String path;
    protected RightSlideFinishView layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Intent intent = getIntent();
        ArrayList<Beans> list = new GetFiles(intent.getStringExtra("path")).getFiles();
        mRv = findViewById(R.id.rv_2);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        path = list.get(intent.getIntExtra("pos",0)).getPath();
        Log.i("logIn","i:" + GetFiles.getImage(path));
        if (GetFiles.getImage(path).size() != 0){
            mRv.setAdapter(new ImageAdapter(path,ImageActivity.this, GetFiles.getImage(path), new ImageAdapter.OnItemClickListener() {
                @Override
                public void onClick(int pos) {
                    Intent intent = new Intent(ImageActivity.this,PictureActivity.class);
                    intent.putExtra("path",path);
                    intent.putExtra("pos",pos);
                    startActivity(intent);
                }
            }));
        }else{
            Log.i("GetFiles.getMovie(path)","aB<> -> " + GetFiles.getMovie(path));
            mRv.setAdapter(new MovieAdapter(path,ImageActivity.this, GetFiles.getMovie(path), new MovieAdapter.OnItemClickListener() {
                @Override
                public void onClick(int pos) {
                    Intent intent = new Intent(ImageActivity.this,VideoActivity.class);
                    intent.putExtra("path",path);
                    intent.putExtra("pos",pos);
                    startActivity(intent);
                }
            }));
        }


        mRe = findViewById(R.id.re_1);
        mRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("tag_re","111");
                finish();
            }
        });

        mTe = findViewById(R.id.te_1);
        String[] s = path.split("/");
        char[] cs = new char[s[s.length - 1].length()];
        for (int i = 0 ; i < s[s.length - 1].length() ; i ++){
            char c = s[s.length - 1].charAt(i);
            cs[i] = c;
        }
        mTe.setText(cs,0,cs.length);

        layout = new RightSlideFinishView(this);
        layout.attachToActivity(this);
    }
}
package com.example.images;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PictureActivity extends AppCompatActivity {
    private ZoomImageView picture;
    private TextView countTitle;
    private ImageView anti_clockwise;
    private ImageView clockwise;
    protected RightSlideFinishView layout;
    private int count = 0;

    private ImageView mBtnUp;
    private ImageView mBtnDown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        Intent intent = getIntent();
        String path = intent.getStringExtra("path");
        count = intent.getIntExtra("pos",0);
        ArrayList<Beans> beans = GetFiles.getImage(path);
        ArrayList<Beans> bean = new ArrayList<>();
        for(int i = 1 ; i <= beans.size() ; i ++){
            bean.add(beans.get(beans.size() - i));
        }
        String filePath = bean.get(count).getFile().getPath();
        Log.i("filePath","filePath" + filePath);

        countTitle = findViewById(R.id.countTitle);
        String sum = String.valueOf(bean.size());
        String now = String.valueOf(count + 1);
        char[] chars = new char[sum.length() + now.length() + 1];
        for (int i = 0 ; i < chars.length ; i ++){
            for (int j = 0 ; j < now.length() ; j ++){
                chars[j] = now.charAt(j);
            }
            chars[now.length()] = '/';
            for (int j = 1 ; j <= sum.length() ; j ++){
                chars[chars.length - j] = sum.charAt(sum.length() - j);
            }
        }
        countTitle.setText(chars,0,chars.length);

        picture = findViewById(R.id.picture);
        Bitmap bm = BitmapFactory.decodeFile(filePath);
        picture.setImageBitmap(bm);

        mBtnUp = findViewById(R.id.dragUp);
        mBtnDown = findViewById(R.id.dragDown);

        mBtnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count > 0){
//                    picture.setImageBitmap(BitmapFactory.decodeFile(bean.get(-- count).getFile().getPath()));
                    Log.i("countTag","count:" + count);
                    Intent intent1 = new Intent(PictureActivity.this,PictureActivity.class);
                    intent1.putExtra("path",path);
                    intent1.putExtra("pos",count - 1);
                    finish();
                    startActivity(intent1);
                    overridePendingTransition(R.anim.enter_left,R.anim.exit_left);
                }else {
                    Toast.makeText(getApplicationContext(),"没有更多了",Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBtnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count + 1 < bean.size()){
//                    picture.setImageBitmap(BitmapFactory.decodeFile(bean.get(++ count).getFile().getPath()));
                    Log.i("countTag","count:" + count);
                    Intent intent2 = new Intent(PictureActivity.this,PictureActivity.class);
                    intent2.putExtra("path",path);
                    intent2.putExtra("pos",count + 1);
                    finish();
                    startActivity(intent2);
                    overridePendingTransition(R.anim.enter_right,R.anim.exit_right);
                }else {
                    Toast.makeText(getApplicationContext(),"没有更多了",Toast.LENGTH_SHORT).show();
                }
            }
        });

        layout = new RightSlideFinishView(this);
        layout.attachToActivity(this);

        anti_clockwise = findViewById(R.id.anti_clockwise);
        clockwise = findViewById(R.id.clockwise);

        anti_clockwise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"我被点击了",Toast.LENGTH_SHORT).show();
                picture.setImageBitmap(picture.bitmapRotation(90));
            }
        });

        clockwise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"我被点击了",Toast.LENGTH_SHORT).show();
                picture.setImageBitmap(picture.bitmapRotation(-90));
            }
        });
    }

}
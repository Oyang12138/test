package com.example.photo;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FragmentPhoto extends Fragment {
    private ListView listView;
    private MyAdapter myAdapter;
    private LinearLayout linear;
    private Button btn1, btn2, btn3, btn;
    //    private TextView tv;
    private int dx, dy, l, r, t, b;
    private int lastX, lastY;
    private int i;
    private Button[] mBtns = new Button[3];

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
//        linear = view.findViewById(R.id.linear);
        btn1 = view.findViewById(R.id.btn1);
        btn2 = view.findViewById(R.id.btn2);
        btn3 = view.findViewById(R.id.btn3);
        btn = view.findViewById(R.id.btn);
        mBtns[0] = btn1;
        mBtns[1] = btn2;
        mBtns[2] = btn3;
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBtn(0);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBtn(1);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBtn(2);
            }
        });
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = (int) motionEvent.getRawX();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        dx = (int) (motionEvent.getRawX() - lastX);
                        l = view.getLeft() + dx;
                        r = view.getRight() + dx;
                        t = view.getTop();
                        b = view.getBottom();
                        if (l <= 0) {
                            l = 0;
                            r = l + view.getWidth();
                        } else if (r >= 1074) {
                            r = 1074;
                            l = r - view.getWidth();
                        }
                        lastX = (int) motionEvent.getRawX();
                        mBtns[i].setBackgroundResource(android.R.color.darker_gray);
                        view.layout(l, t, r, b);
                        view.postInvalidate();
                        Log.e("abc", "onTouch: " + btn1.getWidth());
                        Log.e("abc", "onTouch: " + btn.getWidth());//374 / 2 = 187
                        break;

                    case MotionEvent.ACTION_CANCEL:

                    case MotionEvent.ACTION_UP:
                        Log.e("abc", "onTouch: " + l);//374 / 2 = 187
                        if (l <= 179) {
                            setBtn(0);
                        } else if (r >= 179 * 5) {
                            setBtn(2);
                        } else {
                            setBtn(1);
                        }
                        break;

                    default:
                        break;
                }
                return true;
            }
        });
        return view;
    }

    public void setBtn(int i){
        this.i = i;
        setBg(mBtns[i]);
        btn.layout(mBtns[i].getLeft(), mBtns[i].getTop(), mBtns[i].getRight(), mBtns[i].getBottom());
        btn.setText(mBtns[i].getText());
    }
    public void setBg(Button button){
        for (Button btn : mBtns) {
            btn.setBackgroundResource(btn == button ? R.color.teal_200 : android.R.color.darker_gray);
        }
    }
}

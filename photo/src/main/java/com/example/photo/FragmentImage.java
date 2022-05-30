package com.example.photo;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class FragmentImage extends Fragment {
    private Context context;
    private int progress = 0;
    private ProgressDialog dialog = null;
    private ProgressDialog dialog2 = null;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (progress <= 100) {
                dialog.setProgress(progress);
                progress += 5;
                handler.postDelayed(this, 1000);
            } else {
                dialog.dismiss();
                handler.removeCallbacksAndMessages(null);
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        TextView tv = view.findViewById(R.id.tv);
        TextView date = view.findViewById(R.id.date);
        TextView time = view.findViewById(R.id.time);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = LayoutInflater.from(context).inflate(R.layout.pop_window, null, false);
                Button btn1 = v.findViewById(R.id.show);
                Button btn2 = v.findViewById(R.id.gone);
                Button btn3 = v.findViewById(R.id.circle);
                PopupWindow popupWindow = new PopupWindow(v, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                popupWindow.setTouchable(true);
                popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return false;
                    }
                });
                popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
                popupWindow.showAsDropDown(view, 50, -250);

                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        progress = 0;
                        dialog = new ProgressDialog(context);
                        dialog.setMax(100);
                        dialog.setTitle("文件读取中");
                        dialog.setMessage("文件读取中，请稍后...");
                        dialog.setCancelable(false);
                        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        dialog.setIndeterminate(false);
                        dialog.show();
                        handler.post(runnable);
                    }
                });
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog2 = new ProgressDialog(context);
                        dialog2.setTitle("软件更新中");
                        dialog2.setMessage("软件更新中，请稍后...");
                        dialog2.setCancelable(true);
                        dialog2.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        dialog2.setIndeterminate(true);
                        dialog2.show();
                    }
                });
                btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ProgressDialog.show(context, "资源加载中", "资源加载中，请稍后...", false, true);
                    }
                });
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Toast.makeText(getActivity().getApplicationContext(), i + "." + (i1 + 1) + "." + i2, Toast.LENGTH_SHORT).show();
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        Toast.makeText(getActivity().getApplicationContext(), i + ":" + i1, Toast.LENGTH_SHORT).show();
                    }
                },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();
            }
        });
        return view;
    }
}

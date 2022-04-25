package com.example.asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 同步：当我们执行某个功能时，在没有得到结果之前，这个调用就不能返回！简单点就是说必须 等前一件事做完才能做下一件事
 * 异步：当我们执行某个功能后，我们并不需要立即得到结果，我们可以正常地做其他操作，这个功能可以在完成后通知或者回调来告诉我们
 */
public class MainActivity extends AppCompatActivity {
    private TextView title;
    private ProgressBar bar;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.title);
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if ("  更新完成".equals(title.getText().toString())){
                    button.setEnabled(true);
                }
            }
        });
        bar = findViewById(R.id.bar);
        button = findViewById(R.id.download);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyAsyncTask task = new MyAsyncTask(title, bar);
                task.execute(1000);
                button.setEnabled(false);
            }
        });
    }
}
package com.example.asynctask;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MyAsyncTask extends AsyncTask<Integer, Integer, String> {

    private TextView title;
    private ProgressBar bar;

    public MyAsyncTask(TextView title, ProgressBar bar) {
        super();
        this.title = title;
        this.bar = bar;
        AlertDialog.Builder builder = new AlertDialog.Builder(bar.getContext()).setTitle()
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        title.setText("  准备中");
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        bar.setProgress(values[0]);
        title.setText("  正在更新(" + values[0] + "%)...");
        if (values[0] == 100){
            title.setText("  更新完成");
        }
    }

    @Override
    protected String doInBackground(Integer... integers) {
        int i = 0;
        for (i = 10; i <= 100; i += 10){
            publishProgress(i);
            try {
                double b = Math.random() * 1000 + 100;
                Thread.sleep((long) b);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return i + integers[0].intValue() + "";
    }
}

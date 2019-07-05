package com.ceshi.ha.activity;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ceshi.ha.R;

import java.io.File;

/**
 * 进度条的写法
 */
public class NewActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    private MyHandler handler = new MyHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progressbar);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        String path = Environment.getExternalStorageDirectory() + File.separator;


        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                int progressBarMax = progressBar.getMax();
                try {
                    while (progressBarMax != progressBar.getProgress()) {
                        int stepProgress = progressBarMax / 10;
                        int currentprogress = progressBar.getProgress();
                        progressBar.setProgress(currentprogress + stepProgress);
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        thread.start();

        new Thread() {
            @Override
            public void run() {
                // 1.进度设置为10,线程每睡眠一秒发送一次进度
                for (int i = 0; i <= 10; i++) {
                    // 获得消息对象
                    Message ms = Message.obtain();
                    ms.arg1 = i;
                    handler.sendMessage(ms);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 取出进度
            int i = 0;
            i = msg.arg1;
            // 进行进度更新
            progressBar.setProgress(i);
            if (i == 10) {
                Toast.makeText(NewActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

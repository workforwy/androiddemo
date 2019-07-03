package com.ceshi.ha.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ceshi.ha.R;

/**
 * Created by WY on 2017/10/26 0026.
 */
public class ProgressBarStuActivity extends Activity {
    private int intstepProgress;
    private int intcurrentprogress;
    private int currentprogress;
    private int stepProgress;

    private ProgressBar progressBar = null;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 取出进度
            int i = 0;
            i = msg.arg1;
            // 进行进度更新
            progressBar.setProgress(i);
            if (i == 10) {
                Toast.makeText(ProgressBarStuActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progressbar1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int progressBarMax = progressBar.getMax();
                try {
                    while (progressBarMax != progressBar.getProgress()) {
                        intstepProgress = progressBarMax / 10;
                        intcurrentprogress = progressBar.getProgress();
                        progressBar.setProgress(currentprogress + stepProgress);
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
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
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}


package com.ceshi.ha.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.StringUtils;
import com.ceshi.ha.R;
import com.ceshi.ha.databinding.ActivityHandlerBinding;

/**
 * Created by WY on 2017/9/21 0021.
 */

public class HandlerActivity extends AppCompatActivity {

    private static int s = 1;
    private final String DATA = "data";
    private Thread thread;
    private int[] images = {
            R.mipmap.za1,
            R.mipmap.za2,
            R.mipmap.za3,
            R.mipmap.za4,
    };
    ActivityHandlerBinding binding;
    MyThread myThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHandlerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myThread = new MyThread();
        //子线程中不能有UI组件进行操作
        thread = new Thread(myThread);
        // 启动线程
        thread.start();

        binding.ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (StringUtils.isTrimEmpty(binding.ed1.getText().toString())) {
                    Toast.makeText(HandlerActivity.this, "请填写数字", Toast.LENGTH_SHORT).show();
                } else {
                    Message msg = new Message();
                    msg.what = 90;
                    Bundle bundle = new Bundle();
                    bundle.putInt(DATA, Integer.parseInt(binding.ed1.getText().toString()));
                    msg.setData(bundle);
                    myThread.myHandler.sendMessage(msg);
                }
            }
        });
        //在UI线程中操作组件
        binding.next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        for (int i = 0; i < images.length; i++) {
                            binding.image1.setImageResource(images[s++ % images.length]);
                            if (i == images.length - 1) {
                                break;
                            }
                        }

                    }
                }.start();

            }
        });
    }

    // 实现Runnable接口
    class MyThread implements Runnable {

        private Handler myHandler;

        // 线程体
        @Override
        public void run() {
            Looper.prepare();
            myHandler = new MyHandler();
            Looper.loop();
        }

        @SuppressLint("HandlerLeak")
        class MyHandler extends Handler {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String ms = "";
                if (msg.what == 90) {
                    int data = msg.getData().getInt(DATA);
                    //循环的时候界面依旧可以点击next按钮 这是本实例效果
                    for (int i = 0; i < data; i++) {
                        try {
                            //循环一次 暂停1秒
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        ms += i + ",";
                        Toast.makeText(getApplicationContext(), ms, Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }
}

package com.ceshi.ha.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ceshi.ha.R;

/**
 * Created by WY on 2017/9/21 0021.
 */

public class HandlerActivity extends AppCompatActivity {
    private EditText ed1;
    private Button ok1;
    private Button next;
    private ImageView image1;
    private Thread th;
    private static int s = 1;
    private int iamges[] = {
            R.mipmap.za1,
            R.mipmap.za2,
            R.mipmap.za3,
            R.mipmap.za4,
    };

    private final String DATA = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);
        ed1 = (EditText) findViewById(R.id.ed1);
        ok1 = (Button) findViewById(R.id.Ok);
        next = (Button) findViewById(R.id.next);
        image1 = (ImageView) findViewById(R.id.image1);

        final Rub rub = new Rub();
        //子线程中不能有UI组件进行操作
        th = new Thread(rub);
        // 启动线程
        th.start();

        ok1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!ed1.getText().toString().equals("")) {
                    Message msg = new Message();
                    msg.what = 0x123;
                    Bundle bundle = new Bundle();
                    bundle.putInt(DATA, Integer.parseInt(ed1.getText().toString()));
                    msg.setData(bundle);
                    rub.myHandler.sendMessage(msg);
                } else {
                    Toast.makeText(HandlerActivity.this, "请填写数字", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //在UI线程中操作组件
        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                image1.setImageResource(iamges[s++ % iamges.length]);
            }
        });
    }

    // 实现Runnable接口
    class Rub implements Runnable {

        private Handler myHandler;

        // 线程体
        @Override
        public void run() {
            Looper.prepare();

            myHandler = new Handler() {
                public void handleMessage(Message msg) {
                    String ms = "";
                    if (msg.what == 0x123) {
                        int data = msg.getData().getInt(DATA);
                        //循环的时候界面依旧可以点击next按钮 这是本实例效果
                        for (int i = 0; i < data; i++) {
                            try {
                                //循环一次 暂停1秒
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            ms += String.valueOf(i) + "  ";
                            Toast.makeText(getApplicationContext(), ms, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            };
            Looper.loop();
        }
    }
}

package com.ceshi.ha.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.ceshi.ha.view.MvpActivity;
import com.ceshi.ha.R;

/**
 * Created by Administrator on 2016/9/23.
 */
public class SplashActivity extends Activity implements View.OnClickListener {
    ImageView view;
    SplashActivity kaiping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_kaiping);
        view = (ImageView) findViewById(R.id.iv_kaiping);
        view.setBackgroundResource(R.mipmap.ic_launcher);
        view.setOnClickListener(this);
        Message a = Message.obtain();
        a.what = 0;
        handler.sendMessageDelayed(a, 3000);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Intent intent = new Intent(kaiping, MvpActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_kaiping:
                Toast.makeText(this, "开屏被点击了", Toast.LENGTH_LONG).show();
                break;
        }
    }
}

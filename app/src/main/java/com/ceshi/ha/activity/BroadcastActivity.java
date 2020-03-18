package com.ceshi.ha.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.ceshi.ha.R;
import com.ceshi.ha.databinding.ActivityReceiverBinding;

/**
 * Created by WY on 2017/9/17 0017.
 */

public class BroadcastActivity extends AppCompatActivity {

    public static final String ACTION = "com.iteye.myreceiver.action";

    ActivityReceiverBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReceiverBinding.inflate(LayoutInflater.from(BroadcastActivity.this));
        setContentView(binding.getRoot());

        MyReceiver mr = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(mr, filter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String n = "";
        if (intent != null) {
            n = intent.getStringExtra("name");
        }
        Toast.makeText(this, "动态注册接收者完成,收到粘性广播,name=" + n, Toast.LENGTH_SHORT).show();
    }

    /**
     * 发送广播,指定接收者权限,隐式意图,发送广播
     * 指定接收者权限
     */
    public void sendBroadcast(View view) {
        Intent i = new Intent();
        i.setAction("com.iteye.receiver.action");
        i.putExtra("name", "tom");
        sendBroadcast(i, " com.iteye.permission.receiver ");
    }

    /**
     * 发送粘性广播
     */
    public void sendStickyBroadCast(View view) {
        Intent intent = new Intent();
        intent.setAction("com.iteye.myreceiver.action");
        intent.putExtra("name", "tom");
        sendStickyBroadcast(intent);
    }

    public static class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ToastUtils.showShort("onReceive = ", intent.getAction(), intent.getExtras().get("name"));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("id", "翻转");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            String id = savedInstanceState.getString("id");
            ToastUtils.showShort(id);
        }
    }
}

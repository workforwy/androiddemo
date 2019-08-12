package com.ceshi.ha.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Debug;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ceshi.ha.R;
import com.ceshi.ha.utils.toast.ToastUtil;

import java.util.List;

/**
 * Created by WY on 2017/9/17 0017.
 */

public class BroadcastActivity extends AppCompatActivity {


    /**
     * 判断 savedInstanceState是不是空，如果不为空就取出来
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            long id = savedInstanceState.getLong("id");
            ToastUtil.showShort(this, "" + id);
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
        setContentView(R.layout.activity_receiver);

        MyReceiver mr = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.iteye.myreceiver.action ");
        filter.addCategory(Intent.CATEGORY_DEFAULT);

        Intent intent = registerReceiver(mr, filter);
        String n = "";
        if (intent != null) {
            n = intent.getStringExtra("name");
        }
        Toast.makeText(this, "动态注册接收者完成,收到粘性广播,name=" + n, Toast.LENGTH_SHORT).show();

    }

    /**
     * 发送广播,指定接收者权限,隐式意图,发送广播
     * 指定接收者权限
     * sendBroadcast(i, "com.iteye.permission.receiver");
     */
    public void sendBroadcast() {
        Intent i = new Intent();
        i.setAction("com.iteye.receiver.action");
        i.putExtra("name", "tom");
        sendBroadcast(i, " com.iteye.permission.receiver ");
        Log.i("Other", ".send ok!");
    }

    public static class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

    public void onClick(View v) {
        new Thread(new Runnable() {
            public void run() {

            }
        }).start();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putLong("id", 1234567890);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void sendStickyBroadCast(Intent intent) {
        intent.setAction("com.iteye.myreceiver.action");
        intent.putExtra("name", "tom");
        this.sendStickyBroadCast(intent);
    }

    /**
     * 直接调用短信接口发短信
     *
     * @param phoneNumber
     * @param message
     */
    public void sendSMS(String phoneNumber, String message) {
        //获取短信管理器
        SmsManager smsManager = SmsManager.getDefault();
        //拆分短信内容（手机短信长度限制）
        List<String> divideContents = smsManager.divideMessage(message);
        for (String text : divideContents) {
            //            smsManager.sendTextMessage(phoneNumber, null, text, sentPI, deliverPI);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Debug.stopMethodTracing();
    }
}

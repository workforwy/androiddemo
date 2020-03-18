package com.ceshi.ha.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * Created by WY on 2017/10/31 0031.
 */

public class BackService extends Service {

    private Thread mThread;

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("onBind");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //执行耗时操作

        mThread = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        //等待停止线程
                        if (this.isInterrupted()) {
                            throw new InterruptedException();
                        }
                        //耗时操作。
                        System.out.println("执行耗时操作");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        mThread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //停止线程
        mThread.interrupt();
    }
}

package com.ceshi.ha;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;

import com.ceshi.ha.crashhandler.CrashHandler;
import com.ceshi.ha.view.MvpActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 注意要在Manifest中设置android:name=".WXApplication"
 * 要实现ImageAdapter 否则图片不能下载
 * gradle 中一定要添加一些依赖，否则初始化会失败。
 * compile 'com.android.support:recyclerview-v7:23.1.1'
 * compile 'com.android.support:support-v4:23.1.1'
 * compile 'com.android.support:appcompat-v7:23.1.1'
 * compile 'com.alibaba:fastjson:1.1.45'
 */

public class MyApplication extends Application {

    public static List<Activity> activityList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        // 在application 全集的环境里面
        activityList = new ArrayList<Activity>();

    }

    /**
     * 全局异常处理
     */
    void openCrashHandler() {
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }

    /**
     * 杀掉进程,退出APP
     */
    void stopAPP() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    void getApp() {
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
    }

    /**
     * 一键退出app
     */
    public void stopAppByActivity() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }

    public void stopAppByIntent() {
        Intent intent = new Intent(this, MvpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }
}

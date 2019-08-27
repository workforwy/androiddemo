package com.ceshi.ha;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;

import com.ceshi.ha.utils.CrashHandler;
import com.ceshi.ha.activity.MvpActivity;

import java.util.ArrayList;
import java.util.List;


public class MyApplication extends Application {

    public static List<Activity> activityList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        activityList = new ArrayList<>();
        openCrashHandler();
    }

    /**
     * 全局异常处理
     */
    private void openCrashHandler() {
        if (!BuildConfig.DEBUG) {
            CrashHandler crashHandler = CrashHandler.getInstance();
            crashHandler.init(getApplicationContext());
        }
    }

    /**
     * 一键退出app
     */
    public static void stopAppByActivity() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }

    public void stopAppByIntent() {
        Intent intent = new Intent(this, MvpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    /**
     * 杀掉进程,退出APP
     */
    private void stopAPP() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}

package com.ceshi.ha;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import com.blankj.utilcode.util.CrashUtils;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

    public static List<Activity> activityList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        activityList = new ArrayList<>();
        openCrashHandler();
        QMUISwipeBackActivityManager.init(this);
    }

    /**
     * 全局异常处理
     */
    private void openCrashHandler() {
        if (!BuildConfig.DEBUG) {
            CrashUtils.init();
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

    /**
     * 杀掉进程,退出APP
     */
    private void stopAPP() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}

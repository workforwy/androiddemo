package com.ceshi.ha.app;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        QMUISwipeBackActivityManager.init(this)
    }
}

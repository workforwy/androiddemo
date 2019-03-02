package com.ceshi.ha.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.ceshi.ha.MyApplication;
import com.ceshi.ha.utils.AppState;

/**
 * Created by Administrator on 2016/7/25.
 */
public class BaseActivity extends Activity {
    public final String TAG = BaseActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        MyApplication.activityList.add(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        AppState.getInstance().onActivityVisible(this);
        Log.d(TAG, "启动");
    }

    @Override
    protected void onStop() {
        AppState.getInstance().onActivityNotVisible(this);
        Log.d(TAG, "暂停");
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "重启");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "销毁");
    }
}

package com.ceshi.ha.views.customview.floatwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;

import com.ceshi.ha.R;

public class FloatWindowTest extends Activity {

    WindowManager mWindowManager;
    WindowManager.LayoutParams wmParams;
    View mFloat, mMenu;
    FloatWindow floatWindow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        test1();

//        test2();
    }

    private void test2() {
        mFloat = LayoutInflater.from(this).inflate(R.layout.layout_float, null);
        mMenu = LayoutInflater.from(this).inflate(R.layout.layout_za, null);
        floatWindow = new FloatWindow(this);
        floatWindow.setFloatView(mFloat);
        floatWindow.setPlayerView(mMenu);
        floatWindow.show();
    }

    private void test1() {
        //获取的是LocalWindowManager对象
        mWindowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        //获取LayoutParams对象
        wmParams = new WindowManager.LayoutParams();
        //获取的是CompatModeWrapper对象
        wmParams.dimAmount = 0.2f;
        wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.alpha = 1.0f;
        wmParams.flags = WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        wmParams.gravity = Gravity.CENTER;
        wmParams.x = 0;
        wmParams.y = 0;
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        mFloat = LayoutInflater.from(this).inflate(R.layout.layout_float, null);
        mMenu = LayoutInflater.from(this).inflate(R.layout.layout_za, null);
        mWindowManager.addView(mFloat, wmParams);

        //绑定触摸移动监听
        mFloat.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                wmParams.x = (int) event.getRawX() - mFloat.getWidth() / 2;
                //25为状态栏高度
                wmParams.y = (int) event.getRawY() - mFloat.getHeight() / 2 - 25;
                mWindowManager.updateViewLayout(mFloat, wmParams);
                return false;
            }
        });

        //绑定点击监听
        mFloat.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mWindowManager.removeView(mFloat);
                wmParams.gravity = Gravity.CENTER;
                mWindowManager.addView(mMenu, wmParams);
            }
        });
    }
}
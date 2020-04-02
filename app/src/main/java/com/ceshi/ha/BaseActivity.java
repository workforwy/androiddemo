package com.ceshi.ha;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.qmuiteam.qmui.arch.QMUIActivity;

public abstract class BaseActivity extends QMUIActivity {

    long lastkeyBackDown;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断按下的是否是返回键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //记录下当前按下返回键的时间
            long currentTime = System.currentTimeMillis();
            //两次按下的时间间隔进行判断 ，判断是否是连续按下
            if (currentTime - lastkeyBackDown > 1500) {
                Toast.makeText(this, "不是连续按下，再按一下退出应用程序", Toast.LENGTH_SHORT).show();
                return super.onKeyDown(keyCode, event);
            } else {
                Toast.makeText(this, "是连续按下，退出应用程序", Toast.LENGTH_SHORT).show();
                finish();
            }
            //重置
            lastkeyBackDown = currentTime;
            //ture和false效果一样
            return false;
        }
        //建议不要动，继承父类的返回结果，因为我们只改变了返回键的点击事件，别的键还是要执行父类的默认方法
        return super.onKeyDown(keyCode, event);
    }
}

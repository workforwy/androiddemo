package com.ceshi.ha.base;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.ceshi.ha.R;
import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.OnKeyboardListener;
import com.qmuiteam.qmui.arch.QMUIActivity;

public abstract class BaseActivity extends QMUIActivity {

    long lastkeyBackDown;

    private ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBar();
    }

    private void initBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.transparentStatusBar()  //透明状态栏，不写默认透明色
                .transparentNavigationBar()  //透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为 true)
                .transparentBar()             //透明状态栏和导航栏，不写默认状态栏为透明色，导航栏为黑色（设置此方法，fullScreen()方法自动为 true）
                .statusBarColor(R.color.colorPrimary)     //状态栏颜色，不写默认透明色
                .navigationBarColor(R.color.colorPrimary) //导航栏颜色，不写默认黑色
                .barColor(R.color.colorPrimary)  //同时自定义状态栏和导航栏颜色，不写默认状态栏为透明色，导航栏为黑色
                .statusBarAlpha(0.3f)  //状态栏透明度，不写默认 0.0f
                .navigationBarAlpha(0.4f)  //导航栏透明度，不写默认 0.0F
                .barAlpha(0.3f)  //状态栏和导航栏透明度，不写默认 0.0f
                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
//                .flymeOSStatusBarFontColor(R.color.btn3)  //修改 flyme OS 状态栏字体颜色
//                .fullScreen(true)      //有导航栏的情况下，activity 全屏显示，也就是 activity 最下面被导航栏覆盖，不写默认非全屏
//                .hideBar(BarHide.FLAG_HIDE_BAR)  //隐藏状态栏或导航栏或两者，不写默认不隐藏
//                .addViewSupportTransformColor(toolbar)  //设置支持 view 变色，可以添加多个 view，不指定颜色，默认和状态栏同色，还有两个重载方法
//                .titleBar(view)    //解决状态栏和布局重叠问题，任选其一
//                .titleBarMarginTop(view)     //解决状态栏和布局重叠问题，任选其一
//                .statusBarView(view)  //解决状态栏和布局重叠问题，任选其一
//                .fitsSystemWindows(true)    //解决状态栏和布局重叠问题，任选其一，默认为 false，当为 true 时一定要指定 statusBarColor()，不然状态栏为透明色，还有一些重载方法
//                .supportActionBar(true) //支持 ActionBar 使用
//                .statusBarColorTransform(R.color.orange)  //状态栏变色后的颜色
//                .navigationBarColorTransform(R.color.orange) //导航栏变色后的颜色
//                .barColorTransform(R.color.orange)  //状态栏和导航栏变色后的颜色
//                .removeSupportView(toolbar)  //移除指定 view 支持
                .removeSupportAllView() //移除全部 view 支持
                .navigationBarEnable(true)   //是否可以修改导航栏颜色，默认为 true
                .navigationBarWithKitkatEnable(true)  //是否可以修改安卓 4.4 和 emui3.1 手机导航栏颜色，默认为 true
                .fixMarginAtBottom(true)   //已过时，当 xml 里使用 android:fitsSystemWindows="true"属性时,解决 4.4 和 emui3.1 手机底部有时会出现多余空白的问题，默认为 false，非必须
                .addTag("tag")  //给以上设置的参数打标记
                .getTag("tag")  //根据 tag 获得沉浸式参数
                .reset()  //重置所以沉浸式参数
                .keyboardEnable(true)  //解决软键盘与底部输入框冲突问题，默认为 false，还有一个重载方法，可以指定软键盘 mode
                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)  //单独指定软键盘模式
                .setOnKeyboardListener(new OnKeyboardListener() {    //软键盘监听回调
                    @Override
                    public void onKeyboardChange(boolean isPopup, int keyboardHeight) {
                        LogUtils.e(isPopup);  //isPopup 为 true，软键盘弹出，为 false，软键盘关闭
                    }
                });
        mImmersionBar.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
        if (mImmersionBar != null)
            mImmersionBar.destroy();
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

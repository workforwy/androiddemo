package com.ceshi.ha.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ceshi.ha.R;
import com.ceshi.ha.mvp.interfaceview.IMainActivityView;
import com.ceshi.ha.mvp.compl.MainActivityPresenterCompl;
import com.ceshi.ha.mvp.presenter.IMainActivityPresenter;
import com.jerey.animationlib.AnimationHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rex on 2017/3/18 0018.
 */
public class MvpActivity extends AppCompatActivity implements IMainActivityView, View.OnClickListener{

    @BindView(R.id.submit_pro)
    public ProgressBar progressBar;
    @BindView(R.id.clean)
    public Button btn_clean;
    @BindView(R.id.submit)
    public Button btn_submit;
    @BindView(R.id.iv_touxiang)
    public ImageView touxiang;

    IMainActivityPresenter mainActivityPresenter;
    List<EditText> editList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mvp);
        ButterKnife.bind(this);
        init();
        initView();

    }

    @Override
    public void init() {
        mainActivityPresenter = new MainActivityPresenterCompl();
        editList = new ArrayList<>();
    }

    @Override
    public void initView() {
        editList.add((EditText) findViewById(R.id.age));
        editList.add((EditText) findViewById(R.id.gender));
        editList.add((EditText) findViewById(R.id.name));
        editList.add((EditText) findViewById(R.id.hobby));
        btn_submit.setOnClickListener(this);
        btn_clean.setOnClickListener(this);
        progressBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                submitData();
                AnimationHelper.show(touxiang);
                break;
            case R.id.clean:
                initData();
                break;
        }
    }

    @Override
    public void submitData() {
        mainActivityPresenter.submitData(this, editList, progressBar);
    }

    @Override
    public void initData() {
        mainActivityPresenter.initData(editList);
    }

    long lastkeyBackDown;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断按下的是否是返回键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //记录下当前按下返回键的时间
            long currentTime = System.currentTimeMillis();
            //两次按下的时间间隔进行判断 ，判断是否是连续按下
            if (currentTime - lastkeyBackDown > 1500) {
                Toast.makeText(this, "不是连续按下，再按一下退出应用程序", Toast.LENGTH_SHORT).show();
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

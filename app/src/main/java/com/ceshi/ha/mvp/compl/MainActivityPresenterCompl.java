package com.ceshi.ha.mvp.compl;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ceshi.ha.bean.User;
import com.ceshi.ha.mvp.presenter.IMainActivityPresenter;

import java.util.List;

/**
 * Created by rex on 2017/3/18 0018.
 */

public class MainActivityPresenterCompl implements IMainActivityPresenter {

    @Override
    public void submitData(final Context context, final List<EditText> editList, final ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        final Handler mainHandler = new Handler(Looper.getMainLooper());
        new Thread() {
            @Override
            public void run() {
                /*模拟提交信息*/
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                User info = new User();
                info.setAge(editList.get(0).getText().toString());
                info.setGender(editList.get(1).getText().toString());
                info.setName(editList.get(2).getText().toString());
                info.setHobby(editList.get(3).getText().toString());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(context, "提交数据完成", Toast.LENGTH_SHORT).show();
                        initData(editList);
                    }
                });
            }
        }.start();
    }

    @Override
    public void initData(List<EditText> editList) {
        for (EditText editText : editList)
            editText.setText("");
    }
}

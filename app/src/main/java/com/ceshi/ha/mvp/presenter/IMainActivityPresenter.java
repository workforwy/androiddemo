package com.ceshi.ha.mvp.presenter;

import android.content.Context;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.List;

/**
 * Created by rex on 2017/3/18 0018.
 */

public interface IMainActivityPresenter {

    void submitData(Context context, List<EditText> editList, ProgressBar progressBar);

    void initData(List<EditText> editList);
}

package com.ceshi.ha.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ceshi.ha.R;
import com.ceshi.ha.databinding.ActivityMvpBinding;
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
public class MvpActivity extends AppCompatActivity implements IMainActivityView, View.OnClickListener {

    IMainActivityPresenter mainActivityPresenter;
    List<EditText> editList;
    ActivityMvpBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMvpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
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
        editList.add(binding.name);
        editList.add(binding.age);
        binding.submit.setOnClickListener(this);
        binding.clean.setOnClickListener(this);
        binding.submitPro.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                submitData();
                AnimationHelper.show(binding.ivTouxiang);
                break;
            case R.id.clean:
                clearContent();
                break;
        }
    }

    @Override
    public void submitData() {
        mainActivityPresenter.submitData(this, editList, binding.submitPro);
    }

    @Override
    public void clearContent() {
        mainActivityPresenter.clearContent(editList);
    }


}

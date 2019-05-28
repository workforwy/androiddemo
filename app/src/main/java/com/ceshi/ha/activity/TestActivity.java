package com.ceshi.ha.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ceshi.ha.R;

/**
 * Created by wy on 2017/5/31.
 */

public class TestActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progressbar1);
    }
}

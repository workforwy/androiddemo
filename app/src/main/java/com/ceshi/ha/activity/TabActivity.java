package com.ceshi.ha.activity;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.ceshi.ha.databinding.ActivityTabBinding;
import com.ceshi.ha.fragment.Fragment_Home;
import com.ceshi.ha.fragment.Fragment_Main;
import com.ceshi.ha.fragment.Fragment_My;

/**
 * Created on 2018/3/10 0010 11:17.
 *
 * @author wy
 * 类定义：
 */
public class TabActivity extends FragmentActivity {

    ActivityTabBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTabBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        QMUIFragmentPagerAdapter qmuiFragmentPagerAdapter = new QMUIFragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public QMUIFragment createFragment(int position) {
                switch (position) {
                    case 0:
                        return new Fragment_Home();
                    case 1:
                        return new Fragment_Main();
                    case 2:
                    default:
                        return new Fragment_My();
                }
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "首页";
                    case 1:
                        return "主页";
                    case 2:
                    default:
                        return "我的";
                }
            }
        };
        binding.qvp.setAdapter(qmuiFragmentPagerAdapter);
        binding.qtab.setupWithViewPager(binding.qvp);
    }
}
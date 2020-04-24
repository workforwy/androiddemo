package com.ceshi.ha.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTabHost;
import androidx.viewpager.widget.PagerAdapter;

import com.ceshi.ha.R;
import com.ceshi.ha.databinding.ActivityTabBinding;
import com.ceshi.ha.fragment.Fragment_Home;
import com.ceshi.ha.fragment.Fragment_Main;
import com.ceshi.ha.fragment.Fragment_My;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.arch.QMUIFragmentPagerAdapter;
import com.qmuiteam.qmui.widget.QMUIPagerAdapter;
import com.qmuiteam.qmui.widget.tab.QMUITab;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;

import java.util.ArrayList;
import java.util.List;

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
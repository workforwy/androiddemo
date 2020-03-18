package com.ceshi.ha.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTabHost;

import com.ceshi.ha.MainActivity;
import com.ceshi.ha.R;
import com.ceshi.ha.fragment.Fragment_Home;
import com.ceshi.ha.fragment.Fragment_Main;
import com.ceshi.ha.fragment.Fragment_My;

/**
 * Created on 2018/3/10 0010 11:17.
 *
 * @author wy
 * 类定义：
 */
public class TabActivity extends FragmentActivity implements TabHost.OnTabChangeListener {

    FragmentTabHost tabhost;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        setTabHost();
    }

    private void setTabHost() {
        tabhost = findViewById(android.R.id.tabhost);
        tabhost.setup(this, getSupportFragmentManager(), R.id.contentLayout);
        tabhost.getTabWidget().setDividerDrawable(R.mipmap.bt_bg);
        tabhost.setOnTabChangedListener(this);
        for (int i = 0; i < getTabsText().length; i++) {
            TabHost.TabSpec tabSpec = tabhost.newTabSpec(getTabsText()[i]).setIndicator(getTabView(i));
            tabhost.addTab(tabSpec, getFragments()[i], null);
            tabhost.setTag(i);
        }
    }

    private View getTabView(int idx) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_content, null);
        textView = view.findViewById(R.id.tvtab);
        textView.setText(getTabsText()[idx]);
        if (idx == 0) {
            textView.setTextColor(Color.RED);
        }
        return view;
    }

    @Override
    public void onTabChanged(String tabId) {
        TabWidget tabw = tabhost.getTabWidget();
        for (int i = 0; i < tabw.getChildCount(); i++) {
            View view = tabw.getChildAt(i);
            if (i == tabhost.getCurrentTab()) {
                ((TextView) view.findViewById(R.id.tvtab)).setTextColor(Color.RED);
            } else {
                ((TextView) view.findViewById(R.id.tvtab)).setTextColor(Color.GRAY);
            }
        }
    }

    public String[] getTabsText() {
        String[] tabs = new String[getFragments().length];
        for (int i = 0; i < getFragments().length; i++) {
            tabs[i] = getFragments()[i].getSimpleName();
        }
        return tabs;
    }

    public Class[] getFragments() {
        return new Class[]{Fragment_Home.class, Fragment_Main.class, Fragment_My.class};
    }
}
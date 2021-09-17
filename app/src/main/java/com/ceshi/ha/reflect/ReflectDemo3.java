package com.ceshi.ha.reflect;

import java.lang.reflect.Field;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.ceshi.ha.R;

public class ReflectDemo3 extends Activity implements OnQueryTextListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reflectdemo3);
        // 1.获得searchView
        SearchView sv = (SearchView) findViewById(R.id.searchView1);
        // 展开searchView
        sv.onActionViewExpanded();
        // 设置字体默认颜色
        Class<?> c = sv.getClass();
        try {

            Field[] fs = c.getDeclaredFields();
            for (Field fd : fs) {
                Log.i("TAG", fd.getName());
            }

            Field f = c.getDeclaredField("mQueryTextView");
            f.setAccessible(true);
            AutoCompleteTextView sac = (AutoCompleteTextView) f.get(sv);
            // sac.setTextColor(0xff0000);
            sac.setTextColor(Color.RED);
            //sac.setTextColor( getResources() .getColor(R.color.red_dark));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 2.添加监听器
        sv.setOnQueryTextListener(this);
    }

    /**
     * 文本内容发生变化会自动执行此方法
     */
    @Override
    public boolean onQueryTextChange(String text) {
        Log.i("TAG", "text=" + text);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String arg0) {
        Log.i("tag", "onQueryTextSubmit");
        return false;
    }
}


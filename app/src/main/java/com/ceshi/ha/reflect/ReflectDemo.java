package com.ceshi.ha.reflect;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.ceshi.ha.R;

public class ReflectDemo extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        1.根据资源id找到xml资源
//        2.借助pull解析，解析xml资源，获得元素
//        3.借助反射构建xml元素对象
        XmlResourceParser xpp = getResources().getLayout(R.layout.view_btn_1);

        View v;
        v = View.inflate(this, R.layout.view_btn_1, null);
        v = LayoutInflater.from(this).inflate(R.layout.view_btn_1, null);
        setContentView(v);
    }

    public void onClick(View view) {
        Button btn = (Button) view;
        btn.setText("hello xuanzhuo!");
    }

}

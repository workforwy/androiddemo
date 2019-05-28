package com.ceshi.ha.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

import com.ceshi.ha.R;

/**
 * 关于Android 加载 html 页面
 */
public class HtmlActivity extends AppCompatActivity {

    TextView textView1, textView2, textView3, textView4, textView5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html);


        textView1 = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);


        textView1.setText(R.string.what_the_html);
        textView2.setText(getString(R.string.what_the_html));
        textView3.setText(getText(R.string.what_the_html));

        /**
         *  <![CDATA[ 属性保留了样式
         */
        textView4.setText(Html.fromHtml(getString(R.string.what_the_html_2)));
        textView5.setText(Html.fromHtml(getString(R.string.what_the_html)));
    }
}

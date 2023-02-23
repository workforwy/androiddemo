package com.ceshi.ha.activity;

import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class ApiActivity extends Activity implements OnClickListener {
    private AtomicInteger ato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //读网络文件(获得已有的点赞的值)
        ato = new AtomicInteger(100);//可以理解从网络中获取的
        Button btn = new Button(this);

        btn.setText(String.valueOf(ato.getAndIncrement()));
        btn.setOnClickListener(this);
        setContentView(btn);
    }

    /**
     * @param v 表示被点击的对象
     */
    @Override
    public void onClick(View v) {//v-->Button
        if (v instanceof Button) {
            ((Button) v).
                    setText(String.valueOf(ato.getAndIncrement()));
        }
        //将加1以后的值要存储到网络的服务端
    }

    /**
     * activity销毁时会自动执行此方法
     */
    @Override
    protected void onDestroy() {
        ato = null;
        super.onDestroy();
    }
}

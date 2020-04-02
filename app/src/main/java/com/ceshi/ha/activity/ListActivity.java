package com.ceshi.ha.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.blankj.utilcode.util.ToastUtils;
import com.ceshi.ha.R;
import com.ceshi.ha.adapter.ContentAdapter2;
import com.ceshi.ha.databinding.ActivityListviewBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * adapter 代理模式
 */
public class ListActivity extends Activity implements AdapterView.OnItemClickListener {

    // 模拟listview中加载的数据
    private String[] CONTENTS = {"北京", "上海", "广州", "深圳", "苏州", "南京", "武汉", "长沙", "杭州"};
    private List<String> contentList = new ArrayList<String>();
    ActivityListviewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        for (int i = 0; i < CONTENTS.length; i++) {
            contentList.add(CONTENTS[i]);
        }
        binding.listview.setAdapter(new ContentAdapter2(this, R.layout.item_listview, contentList, mListener));
        binding.listview.setOnItemClickListener(this);
    }

    /**
     * 实现类，响应按钮点击事件
     */
    private ContentAdapter2.MyClickListener mListener = new ContentAdapter2.MyClickListener() {
        @Override
        public void onClick(View view) {
            ToastUtils.showShort("内部");
        }

        @Override
        public void myOnClick(int position, View v) {
            ToastUtils.showShort("内部,位置:" + position + ",内容是:" + contentList.get(position));
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ToastUtils.showShort("外部: item被点击了！，点击的位置是-->" + position);
    }
}

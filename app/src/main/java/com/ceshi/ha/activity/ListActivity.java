package com.ceshi.ha.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ceshi.ha.R;
import com.ceshi.ha.adapter.ContentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rex on 2017/3/28 0028.
 */

public class ListActivity extends Activity implements AdapterView.OnItemClickListener {

    // 模拟listview中加载的数据
    private static final String[] CONTENTS = {"北京", "上海", "广州", "深圳", "苏州", "南京", "武汉", "长沙", "杭州"};
    private List<String> contentList = new ArrayList<String>();
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        init();
    }

    private void init() {
        mListView = (ListView) findViewById(R.id.listview);
        for (int i = 0; i < CONTENTS.length; i++) {
            contentList.add(CONTENTS[i]);
        }
        //实例化ContentAdapter类，并传入实现类接口
        mListView.setAdapter(new ContentAdapter(this, contentList, mListener));
        mListView.setOnItemClickListener(this);
    }

    /**
     * 实现类，响应按钮点击事件
     */
    private ContentAdapter.MyClickListener mListener = new ContentAdapter.MyClickListener() {
        @Override
        public void myOnClick(int position, View v) {
            Log.d("123", "内部,位置:" + position + ",内容是:" + contentList.get(position));
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("123", "外部: item被点击了！，点击的位置是-->" + position);
    }
}

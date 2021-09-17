package com.ceshi.ha.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ceshi.ha.adapter.RecycleAdapter;
import com.ceshi.ha.base.BaseActivity;
import com.ceshi.ha.bean.User;
import com.ceshi.ha.databinding.ActivityMainBinding;
import com.ceshi.ha.reflect.ReflectDemo;
import com.ceshi.ha.reflect.ReflectDemo2;
import com.ceshi.ha.reflect.ReflectDemo3;

import java.util.ArrayList;
import java.util.List;

/**
 * recycleview
 */
public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.setUser(new User("12","张三","男","爱好"));

        RecyclerView mRecyclerView = binding.recyclerview;
        //设置布局管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        // 设置分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
        // 设置 adapter
        RecycleAdapter mAdapter = new RecycleAdapter(this, getName());
        mAdapter.setOnItemClickListener(new RecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, getData().get(position));
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    private List<Class> getData() {
        List<Class> mDatas = new ArrayList<>();
        mDatas.add(BroadcastActivity.class);
        mDatas.add(HandlerActivity.class);
        mDatas.add(ListActivity.class);
        mDatas.add(ThreadActivity.class);
        mDatas.add(WebViewActivity.class);
        mDatas.add(TabActivity.class);
        mDatas.add(TakePhotoActivity.class);
        mDatas.add(RXActivity.class);
        mDatas.add(RetrofitActivity.class);

        mDatas.add(ReflectDemo.class);
        mDatas.add(ReflectDemo2.class);
        mDatas.add(ReflectDemo3.class);
        return mDatas;
    }

    private List<String> getName() {
        List<String> mDatas = new ArrayList<>();
        for (int i = 0; i < getData().size(); i++) {
            mDatas.add(getData().get(i).getSimpleName());
        }
        return mDatas;
    }
}

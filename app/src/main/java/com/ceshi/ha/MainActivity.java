package com.ceshi.ha;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ceshi.ha.activity.BroadcastActivity;
import com.ceshi.ha.activity.HandlerActivity;
import com.ceshi.ha.activity.ListActivity;
import com.ceshi.ha.activity.RetrofitActivity;
import com.ceshi.ha.activity.WebViewActivity;
import com.ceshi.ha.activity.TabActivity;
import com.ceshi.ha.activity.TakePhotoActivity;
import com.ceshi.ha.activity.ThreadActivity;
import com.ceshi.ha.adapter.RecycleAdapter;
import com.ceshi.ha.bean.User;
import com.ceshi.ha.databinding.ActivityMainBinding;
import com.ceshi.ha.activity.RXActivity;

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

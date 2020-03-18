package com.ceshi.ha;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ceshi.ha.activity.BroadcastActivity;
import com.ceshi.ha.activity.HandlerActivity;
import com.ceshi.ha.activity.ListActivity;
import com.ceshi.ha.activity.MvpActivity;
import com.ceshi.ha.activity.RefreshActivity;
import com.ceshi.ha.activity.TabActivity;
import com.ceshi.ha.activity.TakePhotoActivity;
import com.ceshi.ha.activity.ThreadActivity;
import com.ceshi.ha.adapter.RecycleAdapter;
import com.ceshi.ha.rx.RXActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * recycleview
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView mRecyclerView = findViewById(R.id.recyclerview);
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
        mDatas.add(MvpActivity.class);
        mDatas.add(ThreadActivity.class);
        mDatas.add(RefreshActivity.class);
        mDatas.add(TabActivity.class);
        mDatas.add(TakePhotoActivity.class);
        mDatas.add(RXActivity.class);
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

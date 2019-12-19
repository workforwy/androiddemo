package com.ceshi.ha;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ceshi.ha.activity.BroadcastActivity;
import com.ceshi.ha.activity.HandlerActivity;
import com.ceshi.ha.activity.ListActivity;
import com.ceshi.ha.activity.MvpActivity;
import com.ceshi.ha.activity.NewActivity;
import com.ceshi.ha.activity.ProgressBarActivity;
import com.ceshi.ha.activity.RefreshActivity;
import com.ceshi.ha.activity.RxAndroid;
import com.ceshi.ha.activity.TabActivity;
import com.ceshi.ha.activity.TakePhotoActivity;
import com.ceshi.ha.adapter.RecycleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * recycleview
 */
public class MainActivity extends Activity {

    private RecyclerView mRecyclerView;
    private RecycleAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
    }

    private void setViews() {
        mRecyclerView = findViewById(R.id.recyclerview);
        setLinear(mRecyclerView);
        mAdapter = new RecycleAdapter(this, getName());
        mRecyclerView.setAdapter(mAdapter);
        setListener();
    }

    private void setListener() {
        mAdapter.setOnItemClickListener(new RecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, getData().get(position));
                startActivity(intent);
            }
        });
    }

    /**
     * 设置竖向
     */
    private void setLinear(RecyclerView mRecyclerView) {
        //设置布局管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        // 设置分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
    }

    private List<Class> getData() {
        List<Class> mDatas = new ArrayList<>();
        mDatas.add(BroadcastActivity.class);
        mDatas.add(HandlerActivity.class);
        mDatas.add(ListActivity.class);
        mDatas.add(MvpActivity.class);
        mDatas.add(NewActivity.class);
        mDatas.add(ProgressBarActivity.class);
        mDatas.add(RefreshActivity.class);
        mDatas.add(RxAndroid.class);
        mDatas.add(TabActivity.class);
        mDatas.add(TakePhotoActivity.class);
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

package com.ceshi.ha;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ceshi.ha.activity.TestActivity;
import com.ceshi.ha.adapter.RecycleAdapter;
import com.ceshi.ha.utils.acp.AcpActivity;
import com.ceshi.ha.utils.divider.DividerItemDecoration;
import com.ceshi.ha.view.HtmlActivity;
import com.ceshi.ha.view.NewActivity;
import com.ceshi.ha.view.RxAndroid;
import com.ceshi.ha.view.TabActivity;
import com.ceshi.ha.view.TakePhotoActivity;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;


/**
 * recycleview
 */
public class MainActivity extends Activity {

    private RecyclerView mRecyclerView;
    private RecycleAdapter mAdapter;
    private AlphaAnimatorAdapter animatorAdapter;

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
        animatorAdapter = new AlphaAnimatorAdapter(mAdapter, mRecyclerView);
        mRecyclerView.setAdapter(animatorAdapter);
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
        mRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL_LIST));
    }

    private List<Class> getData() {
        List<Class> mDatas = new ArrayList<>();
        mDatas.add(HtmlActivity.class);
        mDatas.add(NewActivity.class);
        mDatas.add(TabActivity.class);
        mDatas.add(TakePhotoActivity.class);
        mDatas.add(AcpActivity.class);
        mDatas.add(TakePhotoActivity.class);
        mDatas.add(TestActivity.class);
        mDatas.add(RxAndroid.class);
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

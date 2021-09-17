package com.ceshi.ha.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ceshi.ha.R;
import com.ceshi.ha.base.adapter.BaseViewHolder;
import com.ceshi.ha.base.adapter.CommonAdapter;

import java.util.List;

public class ContentAdapter2 extends CommonAdapter<String> {

    MyClickListener mListener;

    public ContentAdapter2(Context context, int layoutResId, List<String> dataList, MyClickListener myClickListener) {
        super(context, layoutResId, dataList);
        this.mListener = myClickListener;
    }

    @Override
    protected void convert(BaseViewHolder holder, String s) {
        ((TextView) holder.getViewByViewId(R.id.textView1)).setText(s);
        // 为button设置监听和tag
        ((Button) holder.getViewByViewId(R.id.button1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.myOnClick(holder.getPosition(), holder.getConvertView());
            }
        });
    }

    public abstract static class MyClickListener implements View.OnClickListener {
        public abstract void myOnClick(int position, View v);
    }

}


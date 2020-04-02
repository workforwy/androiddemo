package com.ceshi.ha.adapter.baseadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import java.util.List;

/**
 * 通用 adapter
 *
 * @param <T>
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    private Context context;
    private int layoutResId;
    private List<T> dataList;

    public CommonAdapter(@NonNull Context context, @LayoutRes int layoutResId, @NonNull List<T> dataList) {
        this.context = context;
        this.layoutResId = layoutResId;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public T getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder baseViewHolder = BaseViewHolder.getViewHolder(context, position, convertView, parent, layoutResId);
        convert(baseViewHolder, dataList.get(position));
        return baseViewHolder.getConvertView();
    }

    protected abstract void convert(BaseViewHolder baseViewHolder, T t);
}

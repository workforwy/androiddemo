package com.ceshi.ha.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 混合式代理
 *
 * @param <T>
 */

public class MultiCommonAdapter<T extends List<?>> extends BaseAdapter {

    private Context context;
    private T dataList;
    private AdapterDelegateManage<T> adapterDelegateManage = new AdapterDelegateManage<>();

    public MultiCommonAdapter(Context context, T dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return adapterDelegateManage.getItemViewType(position, dataList);
    }

    @Override
    public int getViewTypeCount() {
        return adapterDelegateManage.getAdapterDelegateCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdapterDelegate<T> adapterDelegate = adapterDelegateManage.getDelegateByViewType(getItemViewType(position));
        BaseViewHolder baseViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(adapterDelegate.getItemLayoutResId(), parent, false);
            baseViewHolder = new BaseViewHolder(convertView,position);
            convertView.setTag(baseViewHolder);
        } else {
            baseViewHolder = (BaseViewHolder) convertView.getTag();
        }
        adapterDelegateManage.convert(position, dataList, baseViewHolder);
        return convertView;
    }

    protected MultiCommonAdapter addItemViewDelegate(AdapterDelegate<T> adapterDelegate) {
        adapterDelegateManage.addDelegate(adapterDelegate);
        return this;
    }
}

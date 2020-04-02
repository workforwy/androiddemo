package com.ceshi.ha.adapter.baseadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.collection.SparseArrayCompat;

/**
 * adappter 通用 holder
 */
public class BaseViewHolder {

    private View convertView;   //adapter中getView() 返回的对象，用于与ListView的数据绑定
    private SparseArrayCompat<View> viewSparseArrayCompat = new SparseArrayCompat<>();

    int position; //当前位置

    public BaseViewHolder(View convertView, int position) {
        this.convertView = convertView;
        this.position = position;
    }

    public static BaseViewHolder getViewHolder(Context context, int position, View convertView, ViewGroup parent, @LayoutRes int resource) {
        BaseViewHolder baseViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
            baseViewHolder = new BaseViewHolder(convertView, position);
            convertView.setTag(baseViewHolder);
        } else {
            baseViewHolder = (BaseViewHolder) convertView.getTag();
        }
        return baseViewHolder;
    }

    public int getPosition() {
        return position;
    }

    public View getViewByViewId(@IdRes int viewId) {
        View view = viewSparseArrayCompat.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            viewSparseArrayCompat.put(viewId, view);
        }
        return view;
    }

    public View getConvertView() {
        return convertView;
    }
}

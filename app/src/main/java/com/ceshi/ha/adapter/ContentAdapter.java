package com.ceshi.ha.adapter;

/**
 * Created by rex on 2017/3/28 0028.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ceshi.ha.R;

import java.util.List;

public class ContentAdapter extends BaseAdapter {

    private static final String TAG = "ContentAdapter";
    private List<String> mContentList;
    private LayoutInflater mInflater;
    private MyClickListener mListener;

    public ContentAdapter(Context context, List<String> contentList, MyClickListener listener) {
        mContentList = contentList;
        mInflater = LayoutInflater.from(context);
        mListener = listener;
    }

    @Override
    public int getCount() {
        return mContentList.size();
    }

    @Override
    public Object getItem(int position) {
        return mContentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_listview, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.textView1);
            holder.button = (Button) convertView.findViewById(R.id.button1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(mContentList.get(position));
        // 为button设置监听和tag
        holder.button.setOnClickListener(mListener);
        holder.button.setTag(position);
        return convertView;
    }

    /**
     * 复用模板
     */
    class ViewHolder {
        TextView textView;
        public Button button;
    }

    /**
     * 用于回调的抽象类
     */
    public static abstract class MyClickListener implements View.OnClickListener {
        //基类的onClick方法
        @Override
        public void onClick(View v) {
            myOnClick((Integer) v.getTag(), v);
        }

        public abstract void myOnClick(int position, View v);
    }
}
package com.ceshi.ha.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ceshi.ha.R;

import java.util.List;

import static android.view.LayoutInflater.from;

/**
 * Created on 2018/1/4 0004.
 *
 * @author wy
 * 类定义：
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

    private List<String> mDatas;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public RecycleAdapter(Context context, List<String> mDatas) {
        mContext = context;
        this.mDatas = mDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(from(mContext).inflate(R.layout.item_recycle, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.tv.setText(mDatas.get(position));

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickListener != null) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, holder.getLayoutPosition());

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void addData(int position) {
        mDatas.add(position, "插入一个对象");
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemClickListener {

        void onItemClick(View view, int position);

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        ViewHolder(View view) {
            super(view);
            tv = view.findViewById(R.id.id_num);
        }
    }
}

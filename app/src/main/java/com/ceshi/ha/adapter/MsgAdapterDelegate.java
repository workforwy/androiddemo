package com.ceshi.ha.adapter;

import com.ceshi.ha.base.adapter.AdapterDelegate;
import com.ceshi.ha.base.adapter.BaseViewHolder;
import com.ceshi.ha.bean.BaseModel;
import com.ceshi.ha.bean.Message1;

import java.util.List;

public class MsgAdapterDelegate implements AdapterDelegate<List<BaseModel>> {

    @Override
    public int getItemLayoutResId() {
        return 0;
    }

    @Override
    public boolean isForViewType(int position, List<BaseModel> adapterDataSource) {
        return adapterDataSource.get(position) instanceof Message1;
    }

    @Override
    public void convert(int position, BaseViewHolder baseViewHolder, List<BaseModel> adapterDataSource) {
        if (adapterDataSource.get(position) instanceof Message1) {
            Message1 message = (Message1) adapterDataSource.get(position);
//            TextView time = (TextView) baseViewHolder.getViewByViewId(R.id.tv_time);
//            time.setText(message.getTime());
//            TextView title = (TextView) baseViewHolder.getViewByViewId(R.id.tv_title);
//            title.setText(message.getTitle());
//            TextView content = (TextView) baseViewHolder.getViewByViewId(R.id.tv_content);
//            content.setText(message.getContent());
        }
    }
}

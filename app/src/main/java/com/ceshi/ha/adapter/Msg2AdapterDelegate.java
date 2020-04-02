package com.ceshi.ha.adapter;

import com.ceshi.ha.adapter.baseadapter.AdapterDelegate;
import com.ceshi.ha.adapter.baseadapter.BaseViewHolder;
import com.ceshi.ha.bean.BaseModel;
import com.ceshi.ha.bean.Message2;

import java.util.List;

public class Msg2AdapterDelegate implements AdapterDelegate<List<BaseModel>> {

    @Override
    public int getItemLayoutResId() {
        return 0;
    }

    @Override
    public boolean isForViewType(int position, List<BaseModel> adapterDataSource) {
        return adapterDataSource.get(position) instanceof Message2;
    }

    @Override
    public void convert(int position, BaseViewHolder baseViewHolder, List<BaseModel> adapterDataSource) {
        if (adapterDataSource.get(position) instanceof Message2) {
            Message2 dog = (Message2) adapterDataSource.get(position);
//            TextView textView = (TextView) baseViewHolder.getViewByViewId(R.id.id_tv_title);
//            textView.setText(dog.getName());
        }
    }
}

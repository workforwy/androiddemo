package com.ceshi.ha.adapter.baseadapter;

import androidx.annotation.LayoutRes;

/**
 * Adapter 模块代理接口
 *
 * @param <T> adapter 数据源 i.e List<BeanName>
 */
public interface AdapterDelegate<T> {

    @LayoutRes
    int getItemLayoutResId();
    /**
     * 判断
     *
     * @param position          itemview 在Adapter中的位置
     * @param adapterDataSource adapter 数据源
     * @return true item 是对应viewtype，false 不是
     */
    boolean isForViewType(int position, T adapterDataSource);

    /**
     * itemview中的子view 根据 数据，绘制界面
     *
     * @param position          itemview 在Adapter中的位置
     * @param baseViewHolder    与itemview绑定在一起的viewHolder
     * @param adapterDataSource adapter 数据源
     */
    void convert(int position, BaseViewHolder baseViewHolder, T adapterDataSource);
}
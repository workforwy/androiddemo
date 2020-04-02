package com.ceshi.ha.adapter.baseadapter;


import androidx.annotation.NonNull;
import androidx.collection.SparseArrayCompat;

/**
 * 数据源
 *
 * @param <T>
 */
public class AdapterDelegateManage<T> {

    private SparseArrayCompat<AdapterDelegate<T>> delegates = new SparseArrayCompat<>();

    public AdapterDelegateManage<T> addDelegate(@NonNull AdapterDelegate<T> delegate) {
        int viewType = delegates.size();
        if (delegates.get(viewType) != null) {
            viewType++;
        }
        addDelegate(viewType, false, delegate);
        return this;
    }

    public AdapterDelegateManage<T> addDelegate(int viewType, boolean allowReplacingDelegate, @NonNull AdapterDelegate<T> delegate) {
        if (!allowReplacingDelegate && delegates.get(viewType) != null) {
            throw new IllegalArgumentException(
                    "An ItemViewDelegate is already registered for the viewType = "
                            + viewType
                            + ". Already registered ItemViewDelegate is "
                            + delegates.get(viewType));
        }

        delegates.put(viewType, delegate);
        return this;
    }

    public int getItemViewType(int position, T adapterDataSource) {
        int delegateCount = delegates.size();
        for (int i = 0; i < delegateCount; i++) {
            AdapterDelegate<T> adapterDelegate = delegates.valueAt(i);
            if (adapterDelegate.isForViewType(position, adapterDataSource)) {
                return delegates.keyAt(i);
            }
        }
        throw new NullPointerException("No AdapterDelegate added that matches position=" + position + " in data source");
    }

    public int getAdapterDelegateCount() {
        return delegates.size();
    }

    public AdapterDelegate<T> getDelegateByViewType(int viewType) {
        return delegates.get(viewType);
    }

    public void convert(int position, T adapterDataSource, BaseViewHolder baseViewHolder) {
        int delegateCount = delegates.size();
        for (int i = 0; i < delegateCount; i++) {
            AdapterDelegate<T> adapterDelegate = delegates.valueAt(i);
            if (adapterDelegate.isForViewType(position, adapterDataSource)) {
                adapterDelegate.convert(position, baseViewHolder, adapterDataSource);
                return;
            }
        }
        throw new NullPointerException("No AdapterDelegate added that matches position=" + position + " in data source");
    }
}

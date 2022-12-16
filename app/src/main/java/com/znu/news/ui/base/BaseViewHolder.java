package com.znu.news.ui.base;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder<ITEM, IB extends ViewDataBinding> extends RecyclerView.ViewHolder {

    public IB itemBinding;

    public BaseViewHolder(IB itemBinding) {
        super(itemBinding.getRoot());
        this.itemBinding = itemBinding;
    }

    public abstract void bind(ITEM item);
}

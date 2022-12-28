package com.znu.news.ui.base;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

public abstract class BaseViewHolder<Item, IB extends ViewBinding> extends RecyclerView.ViewHolder {

    public IB itemBinding;

    public BaseViewHolder(IB itemBinding) {
        super(itemBinding.getRoot());
        this.itemBinding = itemBinding;
    }

    public abstract void bind(Item item);
}

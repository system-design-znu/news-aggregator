package com.znu.news.ui.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public abstract class BasePagedAdapter<ITEM> extends PagingDataAdapter<ITEM, BaseViewHolder<ITEM, ?>> {

    protected List<ITEM> items;
    protected LayoutInflater layoutInflater;

    public BasePagedAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback) {
        super(diffCallback);
    }

    protected abstract BaseViewHolder<ITEM, ?> initViewHolder(View view);

    protected abstract
    @LayoutRes
    int getLayoutId();

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        return initViewHolder(layoutInflater.inflate(getLayoutId(), parent, true));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<ITEM, ?> holder, int position) {
        holder.bind(getItem(position));
    }
}

package com.znu.news.ui.base;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<Item> extends RecyclerView.Adapter<BaseViewHolder<Item, ?>> {

    protected List<Item> items;
    protected LayoutInflater layoutInflater;

    public BaseAdapter() {
        items = new ArrayList<>();
    }

    public BaseAdapter(List<Item> items) {
        this.items = items;
    }

    protected abstract BaseViewHolder<Item, ?> initViewHolder(ViewGroup parent);

    @SuppressLint("NotifyDataSetChanged")
    public void submitData(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder<Item, ?> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        return initViewHolder(parent);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<Item, ?> holder, int position) {
        holder.bind(items.get(position));
    }
}

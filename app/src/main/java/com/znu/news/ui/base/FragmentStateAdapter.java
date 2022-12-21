package com.znu.news.ui.base;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import java.util.ArrayList;

public class FragmentStateAdapter extends androidx.viewpager2.adapter.FragmentStateAdapter {

    private final ArrayList<Fragment> fragments;

    public FragmentStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        fragments = new ArrayList<>();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

    @Override
    public boolean containsItem(long itemId) {
        Fragment fragment = fragments.get((int) itemId);
        return fragments.contains(fragment);
    }
}

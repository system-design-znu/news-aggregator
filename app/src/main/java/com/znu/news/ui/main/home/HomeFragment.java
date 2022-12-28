package com.znu.news.ui.main.home;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayoutMediator;
import com.znu.news.R;
import com.znu.news.databinding.FragmentHomeBinding;
import com.znu.news.di.GlideApp;
import com.znu.news.ui.base.BaseViewModelFragment;
import com.znu.news.ui.base.FragmentStateAdapter;
import com.znu.news.viewmodel.HomeViewModel;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class HomeFragment extends BaseViewModelFragment<FragmentHomeBinding, HomeViewModel> {


    public HomeFragment() {
    }

    @Override
    protected void initViewModel() {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    protected FragmentHomeBinding initViewBinding() {
        return FragmentHomeBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GlideApp.with(this)
                .load(R.drawable.image)
                .into(binding.profileImageView);

        setUpViewPager();
    }

    private void setUpViewPager() {
        FragmentStateAdapter fragmentStateAdapter
                = new FragmentStateAdapter(getChildFragmentManager(), getLifecycle());

        fragmentStateAdapter.addFragment(new HomeMainFragment());
        fragmentStateAdapter.addFragment(new HomeFavoriteFragment());

        binding.homeViewPager.setAdapter(fragmentStateAdapter);

        final String[] homeTabs = activity.getResources().getStringArray(R.array.home_tabs);
        new TabLayoutMediator(binding.homeTabLayout, binding.homeViewPager, false, true,
                (tab, position) -> tab.setText(homeTabs[position])).attach();
    }
}
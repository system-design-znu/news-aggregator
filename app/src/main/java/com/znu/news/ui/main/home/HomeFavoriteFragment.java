package com.znu.news.ui.main.home;

import androidx.lifecycle.ViewModelProvider;

import com.znu.news.R;
import com.znu.news.databinding.FragmentHomeFavoriteBinding;
import com.znu.news.ui.base.BaseViewModelFragment;
import com.znu.news.viewmodel.HomeViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFavoriteFragment extends BaseViewModelFragment<FragmentHomeFavoriteBinding, HomeViewModel> {


    public HomeFavoriteFragment() {
    }

    @Override
    protected void initViewModel() {
        viewModel = new ViewModelProvider(requireParentFragment()).get(HomeViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_favorite;
    }
}
package com.znu.news.ui.main.search;

import com.znu.news.R;
import com.znu.news.databinding.FragmentSearchBinding;
import com.znu.news.ui.base.BaseViewModelFragment;
import com.znu.news.viewmodel.SearchViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SearchFragment extends BaseViewModelFragment<FragmentSearchBinding, SearchViewModel> {

    public SearchFragment() {
    }

    @Override
    protected void initViewModel() {

    }

    @Override
    protected FragmentSearchBinding initViewBinding() {
        return FragmentSearchBinding.inflate(getLayoutInflater());
    }
}
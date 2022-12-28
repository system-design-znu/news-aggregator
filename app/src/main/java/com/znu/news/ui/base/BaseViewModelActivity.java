package com.znu.news.ui.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

public abstract class BaseViewModelActivity<B extends ViewBinding, VM extends BaseViewModel> extends BaseActivity<B> {

    protected VM viewModel;


    protected abstract void initViewModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
    }
}

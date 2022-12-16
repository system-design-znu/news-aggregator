package com.znu.news.ui.base;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

public abstract class BaseViewModelActivity<B extends ViewDataBinding, VM extends BaseViewModel> extends BaseActivity<B> {

    protected VM viewModel;


    protected abstract void initViewModel();

    public abstract
    @LayoutRes
    int getLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
    }
}

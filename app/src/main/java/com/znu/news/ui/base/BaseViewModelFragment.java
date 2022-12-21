package com.znu.news.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ViewDataBinding;

public abstract class BaseViewModelFragment<B extends ViewDataBinding, VM extends BaseViewModel> extends BaseFragment<B> {

    protected VM viewModel;

    protected abstract void initViewModel();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViewModel();
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}

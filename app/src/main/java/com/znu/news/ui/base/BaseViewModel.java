package com.znu.news.ui.base;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.ViewModel;

import com.znu.news.utils.rx.SchedulerProvider;

public abstract class BaseViewModel extends ViewModel {

    protected final SchedulerProvider schedulerProvider;

    public BaseViewModel(SchedulerProvider schedulerProvider) {
        this.schedulerProvider = schedulerProvider;
    }
}

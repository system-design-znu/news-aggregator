package com.znu.news.viewmodel;

import android.app.Application;

import com.znu.news.ui.base.BaseViewModel;
import com.znu.news.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SettingViewModel extends BaseViewModel {

    @Inject
    public SettingViewModel(Application application
            , SchedulerProvider schedulerProvider) {
        super(application, schedulerProvider);
    }
}

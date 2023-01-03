package com.znu.news.viewmodel;

import com.znu.news.ui.base.BaseViewModel;
import com.znu.news.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AuthViewModel extends BaseViewModel
{
    @Inject
    public AuthViewModel(SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
    }
}

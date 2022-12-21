package com.znu.news.viewmodel;

import com.znu.news.ui.base.BaseViewModel;
import com.znu.news.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SearchViewModel extends BaseViewModel {

    @Inject
    public SearchViewModel(SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
    }
}

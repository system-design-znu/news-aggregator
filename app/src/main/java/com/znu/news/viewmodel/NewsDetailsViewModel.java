package com.znu.news.viewmodel;

import android.app.Application;

import com.znu.news.data.repo.NewsRepository;
import com.znu.news.ui.base.BaseViewModel;
import com.znu.news.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class NewsDetailsViewModel extends BaseViewModel {

    private final NewsRepository newsRepository;

    @Inject
    public NewsDetailsViewModel(Application application
            , SchedulerProvider schedulerProvider
            , NewsRepository newsRepository) {
        super(application, schedulerProvider);
        this.newsRepository = newsRepository;
    }
}

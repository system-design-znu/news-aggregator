package com.znu.news.viewmodel;

import com.znu.news.data.repo.NewsRepository;
import com.znu.news.ui.base.BaseViewModel;
import com.znu.news.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class NewsViewModel extends BaseViewModel {

    private NewsRepository newsRepository;

    @Inject
    public NewsViewModel(SchedulerProvider schedulerProvider, NewsRepository newsRepository) {
        super(schedulerProvider);
        this.newsRepository = newsRepository;
    }
}

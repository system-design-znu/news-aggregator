package com.znu.news.viewmodel;

import com.znu.news.data.repo.NewsRepository;
import com.znu.news.ui.base.BaseViewModel;
import com.znu.news.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class NewsDetailsViewModel extends BaseViewModel {

    private final NewsRepository newsRepository;

    @Inject
    public NewsDetailsViewModel(SchedulerProvider schedulerProvider,
                                NewsRepository newsRepository) {
        super(schedulerProvider);
        this.newsRepository = newsRepository;
    }
}

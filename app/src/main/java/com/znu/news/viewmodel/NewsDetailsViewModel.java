package com.znu.news.viewmodel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import com.znu.news.data.remote.CallbackWrapper;
import com.znu.news.data.repo.NewsRepository;
import com.znu.news.model.Error;
import com.znu.news.model.News;
import com.znu.news.model.NewsDetails;
import com.znu.news.model.Resource;
import com.znu.news.ui.base.BaseViewModel;
import com.znu.news.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class NewsDetailsViewModel extends BaseViewModel {

    private final NewsRepository newsRepository;
    private final MutableLiveData<News> newsMutableLiveData;
    private final MutableLiveData<Resource<NewsDetails>> newsDetails;
    private SavedStateHandle state;

    @Inject
    public NewsDetailsViewModel(
            SavedStateHandle savedStateHandle
            , Application application
            , SchedulerProvider schedulerProvider
            , NewsRepository newsRepository) {
        super(application, schedulerProvider);
        this.state = savedStateHandle;
        this.newsRepository = newsRepository;

        newsMutableLiveData = new MutableLiveData<>();
        newsDetails = new MutableLiveData<>();

        loadData();
    }

    private void loadData() {
        newsDetails.setValue(Resource.loading());

        int newsId = (state.contains("newsId")) ? state.get("newsId") : -1;

        newsRepository.getNewsDetails(newsId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new CallbackWrapper<NewsDetails, Error>() {
                    @Override
                    protected void onComplete(NewsDetails news) {
                        newsDetails.setValue(Resource.success(news));
                    }

                    @Override
                    protected void onFailure(Error e) {
                        newsDetails.setValue(Resource.error(e));
                    }
                });
    }

    public void setNews(News news) {
        newsMutableLiveData.setValue(news);
    }

    public MutableLiveData<News> observeNews() {
        return newsMutableLiveData;
    }

    public MutableLiveData<Resource<NewsDetails>> observeNewsDetails() {
        return newsDetails;
    }
}

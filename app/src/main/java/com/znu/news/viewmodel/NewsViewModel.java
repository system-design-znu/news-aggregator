package com.znu.news.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.znu.news.data.remote.CallbackWrapper;
import com.znu.news.data.remote.model.NewsResponse;
import com.znu.news.data.repo.NewsRepository;
import com.znu.news.model.Error;
import com.znu.news.model.Resource;
import com.znu.news.ui.base.BaseViewModel;
import com.znu.news.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.disposables.CompositeDisposable;

@HiltViewModel
public class NewsViewModel extends BaseViewModel {

    private NewsRepository newsRepository;

    private MutableLiveData<Resource<NewsResponse>> trendingNews;

    @Inject
    public NewsViewModel(SchedulerProvider schedulerProvider, NewsRepository newsRepository) {
        super(schedulerProvider);
        this.newsRepository = newsRepository;

        trendingNews = new MutableLiveData<>();
        fetchTrendingNews();
    }


    private void fetchTrendingNews() {
        trendingNews.setValue(Resource.loading());
        newsRepository.getTrendingNews()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new CallbackWrapper<NewsResponse, Error>() {
                    @Override
                    protected void onComplete(Resource<NewsResponse> response) {
                        trendingNews.setValue(response);
                    }

                    @Override
                    protected void onFailure(Resource<NewsResponse> error) {
                        trendingNews.setValue(error);
                    }
                });
    }

    public MutableLiveData<Resource<NewsResponse>> observeTrendingNews() {
        return trendingNews;
    }
}

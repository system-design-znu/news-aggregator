package com.znu.news.viewmodel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.znu.news.data.remote.CallbackWrapper;
import com.znu.news.data.repo.NewsRepository;
import com.znu.news.data.repo.UserRepository;
import com.znu.news.model.Error;
import com.znu.news.model.ErrorType;
import com.znu.news.model.News;
import com.znu.news.model.Resource;
import com.znu.news.ui.base.BaseViewModel;
import com.znu.news.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HomeViewModel extends BaseViewModel {

    private final MutableLiveData<Resource<List<News>>> trendingNews;
    private final MutableLiveData<Resource<List<News>>> popularNews;
    private final MutableLiveData<Resource<List<News>>> importantNews;
    private final MutableLiveData<Error> error;
    private final UserRepository userRepository;
    private final NewsRepository newsRepository;


    @Inject
    public HomeViewModel(Application application
            , SchedulerProvider schedulerProvider
            , UserRepository userRepository
            , NewsRepository newsRepository) {
        super(application, schedulerProvider);
        this.userRepository = userRepository;
        this.newsRepository = newsRepository;

        trendingNews = new MutableLiveData<>();
        popularNews = new MutableLiveData<>();
        importantNews = new MutableLiveData<>();
        error = new MutableLiveData<>();

        loadData();
    }

    public void loadData() {
        if (isConnected()) {
            error.setValue(null);
            fetchTrendingNews();
            fetchPopularNews();
            fetchImportantNews();
        } else {
            error.setValue(new Error.RemoteServiceError(ErrorType.Connection));
        }
    }

    private void fetchImportantNews() {
        importantNews.setValue(Resource.loading());
        newsRepository.getImportantNewsNews()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new CallbackWrapper<List<News>, Error>() {
                    @Override
                    protected void onComplete(Resource<List<News>> response) {
                        importantNews.setValue(response);
                    }

                    @Override
                    protected void onFailure(Resource<List<News>> response) {
                        if (!compareError(response.error))
                            error.setValue(response.error);
                    }
                });
    }

    private void fetchPopularNews() {
        popularNews.setValue(Resource.loading());
        newsRepository.getPopularNews()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new CallbackWrapper<List<News>, Error>() {
                    @Override
                    protected void onComplete(Resource<List<News>> response) {
                        popularNews.setValue(response);
                    }

                    @Override
                    protected void onFailure(Resource<List<News>> response) {
                        if (!compareError(response.error))
                            error.setValue(response.error);
                    }
                });
    }

    private void fetchTrendingNews() {
        trendingNews.setValue(Resource.loading());
        newsRepository.getTrendingNews()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new CallbackWrapper<List<News>, Error>() {
                    @Override
                    protected void onComplete(Resource<List<News>> response) {
                        trendingNews.setValue(response);
                    }

                    @Override
                    protected void onFailure(Resource<List<News>> response) {
                        if (!compareError(response.error))
                            error.setValue(response.error);
                    }
                });
    }

    private boolean compareError(Error error) {
        return this.error.getValue() != null
                && this.error.getValue().errorType == error.errorType;
    }

    public MutableLiveData<Resource<List<News>>> observeTrendingNews() {
        return trendingNews;
    }

    public MutableLiveData<Resource<List<News>>> observePopularNews() {
        return popularNews;
    }

    public MutableLiveData<Resource<List<News>>> observeImportantNews() {
        return importantNews;
    }

    public MutableLiveData<Error> observeError() {
        return error;
    }
}

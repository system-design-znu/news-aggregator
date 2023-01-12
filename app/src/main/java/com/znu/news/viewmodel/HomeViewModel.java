package com.znu.news.viewmodel;

import android.annotation.SuppressLint;
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

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Single;

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
            trendingNews.setValue(Resource.loading());
            popularNews.setValue(Resource.loading());
            importantNews.setValue(Resource.loading());
            fetchMergeData();
        } else {
            error.setValue(new Error.RemoteServiceError(ErrorType.Connection));
        }
    }

    private void fetchMergeData() {
        Single.zip(
                        newsRepository.getTrendingNews().subscribeOn(schedulerProvider.io()),
                        newsRepository.getPopularNews().subscribeOn(schedulerProvider.io()),
                        newsRepository.getImportantNews().subscribeOn(schedulerProvider.io()),
                        Arrays::asList
                )
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new CallbackWrapper<List<List<News>>, Error>() {
                    @Override
                    protected void onComplete(List<List<News>> lists) {
                        trendingNews.setValue(Resource.success(lists.get(0)));
                        popularNews.setValue(Resource.success(lists.get(1)));
                        importantNews.setValue(Resource.success(lists.get(2)));
                    }

                    @Override
                    protected void onFailure(Error e) {
                        if (e.isNotEqual(error.getValue()))
                            error.setValue(e);
                    }
                });
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

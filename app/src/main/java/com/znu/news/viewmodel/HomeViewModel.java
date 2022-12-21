package com.znu.news.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.znu.news.data.remote.CallbackWrapper;
import com.znu.news.data.repo.NewsRepository;
import com.znu.news.data.repo.UserRepository;
import com.znu.news.model.Error;
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
    private final UserRepository userRepository;
    private final NewsRepository newsRepository;


    @Inject
    public HomeViewModel(SchedulerProvider schedulerProvider
            , UserRepository userRepository
            , NewsRepository newsRepository) {
        super(schedulerProvider);
        this.userRepository = userRepository;
        this.newsRepository = newsRepository;

        trendingNews = new MutableLiveData<>();
        popularNews = new MutableLiveData<>();
        importantNews = new MutableLiveData<>();

        loadData();
    }

    public void loadData() {
        fetchTrendingNews();
        fetchPopularNews();
        fetchImportantNews();
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
}

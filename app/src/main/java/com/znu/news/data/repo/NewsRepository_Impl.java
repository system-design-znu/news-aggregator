package com.znu.news.data.repo;

import com.znu.news.data.remote.model.NewsResponse;
import com.znu.news.data.remote.services.NewsService;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

public class NewsRepository_Impl implements NewsRepository {

    private NewsService newsService;

    @Inject
    public NewsRepository_Impl(NewsService newsService) {
        this.newsService = newsService;
    }

    @Override
    public Single<NewsResponse> getTrendingNews() {
        return newsService.getTrendingNews();
    }
}

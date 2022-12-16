package com.znu.news.data.repo;

import com.znu.news.data.remote.services.NewsService;

import javax.inject.Inject;

//TODO: access token update
public class NewsRepository_Impl implements NewsRepository {

    private NewsService newsService;

    @Inject
    public NewsRepository_Impl(NewsService newsService) {
        this.newsService = newsService;
    }
}

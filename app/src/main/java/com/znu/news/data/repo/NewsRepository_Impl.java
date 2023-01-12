package com.znu.news.data.repo;

import com.znu.news.data.remote.response.NewsDtoMapper;
import com.znu.news.data.remote.services.NewsService;
import com.znu.news.model.News;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class NewsRepository_Impl implements NewsRepository {

    private final NewsService newsService;
    private final NewsDtoMapper newsDtoMapper;

    @Inject
    public NewsRepository_Impl(NewsService newsService, NewsDtoMapper newsDtoMapper) {
        this.newsService = newsService;
        this.newsDtoMapper = newsDtoMapper;
    }

    @Override
    public Single<List<News>> getTrendingNews() {
        return newsService.getTrendingNews().map(newsResponse -> newsDtoMapper.toDomainList(newsResponse.getNewsDtoList()));
    }

    @Override
    public Single<List<News>> getPopularNews() {
        return newsService.getPopularNews().map(newsResponse -> newsDtoMapper.toDomainList(newsResponse.getNewsDtoList()));
    }

    @Override
    public Single<List<News>> getImportantNews() {
        return newsService.getImportantNews().map(newsResponse -> newsDtoMapper.toDomainList(newsResponse.getNewsDtoList()));
    }
}

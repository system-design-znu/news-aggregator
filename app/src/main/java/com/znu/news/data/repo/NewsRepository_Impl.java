package com.znu.news.data.repo;

import com.znu.news.data.remote.response.NewsDetailsDtoMapper;
import com.znu.news.data.remote.response.NewsDto;
import com.znu.news.data.remote.response.NewsDtoMapper;
import com.znu.news.data.remote.services.NewsService;
import com.znu.news.model.News;
import com.znu.news.model.NewsDetails;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class NewsRepository_Impl implements NewsRepository {

    private final NewsService newsService;
    private final NewsDtoMapper newsDtoMapper;
    private final NewsDetailsDtoMapper newsDetailsDtoMapper;

    @Inject
    public NewsRepository_Impl(
            NewsService newsService
            , NewsDtoMapper newsDtoMapper
            , NewsDetailsDtoMapper newsDetailsDtoMapper
    ) {
        this.newsService = newsService;
        this.newsDtoMapper = newsDtoMapper;
        this.newsDetailsDtoMapper = newsDetailsDtoMapper;
    }

    @Override
    public Single<List<News>> getTrendingNews() {
        return newsService.getTrendingNews().map(newsResponse -> {
            List<NewsDto> newsDtoList
                    = Arrays.asList(
                    newsResponse.getContent_1()
                    , newsResponse.getContent_3()
                    , newsResponse.getContent_4()
                    , newsResponse.getContent_5()
                    , newsResponse.getContent_6()
                    , newsResponse.getContent_7()
                    , newsResponse.getContent_8()
                    , newsResponse.getContent_9()
                    , newsResponse.getContent_10()
            );
            return newsDtoMapper.toDomainList(newsDtoList);
        });
    }

    @Override
    public Single<List<News>> getPopularNews() {
        return newsService.getPopularNews().map(newsResponse -> {
            List<NewsDto> newsDtoList
                    = Arrays.asList(
                    newsResponse.getContent_1()
                    , newsResponse.getContent_3()
                    , newsResponse.getContent_4()
                    , newsResponse.getContent_5()
                    , newsResponse.getContent_6()
                    , newsResponse.getContent_7()
                    , newsResponse.getContent_8()
                    , newsResponse.getContent_9()
                    , newsResponse.getContent_10()
            );
            return newsDtoMapper.toDomainList(newsDtoList);
        });
    }

    @Override
    public Single<List<News>> getImportantNews() {
        return newsService.getImportantNews().map(newsResponse -> {
            List<NewsDto> newsDtoList
                    = Arrays.asList(
                    newsResponse.getContent_1()
                    , newsResponse.getContent_3()
                    , newsResponse.getContent_4()
                    , newsResponse.getContent_5()
                    , newsResponse.getContent_6()
                    , newsResponse.getContent_7()
                    , newsResponse.getContent_8()
                    , newsResponse.getContent_9()
                    , newsResponse.getContent_10()
            );
            return newsDtoMapper.toDomainList(newsDtoList);
        });
    }

    @Override
    public Single<NewsDetails> getNewsDetails(int newsId) {
        return newsService.getNewsDetails(newsId).map(newsDetailsDtoMapper::mapToDomainModel);
    }
}

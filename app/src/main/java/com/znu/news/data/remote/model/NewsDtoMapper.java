package com.znu.news.data.remote.model;

import com.znu.news.model.DomainMapper;
import com.znu.news.model.News;

import java.util.ArrayList;
import java.util.List;

public class NewsDtoMapper implements DomainMapper<NewsDto, News> {


    @Override
    public News mapToDomainModel(NewsDto model) {
        return new News();
    }

    @Override
    public NewsDto mapFromDomainModel(News news) {
        return new NewsDto();
    }

    public List<News> toDomainList(List<NewsDto> initial) {
        List<News> newsList = new ArrayList<>();
        for (NewsDto newsDto: initial) {
            mapToDomainModel(newsDto);
        }
        return newsList;
    }

    public List<NewsDto> fromDomainList(List<News> initial) {
        List<NewsDto> newsDtoList = new ArrayList<>();
        for (News news: initial) {
            mapFromDomainModel(news);
        }
        return newsDtoList;
    }
}

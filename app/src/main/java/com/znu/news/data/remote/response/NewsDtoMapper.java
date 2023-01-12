package com.znu.news.data.remote.response;

import com.znu.news.model.DomainMapper;
import com.znu.news.model.News;

import java.util.ArrayList;
import java.util.List;

public class NewsDtoMapper implements DomainMapper<NewsDto, News> {


    @Override
    public News mapToDomainModel(NewsDto newsDto) {
        return new News(
                newsDto.getTitle(),
                newsDto.getPubDate(),
                newsDto.getAuthor(),
                newsDto.getEnclosure().getLink(),
                newsDto.getCategories().get(0)
        );
    }

    @Override
    public NewsDto mapFromDomainModel(News news) {
        return new NewsDto();
    }

    public List<News> toDomainList(List<NewsDto> initial) {
        List<News> newsList = new ArrayList<>();
        for (NewsDto newsDto : initial) {
            newsList.add(mapToDomainModel(newsDto));
        }
        return newsList;
    }

    public List<NewsDto> fromDomainList(List<News> initial) {
        List<NewsDto> newsList = new ArrayList<>();
        for (News news : initial) {
            newsList.add(mapFromDomainModel(news));
        }
        return newsList;
    }
}

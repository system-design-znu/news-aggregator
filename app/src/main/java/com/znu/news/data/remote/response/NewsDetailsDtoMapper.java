package com.znu.news.data.remote.response;

import com.znu.news.model.DomainMapper;
import com.znu.news.model.NewsDetails;

public class NewsDetailsDtoMapper implements DomainMapper<NewsDetailsDto, NewsDetails> {
    @Override
    public NewsDetails mapToDomainModel(NewsDetailsDto model) {
        return new NewsDetails();
    }

    @Override
    public NewsDetailsDto mapFromDomainModel(NewsDetails newsDetails) {
        return new NewsDetailsDto();
    }
}

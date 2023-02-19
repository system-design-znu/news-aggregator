package com.znu.news.data.remote.services;

import com.znu.news.data.remote.response.NewsDetailsDto;
import com.znu.news.data.remote.response.NewsResponse;

import io.reactivex.Single;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NewsService {

    @POST("/api/v0/news/irna")
    Single<NewsResponse> getTrendingNews();

    @POST("/api/v0/news/irna")
    Single<NewsResponse> getPopularNews();

    @POST("/api/v0/news/irna")
    Single<NewsResponse> getImportantNews();

    @POST("/api/v0/news/irna/{newsId}")
    Single<NewsDetailsDto> getNewsDetails(@Path("newsId") int newsId);
}

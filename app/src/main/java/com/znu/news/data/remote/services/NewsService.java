package com.znu.news.data.remote.services;

import com.znu.news.data.remote.response.NewsDetailsDto;
import com.znu.news.data.remote.response.NewsDto;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NewsService {

    @GET("top_news/6")
    Single<List<NewsDto>> getTrendingNews();

    @GET("top_news/6")
    Single<List<NewsDto>> getPopularNews();

    @GET("top_news/6")
    Single<List<NewsDto>> getImportantNews();

    @POST("api/v0/news/irna/{newsId}")
    Single<NewsDetailsDto> getNewsDetails(@Path("newsId") int newsId);
}

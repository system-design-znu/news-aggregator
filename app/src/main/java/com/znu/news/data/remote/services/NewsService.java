package com.znu.news.data.remote.services;

import com.znu.news.data.remote.model.NewsDto;
import com.znu.news.data.remote.model.NewsResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface NewsService {

    @GET("news")
    Single<List<NewsDto>> getTrendingNews();

    @GET("news")
    Single<List<NewsDto>> getPopularNews();

    @GET("news")
    Single<List<NewsDto>> getImportantNews();
}

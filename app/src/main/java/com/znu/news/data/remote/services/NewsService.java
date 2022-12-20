package com.znu.news.data.remote.services;

import com.znu.news.data.remote.model.NewsResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface NewsService {

    @GET("news")
    Single<NewsResponse> getTrendingNews();
}

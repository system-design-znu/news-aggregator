package com.znu.news.data.remote.services;

import com.znu.news.data.remote.model.NewsResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface NewsService {

    @GET("https://api.rss2json.com/v1/api.json?rss_url=https://www.irna.ir/rss/")
    Single<NewsResponse> getTrendingNews();

    @GET("https://api.rss2json.com/v1/api.json?rss_url=https://www.irna.ir/rss/")

    Single<NewsResponse> getPopularNews();

    @GET("https://api.rss2json.com/v1/api.json?rss_url=https://www.irna.ir/rss/")
    Single<NewsResponse> getImportantNews();
}

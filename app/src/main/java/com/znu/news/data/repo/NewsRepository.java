package com.znu.news.data.repo;

import com.znu.news.data.remote.model.NewsResponse;

import io.reactivex.Single;

public interface NewsRepository {

    Single<NewsResponse> getTrendingNews();
}

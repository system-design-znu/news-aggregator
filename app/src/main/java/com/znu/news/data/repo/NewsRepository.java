package com.znu.news.data.repo;

import com.znu.news.model.News;

import java.util.List;

import io.reactivex.Single;

public interface NewsRepository {

    Single<List<News>> getTrendingNews();

    Single<List<News>> getPopularNews();

    Single<List<News>> getImportantNews();
}

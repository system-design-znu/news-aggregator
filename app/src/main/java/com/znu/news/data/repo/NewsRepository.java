package com.znu.news.data.repo;

import com.znu.news.model.News;
import com.znu.news.model.NewsDetails;

import java.util.List;

import io.reactivex.Single;

public interface NewsRepository {

    Single<List<News>> getTrendingNews();

    Single<List<News>> getPopularNews();

    Single<List<News>> getImportantNews();

    Single<NewsDetails> getNewsDetails(int newsId);
}

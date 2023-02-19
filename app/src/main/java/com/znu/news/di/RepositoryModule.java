package com.znu.news.di;

import com.znu.news.data.local.db.dao.UserDao;
import com.znu.news.data.local.db.entity.UserEntityMapper;
import com.znu.news.data.local.prefs.AppPreferencesHelper;
import com.znu.news.data.remote.response.NewsDetailsDtoMapper;
import com.znu.news.data.remote.response.NewsDtoMapper;
import com.znu.news.data.remote.services.NewsService;
import com.znu.news.data.remote.services.UserService;
import com.znu.news.data.repo.NewsRepository;
import com.znu.news.data.repo.NewsRepository_Impl;
import com.znu.news.data.repo.UserRepository;
import com.znu.news.data.repo.UserRepository_Impl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class RepositoryModule {

    @Provides
    @Singleton
    NewsDtoMapper provideNewsDtoMapper() {
        return new NewsDtoMapper();
    }

    @Provides
    @Singleton
    NewsDetailsDtoMapper provideNewsDetailsDtoMapper() {
        return new NewsDetailsDtoMapper();
    }

    @Provides
    @Singleton
    UserEntityMapper provideUserEntityMapper() {
        return new UserEntityMapper();
    }

    @Provides
    @Singleton
    UserRepository provideUserRepository(UserService userService
            , UserDao userDao
            , UserEntityMapper userEntityMapper
            , AppPreferencesHelper appPreferencesHelper) {
        return new UserRepository_Impl(
                userService
                , userDao
                , userEntityMapper
                , appPreferencesHelper
        );
    }

    @Provides
    @Singleton
    NewsRepository provideNewsRepository(
            NewsService newsService
            , NewsDtoMapper newsDtoMapper
            , NewsDetailsDtoMapper newsDetailsDtoMapper
    ) {
        return new NewsRepository_Impl(newsService, newsDtoMapper, newsDetailsDtoMapper);
    }
}

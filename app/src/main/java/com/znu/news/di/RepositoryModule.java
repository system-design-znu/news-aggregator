package com.znu.news.di;

import com.znu.news.data.local.prefs.AppPreferencesHelper;
import com.znu.news.data.remote.model.NewsDtoMapper;
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
    UserRepository provideUserRepository(UserService userService) {
        return new UserRepository_Impl(userService);
    }

    @Provides
    @Singleton
    NewsRepository provideNewsRepository(NewsService newsService, NewsDtoMapper newsDtoMapper) {
        return new NewsRepository_Impl(newsService, newsDtoMapper);
    }
}

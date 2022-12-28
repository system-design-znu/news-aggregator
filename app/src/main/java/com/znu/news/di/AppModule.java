package com.znu.news.di;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.znu.news.data.local.prefs.AppPreferencesHelper;
import com.znu.news.utils.SessionManager;
import com.znu.news.utils.rx.AppSchedulerProvider;
import com.znu.news.utils.rx.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @Singleton
    SessionManager provideSessionManager(AppPreferencesHelper appPreferencesHelper) {
        return new SessionManager(appPreferencesHelper);
    }

}

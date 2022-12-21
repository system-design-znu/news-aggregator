package com.znu.news.di;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.znu.news.BuildConfig;
import com.znu.news.data.local.prefs.AppPreferencesHelper;
import com.znu.news.data.remote.HeaderInterceptorHelper;
import com.znu.news.data.remote.services.NewsService;
import com.znu.news.data.remote.services.UserService;
import com.znu.news.data.repo.NewsRepository;
import com.znu.news.data.repo.NewsRepository_Impl;
import com.znu.news.data.repo.UserRepository;
import com.znu.news.data.repo.UserRepository_Impl;
import com.znu.news.utils.rx.AppSchedulerProvider;
import com.znu.news.utils.rx.SchedulerProvider;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }


    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }
}

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
    @Named("BASE_URL")
    String provideBaseUrl() {
        return BuildConfig.BASE_URL;
    }

    @Provides
    @Named("PREF_NAME")
    String providePreferenceName() {
        return BuildConfig.PREF_NAME;
    }

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
    AppPreferencesHelper provideAppPreferencesHelper(Context context, @Named("PREF_NAME") String preferenceName) {
        return new AppPreferencesHelper(
                context,
                preferenceName
        );
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @Singleton
    HeaderInterceptorHelper provideHeaderInterceptorHelper(AppPreferencesHelper appPreferencesHelper) {
        return new HeaderInterceptorHelper(appPreferencesHelper);
    }

    @Provides
    @Singleton
    ConnectionSpec provideConnectionSpec() {
        return new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_0)
                .allEnabledCipherSuites()
                .build();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(HeaderInterceptorHelper headerInterceptorHelper, ConnectionSpec spec) {
        return new OkHttpClient
                .Builder()
                .connectionSpecs(Collections.singletonList(spec))
                .addInterceptor(headerInterceptorHelper)
                .callTimeout(40, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient, @Named("BASE_URL") String BASE_URL) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    UserService provideUserService(Retrofit retrofit) {
        return retrofit.create(UserService.class);
    }

    @Provides
    @Singleton
    NewsService provideNewsService(Retrofit retrofit) {
        return retrofit.create(NewsService.class);
    }

    @Provides
    @Singleton
    UserRepository provideUserRepository(UserService userService, AppPreferencesHelper appPreferencesHelper) {
        return new UserRepository_Impl(userService, appPreferencesHelper);
    }

    @Provides
    @Singleton
    NewsRepository provideNewsRepository(NewsService newsService) {
        return new NewsRepository_Impl(newsService);
    }
}

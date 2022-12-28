package com.znu.news.di;

import com.google.gson.Gson;
import com.znu.news.BuildConfig;
import com.znu.news.data.local.prefs.AppPreferencesHelper;
import com.znu.news.data.remote.HeaderInterceptorHelper;
import com.znu.news.data.remote.services.NewsService;
import com.znu.news.data.remote.services.UserService;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {

    @Provides
    @Named("BASE_URL")
    String provideBaseUrl() {
        return BuildConfig.BASE_URL;
    }

    @Provides
    @Singleton
    HeaderInterceptorHelper provideHeaderInterceptorHelper(AppPreferencesHelper appPreferencesHelper) {
        return new HeaderInterceptorHelper(appPreferencesHelper);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(HeaderInterceptorHelper headerInterceptorHelper) {
        return new OkHttpClient
                .Builder()
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
}

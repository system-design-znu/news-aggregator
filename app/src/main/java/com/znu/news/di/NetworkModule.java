package com.znu.news.di;

import com.znu.news.BuildConfig;
import com.znu.news.data.local.prefs.AppPreferencesHelper;
import com.znu.news.data.remote.AccessTokenInterceptor;
import com.znu.news.data.remote.AuthAuthenticator;
import com.znu.news.data.remote.RefreshTokenInterceptor;
import com.znu.news.data.remote.services.NewsService;
import com.znu.news.data.remote.services.RefreshTokenService;
import com.znu.news.data.remote.services.UserService;
import com.znu.news.utils.SessionManager;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
    AccessTokenInterceptor provideAccessTokenInterceptor(AppPreferencesHelper appPreferencesHelper) {
        return new AccessTokenInterceptor(appPreferencesHelper);
    }

    @Provides
    @Singleton
    AuthAuthenticator provideAuthAuthenticator(AppPreferencesHelper appPreferencesHelper
            , RefreshTokenService refreshTokenService
            , SessionManager sessionManager) {
        return new AuthAuthenticator(appPreferencesHelper, refreshTokenService, sessionManager);
    }

    @Named("PublicClient")
    @Provides
    @Singleton
    OkHttpClient provideOkHttpPublicClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient
                .Builder()
                .addInterceptor(httpLoggingInterceptor)
                .callTimeout(40, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    @Named("RefreshClient")
    @Provides
    @Singleton
    OkHttpClient provideOkHttpRefreshClient(RefreshTokenInterceptor refreshTokenInterceptor) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient
                .Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(refreshTokenInterceptor)
                .callTimeout(40, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    @Named("AuthenticatedClient")
    @Provides
    @Singleton
    OkHttpClient provideOkHttpAuthenticatedClient(AccessTokenInterceptor accessTokenInterceptor, AuthAuthenticator authAuthenticator) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient
                .Builder()
                .authenticator(authAuthenticator)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(accessTokenInterceptor)
                .callTimeout(40, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    @Named("PublicRetrofit")
    @Provides
    @Singleton
    Retrofit providePublicRetrofit(@Named("PublicClient") OkHttpClient okHttpClient, @Named("BASE_URL") String BASE_URL) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build();
    }

    @Named("RefreshTokenRetrofit")
    @Provides
    @Singleton
    Retrofit provideRefreshTokenRetrofit(@Named("RefreshClient") OkHttpClient okHttpClient, @Named("BASE_URL") String BASE_URL) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build();
    }

    @Named("AuthenticatedRetrofit")
    @Provides
    @Singleton
    Retrofit provideAuthenticatedRetrofit(@Named("AuthenticatedClient") OkHttpClient okHttpClient, @Named("BASE_URL") String BASE_URL) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    UserService provideUserService(@Named("PublicRetrofit") Retrofit retrofit) {
        return retrofit.create(UserService.class);
    }

    @Provides
    @Singleton
    RefreshTokenService provideRefreshTokenService(@Named("RefreshTokenRetrofit") Retrofit retrofit) {
        return retrofit.create(RefreshTokenService.class);
    }

    @Provides
    @Singleton
    NewsService provideNewsService(@Named("AuthenticatedRetrofit") Retrofit retrofit) {
        return retrofit.create(NewsService.class);
    }
}

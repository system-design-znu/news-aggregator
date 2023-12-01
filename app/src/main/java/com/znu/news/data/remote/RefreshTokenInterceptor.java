package com.znu.news.data.remote;

import androidx.annotation.NonNull;

import com.znu.news.data.local.prefs.AppPreferencesHelper;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RefreshTokenInterceptor implements Interceptor {

    private final AppPreferencesHelper preferencesHelper;

    @Inject
    public RefreshTokenInterceptor(AppPreferencesHelper preferencesHelper) {
        this.preferencesHelper = preferencesHelper;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();
        requestBuilder.addHeader("Authorization", "Bearer " + preferencesHelper.getRefreshToken())
                .build();
        requestBuilder.addHeader("Content-Type", "application/json").build();
        return chain.proceed(requestBuilder.build());
    }
}

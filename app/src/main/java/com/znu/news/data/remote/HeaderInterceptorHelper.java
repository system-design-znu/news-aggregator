package com.znu.news.data.remote;

import androidx.annotation.NonNull;

import com.znu.news.data.local.prefs.AppPreferencesHelper;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptorHelper implements Interceptor {

    private AppPreferencesHelper preferencesHelper;

    @Inject
    public HeaderInterceptorHelper(AppPreferencesHelper preferencesHelper) {
        this.preferencesHelper = preferencesHelper;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();
        if (preferencesHelper.getAccessToken() != null) {
            requestBuilder.addHeader("Authorization", preferencesHelper.getAccessToken())
                    .build();
        }
        return chain.proceed(requestBuilder.build());
    }
}

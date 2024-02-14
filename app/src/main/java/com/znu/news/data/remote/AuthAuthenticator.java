package com.znu.news.data.remote;

import androidx.annotation.NonNull;

import com.znu.news.data.local.prefs.AppPreferencesHelper;
import com.znu.news.data.remote.response.UserTokenDto;
import com.znu.news.data.remote.services.RefreshTokenService;
import com.znu.news.utils.SessionManager;

import javax.inject.Inject;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class AuthAuthenticator implements Authenticator {

    private final AppPreferencesHelper appPreferencesHelper;
    private final RefreshTokenService refreshTokenService;
    private final SessionManager sessionManager;

    @Inject
    public AuthAuthenticator(AppPreferencesHelper appPreferencesHelper,
                             RefreshTokenService refreshTokenService,
                             SessionManager sessionManager) {
        this.appPreferencesHelper = appPreferencesHelper;
        this.refreshTokenService = refreshTokenService;
        this.sessionManager = sessionManager;
    }

    @Override
    public Request authenticate(Route route, @NonNull Response response) {
        synchronized (this) {
            String refreshToken = appPreferencesHelper.getRefreshToken();
            retrofit2.Response<UserTokenDto> newSessionResponse = refreshTokenService.refreshAccessToken();
            String token = null;

            if (newSessionResponse.isSuccessful() && newSessionResponse.body() != null) {
                UserTokenDto body = newSessionResponse.body();
                appPreferencesHelper.setAccessToken(body.getAccessToken());
                appPreferencesHelper.setRefreshToken(body.getRefreshToken());
                token = body.getAccessToken();
            }

            if (token != null) {
                return response.request()
                        .newBuilder()
                        .header("Authorization", "Bearer " + token)
                        .build();
            } else {
                sessionManager.logout();
                return null;
            }
        }
    }
}

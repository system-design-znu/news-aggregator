package com.znu.news.utils;

import static com.znu.news.utils.SessionManager.AuthStatus.LOGGED_IN;
import static com.znu.news.utils.SessionManager.AuthStatus.LOGGED_OUT;

import androidx.lifecycle.MutableLiveData;

import com.znu.news.data.local.prefs.AppPreferencesHelper;

import javax.inject.Inject;

public class SessionManager {

    private final AppPreferencesHelper appPreferencesHelper;
    public MutableLiveData<AuthStatus> authStatus;

    @Inject
    public SessionManager(AppPreferencesHelper appPreferencesHelper) {
        this.appPreferencesHelper = appPreferencesHelper;

        authStatus = new MutableLiveData<>();
    }

    public void logout() {
        appPreferencesHelper.clearAllTokens();
        authStatus.setValue(LOGGED_OUT);
    }

    public void login(String accessToken) {
        appPreferencesHelper.setAccessToken(accessToken);
//        appPreferencesHelper.setRefreshToken(accessToken);
        authStatus.setValue(LOGGED_IN);
    }

    public void login(String accessToken, String refreshToken) {
        appPreferencesHelper.setAccessToken(accessToken);
        appPreferencesHelper.setRefreshToken(refreshToken);
        authStatus.setValue(LOGGED_IN);
    }


    public enum AuthStatus {
        LOGGED_IN,
        LOGGED_OUT
    }
}

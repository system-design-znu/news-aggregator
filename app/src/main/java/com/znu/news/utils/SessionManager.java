package com.znu.news.utils;

import static com.znu.news.utils.SessionManager.Status.LOGGED_IN;
import static com.znu.news.utils.SessionManager.Status.LOGGED_OUT;

import androidx.lifecycle.MutableLiveData;

import com.znu.news.data.local.prefs.AppPreferencesHelper;

import javax.inject.Inject;

public class SessionManager {

    private final AppPreferencesHelper appPreferencesHelper;
    public MutableLiveData<Status> status;

    @Inject
    public SessionManager(AppPreferencesHelper appPreferencesHelper) {
        this.appPreferencesHelper = appPreferencesHelper;

        status = new MutableLiveData<>();

        refreshStatus();
    }

    private void refreshStatus() {
        status.setValue((appPreferencesHelper.getAccessToken() == null) ? LOGGED_OUT : checkAccessToken());
    }

    private Status checkAccessToken() {
        //TODO: check access token is expired or not
        return Status.LOGGED_IN;
    }

    public void logout() {
        appPreferencesHelper.setAccessToken(null);
        status.setValue(LOGGED_OUT);
    }

    public void login(String accessToken) {
        appPreferencesHelper.setAccessToken(accessToken);
        status.setValue(LOGGED_IN);
    }


    public enum Status {
        LOGGED_IN,
        LOGGED_OUT
    }
}

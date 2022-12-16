package com.znu.news.data.repo;

import com.znu.news.data.local.prefs.AppPreferencesHelper;
import com.znu.news.data.remote.services.UserService;

import javax.inject.Inject;

public class UserRepository_Impl implements UserRepository {

    private UserService userService;
    private AppPreferencesHelper appPreferencesHelper;

    @Inject
    public UserRepository_Impl(UserService userService, AppPreferencesHelper appPreferencesHelper) {
        this.userService = userService;
        this.appPreferencesHelper = appPreferencesHelper;
    }

    @Override
    public String getAccessToken() {
        return appPreferencesHelper.getAccessToken();
    }

    @Override
    public void setAccessToken(String accessToken) {
        appPreferencesHelper.setAccessToken(accessToken);
    }
}

package com.znu.news.data.repo;

import com.znu.news.data.local.prefs.AppPreferencesHelper;
import com.znu.news.data.remote.services.UserService;

import javax.inject.Inject;

public class UserRepository_Impl implements UserRepository {

    private UserService userService;

    @Inject
    public UserRepository_Impl(UserService userService) {
        this.userService = userService;
    }
}

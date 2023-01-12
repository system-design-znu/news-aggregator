package com.znu.news.data.repo;

import com.znu.news.data.local.db.dao.UserDao;
import com.znu.news.data.local.db.entity.UserEntityMapper;
import com.znu.news.data.local.prefs.AppPreferencesHelper;
import com.znu.news.data.remote.services.UserService;
import com.znu.news.model.User;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class UserRepository_Impl implements UserRepository {

    private UserService userService;
    private UserDao userDao;
    private UserEntityMapper userEntityMapper;
    private AppPreferencesHelper appPreferencesHelper;

    @Inject
    public UserRepository_Impl(UserService userService
            , UserDao userDao
            , UserEntityMapper userEntityMapper
            , AppPreferencesHelper appPreferencesHelper) {
        this.userService = userService;
        this.userDao = userDao;
        this.userEntityMapper = userEntityMapper;
        this.appPreferencesHelper = appPreferencesHelper;
    }

    @Override
    public int getNightMode() {
        return appPreferencesHelper.getNightMode();
    }

    @Override
    public void setNightMode(int nightMode) {
        appPreferencesHelper.setNightMode(nightMode);
    }

    @Override
    public Single<String> checkToken() {
        String token = appPreferencesHelper.getAccessToken();
        if (token != null)
            return Single.fromCallable(() -> token);
//            return userService.checkToken(token);
        else return Single.fromCallable(() -> "null");
    }

    @Override
    public Completable insertUser(User user) {
        return userDao.insert(userEntityMapper.mapFromDomainModel(user));
    }

    @Override
    public Completable deleteUser(User user) {
        return userDao.delete(userEntityMapper.mapFromDomainModel(user));
    }

    @Override
    public Completable updateUser(User user) {
        return userDao.update(userEntityMapper.mapFromDomainModel(user));
    }

    @Override
    public Flowable<User> getUser() {
        return userDao.getUser().map(user -> userEntityMapper.mapToDomainModel(user));
    }
}

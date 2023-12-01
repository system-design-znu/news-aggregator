package com.znu.news.data.repo;

import com.znu.news.data.local.db.dao.UserDao;
import com.znu.news.data.local.db.entity.UserEntityMapper;
import com.znu.news.data.local.prefs.AppPreferencesHelper;
import com.znu.news.data.remote.services.UserService;
import com.znu.news.model.User;
import com.znu.news.model.UserToken;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import okhttp3.RequestBody;

public class UserRepository_Impl implements UserRepository {

    private final UserService userService;
    private final UserDao userDao;
    private final UserEntityMapper userEntityMapper;
    private final AppPreferencesHelper appPreferencesHelper;

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
    public Single<UserToken> login(RequestBody body) {
        return userService.login(body).map(userTokenDto -> new UserToken(userTokenDto.getAccessToken(), userTokenDto.getRefreshToken()));
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

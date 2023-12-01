package com.znu.news.data.repo;

import com.znu.news.model.User;
import com.znu.news.model.UserToken;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import okhttp3.RequestBody;

public interface UserRepository {

    Single<UserToken> login(RequestBody body);

    int getNightMode();

    void setNightMode(int nightMode);

    Completable insertUser(User user);

    Completable deleteUser(User user);

    Completable updateUser(User user);

    Flowable<User> getUser();
}

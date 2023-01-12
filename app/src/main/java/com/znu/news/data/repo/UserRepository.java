package com.znu.news.data.repo;

import com.znu.news.model.User;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface UserRepository {

    int getNightMode();

    void setNightMode(int nightMode);

    Single<String> checkToken();

    Completable insertUser(User user);

    Completable deleteUser(User user);

    Completable updateUser(User user);

    Flowable<User> getUser();
}

package com.znu.news.data.remote.services;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserService {

    @GET("users/")
    Single<String> checkToken(@Query("token") String token);
}

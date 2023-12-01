package com.znu.news.data.remote.services;

//mobile_app
//M0B1LE!@#

import com.znu.news.data.remote.response.UserTokenDto;

import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

    @POST("login")
    Single<UserTokenDto> login(@Body RequestBody body);
}

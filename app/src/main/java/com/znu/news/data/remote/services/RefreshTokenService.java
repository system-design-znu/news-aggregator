package com.znu.news.data.remote.services;

import com.znu.news.data.remote.response.UserTokenDto;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RefreshTokenService {

    @POST("users/")
    Response<UserTokenDto> refreshAccessToken();
}

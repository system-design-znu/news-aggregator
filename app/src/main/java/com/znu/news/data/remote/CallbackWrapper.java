package com.znu.news.data.remote;

import static com.znu.news.model.ErrorType.Connection;
import static com.znu.news.model.ErrorType.InternalServerError;
import static com.znu.news.model.ErrorType.Unknown;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.znu.news.model.Error;
import com.znu.news.model.ErrorType;
import com.znu.news.model.Resource;
import com.znu.news.utils.SessionManager;

import java.io.IOException;
import java.lang.reflect.Type;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

public abstract class CallbackWrapper<T, E extends Error> implements SingleObserver<T> {

    @Inject
    public SessionManager sessionManager;

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        if (throwable instanceof HttpException) {
            Response<?> response = ((HttpException) throwable).response();
            if (response != null) {

                if (response.code() == ErrorType.Unauthorized.errorCode) {
                    sessionManager.logout();
                    return;
                }

                if (response.errorBody() != null) {
                    E error = getError(response.errorBody());
                    if (error != null) onComplete(Resource.error(error));
                    else
                        onComplete(Resource.error(new Error.RemoteServiceError(response.code())));
                    return;
                }
            }
            onComplete(Resource.error(new Error.RemoteServiceError(InternalServerError)));
        } else if (throwable instanceof IOException) {
            onComplete(Resource.error(new Error.RemoteServiceError(Connection)));
        } else {
            onComplete(Resource.error(new Error.RemoteServiceError(Unknown)));
        }
    }

    @Override
    public void onSuccess(T t) {
        onComplete(Resource.success(t));
    }

    @Override
    public void onSubscribe(Disposable d) {
    }

    protected abstract void onComplete(Resource<T> response);

    protected E getError(ResponseBody responseBody) {
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<E>() {
            }.getType();

            return gson.fromJson(responseBody.charStream(), type);
        } catch (Exception exception) {
            return null;
        }
    }
}

package com.znu.news.data.remote;

import static com.znu.news.model.ErrorType.Connection;
import static com.znu.news.model.ErrorType.InternalServerError;
import static com.znu.news.model.ErrorType.Unknown;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.znu.news.model.Error;
import com.znu.news.model.Resource;

import java.io.IOException;
import java.lang.reflect.Type;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

public abstract class CallbackWrapper<T, E extends Error> implements SingleObserver<T> {

    @Override
    public void onError(Throwable throwable) {
        if (throwable instanceof HttpException) {
            Response<?> response = ((HttpException) throwable).response();
            if (response != null) {
                if (response.errorBody() != null) {
                    E error = getError(response.errorBody());
                    if (error != null) onComplete(Resource.error(error));
                    else
                        onComplete(Resource.error(new Error.RemoteServiceError(response.code())));
                    return;
                }
            }
            onComplete(Resource.error(new Error.RemoteServiceError(InternalServerError, throwable)));
        } else if (throwable instanceof IOException) {
            onComplete(Resource.error(new Error.RemoteServiceError(Connection, throwable)));
        } else {
            onComplete(Resource.error(new Error.RemoteServiceError(Unknown, throwable)));
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

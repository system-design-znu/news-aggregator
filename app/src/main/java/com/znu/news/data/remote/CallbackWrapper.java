package com.znu.news.data.remote;

import static com.znu.news.model.ErrorType.Connection;
import static com.znu.news.model.ErrorType.InternalServerError;
import static com.znu.news.model.ErrorType.Unknown;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.znu.news.model.Error;

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
                    if (error != null) onFailure(error);
                    else
                        onFailure(new Error.RemoteServiceError(response.code()));
                    return;
                }
            }
            onFailure(new Error.RemoteServiceError(InternalServerError));
        } else if (throwable instanceof IOException) {
            onFailure((new Error.RemoteServiceError(Connection)));
        } else {
            onFailure(new Error.RemoteServiceError(Unknown));
        }
    }

    @Override
    public void onSuccess(T t) {
        onComplete(t);
    }

    @Override
    public void onSubscribe(Disposable d) {
    }

    protected abstract void onComplete(T t);

    protected abstract void onFailure(Error e);

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

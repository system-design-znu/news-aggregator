package com.znu.news.model;

import static com.znu.news.model.Status.ERROR;
import static com.znu.news.model.Status.LOADING;
import static com.znu.news.model.Status.SUCCESS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Resource<T> {


    public Status status;

    @Nullable
    public T data;

    @Nullable
    public String message;

    @Nullable
    public Integer errorCode;

    public Resource(Status status, @Nullable T data, @Nullable String message, @Nullable Integer errorCode) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.errorCode = errorCode;
    }

    public Resource(Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static Resource loading() {
        return new Resource(LOADING, null, null);
    }

    public static Resource success(@NonNull Object data) {
        return new Resource(SUCCESS, data, null);
    }

    public static Resource error(@NonNull String message) {
        return new Resource(ERROR, null, message);
    }

    public static Resource error(@NonNull String message, Integer errorCode) {
        return new Resource(ERROR, null, message, errorCode);
    }
}


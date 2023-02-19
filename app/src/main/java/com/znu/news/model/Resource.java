package com.znu.news.model;

import static com.znu.news.model.Status.ERROR;
import static com.znu.news.model.Status.LOADING;
import static com.znu.news.model.Status.SUCCESS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Resource<T> {


    @Nullable
    public final T data;
    public Status status;
    public Error error;

    public Resource(Status status, @Nullable T data, @Nullable Error error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static <T> Resource<T> loading() {
        return new Resource<>(LOADING, null, null);
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> error(@NonNull Error error) {
        return new Resource<>(ERROR, null, error);
    }
}


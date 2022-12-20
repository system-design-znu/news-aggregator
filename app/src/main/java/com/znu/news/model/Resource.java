package com.znu.news.model;

import static com.znu.news.model.Status.ERROR;
import static com.znu.news.model.Status.LOADING;
import static com.znu.news.model.Status.SUCCESS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Resource<T> {


    public Status status;

    public final T data;

    public Error error;

    public Resource(Status status, @Nullable T data, @Nullable Error error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static Resource loading() {
        return new Resource(LOADING, null, null);
    }

    public static Resource success(@NonNull Object data) {
        return new Resource(SUCCESS, data, null);
    }

    public static Resource error(@NonNull Error error) {
        return new Resource(ERROR, null, error);
    }
}


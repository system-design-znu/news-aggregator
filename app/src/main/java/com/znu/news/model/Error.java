package com.znu.news.model;

public class Error {

    public ErrorType errorType;
    public Throwable error;

    public Error() {
    }

    public Error(ErrorType errorType) {
        this.errorType = errorType;
    }

    public Error(Throwable error) {
        this.error = error;
    }

    public Error(ErrorType errorType, Throwable error) {
        this.errorType = errorType;
        this.error = error;
    }

    public static class RemoteServiceError extends Error {


        public RemoteServiceError(int errorCode, Throwable error) {
            errorType = ErrorType.valueOf(errorCode);
            this.error = error;
        }

        public RemoteServiceError(ErrorType errorType, Throwable error) {
            super(errorType, error);
        }
    }
}

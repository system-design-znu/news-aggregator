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

        public boolean isClientError;
        public boolean isServerError;

        public RemoteServiceError(int errorCode) {
            errorType = ErrorType.valueOf(errorCode);
            checkError(errorCode);
        }

        public RemoteServiceError(int errorCode, Throwable error) {
            errorType = ErrorType.valueOf(errorCode);
            this.error = error;
            checkError(errorCode);
        }

        public RemoteServiceError(ErrorType errorType) {
            super(errorType);
            checkError(errorType.errorCode);
        }

        public RemoteServiceError(ErrorType errorType, Throwable error) {
            super(errorType, error);
            checkError(errorType.errorCode);
        }

        private void checkError(int errorCode) {
            isClientError = (errorCode > 399) && (errorCode < 500);
            isServerError = (errorCode > 499) && (errorCode < 600);
        }
    }
}

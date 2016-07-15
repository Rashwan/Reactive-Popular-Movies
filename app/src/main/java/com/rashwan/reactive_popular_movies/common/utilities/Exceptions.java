package com.rashwan.reactive_popular_movies.common.utilities;

/**
 * Created by rashwan on 7/14/16.
 */

public final class Exceptions {
    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before requesting data to the Presenter");
        }
    }
    public static class NoInternetException extends Exception {
        public Boolean firstPage;
        public String message;
        public NoInternetException(Boolean firstPage,String message){
            this.firstPage = firstPage;
            this.message = message;
        }

        public NoInternetException(String message) {
            super(message);
        }

        @Override
        public String toString() {
            return "NoInternetException{" +
                    "firstPage=" + firstPage +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}

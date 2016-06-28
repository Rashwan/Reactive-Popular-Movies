package com.rashwan.reactive_popular_movies.common;

/**
 * Created by rashwan on 6/26/16.
 */

public interface Presenter<V extends MvpView> {
    void attachView(V mvpView);
    void detachView();
}

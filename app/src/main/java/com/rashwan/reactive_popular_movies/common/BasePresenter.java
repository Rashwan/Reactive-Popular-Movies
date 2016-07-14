package com.rashwan.reactive_popular_movies.common;

import static com.rashwan.reactive_popular_movies.common.utilities.Exceptions.MvpViewNotAttachedException;

/**
 * Created by rashwan on 6/26/16.
 */

public class BasePresenter <T extends MvpView> implements Presenter<T>{
    private T mvpView;

    @Override
    public void attachView(T mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void detachView() {
        this.mvpView = null;
    }

    Boolean isViewAttached(){
        return mvpView!=null;
    }

    public T getView() {
        return mvpView;
    }
    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }



}

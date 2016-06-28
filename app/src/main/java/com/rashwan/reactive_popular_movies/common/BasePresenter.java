package com.rashwan.reactive_popular_movies.common;

/**
 * Created by rashwan on 6/26/16.
 */

public class BasePresenter <T extends MvpView> implements Presenter<T>{
    T mvpView;

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


    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before requesting data to the Presenter");
        }
    }
}

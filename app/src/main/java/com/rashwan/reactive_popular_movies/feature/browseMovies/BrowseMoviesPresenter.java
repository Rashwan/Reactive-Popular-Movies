package com.rashwan.reactive_popular_movies.feature.browseMovies;

import com.rashwan.reactive_popular_movies.common.BasePresenter;
import com.rashwan.reactive_popular_movies.model.MoviesResponse;
import com.rashwan.reactive_popular_movies.service.MoviesService;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by rashwan on 6/24/16.
 */

public class BrowseMoviesPresenter extends BasePresenter<BrowseMoviesView>  {
    private Observable<MoviesResponse> request;
    private Subscription subscription;
    MoviesService moviesService;
    MoviesResponse mMoviesResponse;

    @Inject
    public BrowseMoviesPresenter(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (subscription != null) subscription.unsubscribe();
    }

    public void getMovies(int page){
        if (mMoviesResponse != null && mMoviesResponse.getPage() == page){
            request = Observable.just(mMoviesResponse);
        }else {
            request = moviesService.getPopularMovies(page);
        }
        //Retrieve Movies using Retrofit then on success call getView.showMovies()
        subscription = request.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(moviesResponse -> {
                mMoviesResponse = moviesResponse;
                getView().hideProgress();
                getView().showMovies(moviesResponse.getMovies());
                Timber.d(moviesResponse.getMovies().get(1).title());
            }
                , throwable -> Timber.d(throwable,"Error retrieving movies")
                , () ->Timber.d("Finished getting movies"));
    }


}

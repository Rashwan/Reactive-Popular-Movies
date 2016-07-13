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
    private Subscription subscription;
    private MoviesService moviesService;
    private MoviesResponse mMoviesResponse;
    public static final int SORT_POPULAR_MOVIES = 0;
    public static final int SORT_TOP_RATED_MOVIES = 1;


    @Inject
    public BrowseMoviesPresenter(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (subscription != null) subscription.unsubscribe();
    }

    public void getMovies(int sortBy,int page){
        checkViewAttached();
        if (page == 1){
            getView().clearScreen();
            getView().showProgress();
        }
        Observable<MoviesResponse> request = (sortBy == SORT_TOP_RATED_MOVIES) ?
                moviesService.getTopRatedMovies(page):moviesService.getPopularMovies(page);

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

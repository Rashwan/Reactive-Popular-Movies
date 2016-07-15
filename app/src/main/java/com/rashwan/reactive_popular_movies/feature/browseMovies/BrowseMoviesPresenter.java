package com.rashwan.reactive_popular_movies.feature.browseMovies;

import com.rashwan.reactive_popular_movies.common.BasePresenter;
import com.rashwan.reactive_popular_movies.common.utilities.Exceptions.NoInternetException;
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
    public static final int SORT_POPULAR_MOVIES = 0;
    public static final int SORT_TOP_RATED_MOVIES = 1;
    private int page ;


    @Inject
    public BrowseMoviesPresenter(MoviesService moviesService) {
        this.moviesService = moviesService;
        page = 1;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (subscription != null) subscription.unsubscribe();
    }

    public void getMovies(int sortBy,Boolean firstPage){
        checkViewAttached();
        if (firstPage) {
            getView().clearScreen();
            getView().showProgress();
            page = 1;
        }
        Observable<MoviesResponse> request = (sortBy == SORT_TOP_RATED_MOVIES) ?
                moviesService.getTopRatedMovies(page):moviesService.getPopularMovies(page);

        //Retrieve Movies using Retrofit then on success call getView.showMovies()
        subscription = request.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(moviesResponse -> {
                getView().hideProgress();
                getView().showMovies(moviesResponse.getMovies());
                Timber.d(moviesResponse.getMovies().get(1).title());
                }
                , throwable -> {
                        if (throwable instanceof NoInternetException){
                            NoInternetException exception = (NoInternetException) throwable;
                            Timber.d("Error retrieving movies: %s . First page: %s",exception.message,exception.firstPage);
                            if (((NoInternetException) throwable).firstPage){
                                getView().showOfflineLayout();
                            }else {
                                getView().showOfflineSnackbar();
                            }
                        }
                        else {
                            Timber.d("Error retrieving movies");
                        }
                    }
                , () -> {
                        page ++;
                        Timber.d("Finished getting movies");

                    });

    }


}

package com.rashwan.reactive_popular_movies.feature.browseMovies;

import com.rashwan.reactive_popular_movies.common.BasePresenter;
import com.rashwan.reactive_popular_movies.common.utilities.Exceptions.NoInternetException;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.service.MoviesService;

import java.util.ArrayList;
import java.util.List;

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
    private Subscription favoriteSubscription;
    private MoviesService moviesService;
    public static final int SORT_POPULAR_MOVIES = 0;
    public static final int SORT_TOP_RATED_MOVIES = 1;
    public static final int SORT_FAVORITE_MOVIES = 2;
    private int page ;
    private List<Movie> favoriteMovies = new ArrayList<>();


    @Inject
    public BrowseMoviesPresenter(MoviesService moviesService) {
        this.moviesService = moviesService;
        page = 1;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (subscription != null) subscription.unsubscribe();
        if (favoriteSubscription != null) favoriteSubscription.unsubscribe();
    }

    public void getMovies(int sortBy,Boolean firstPage){
        checkViewAttached();
        if (firstPage) {
            getView().clearScreen();
            getView().showProgress();
            page = 1;
        }
        Observable<List<Movie>> request ;
        request = sortBy == SORT_TOP_RATED_MOVIES ? moviesService.getTopRatedMovies(page) : moviesService.getPopularMovies(page);

        subscription = request.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(movies -> {
                getView().hideProgress();
                getView().showMovies(movies);
                Timber.d(movies.get(1).title());
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
    public void getFavoriteMovies() {
        getView().clearScreen();
        getView().showProgress();
        if (favoriteSubscription == null || favoriteSubscription.isUnsubscribed()) {
            favoriteSubscription = moviesService.getFavoriteMovies().observeOn(AndroidSchedulers.mainThread())
                    .subscribe(movies -> {
                                favoriteMovies = movies;
                                Timber.d(String.valueOf(movies.size()));
                                getView().hideProgress();
                                getView().showMovies(favoriteMovies);
                            }
                            , throwable -> Timber.d(throwable, throwable.getMessage())
                            , () -> Timber.d("Finished getting fav movies"));
        }else {
            getView().hideProgress();
            getView().showMovies(favoriteMovies);
        }

    }
}

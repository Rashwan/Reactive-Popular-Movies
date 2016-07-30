package com.rashwan.reactive_popular_movies.feature.browseMovies;

import com.rashwan.reactive_popular_movies.common.BasePresenter;
import com.rashwan.reactive_popular_movies.common.utilities.Exceptions.NoInternetException;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.service.MoviesService;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by rashwan on 6/24/16.
 */

public class BrowseMoviesPresenter extends BasePresenter<BrowseMoviesView>  {

    public static final int SORT_POPULAR_MOVIES = 0;
    public static final int SORT_TOP_RATED_MOVIES = 1;
    public static final int SORT_FAVORITE_MOVIES = 2;
    private Subscription browseSubscription;
    private Subscription favoriteSubscription;
    private MoviesService moviesService;
    private int page ;
    private List<Movie> favoriteMovies = new ArrayList<>();


    public BrowseMoviesPresenter(MoviesService moviesService) {
        this.moviesService = moviesService;
        page = 1;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (browseSubscription != null) browseSubscription.unsubscribe();
        if (favoriteSubscription != null) favoriteSubscription.unsubscribe();
    }
    public void cancelInFlightRequests(){
        if (browseSubscription != null) browseSubscription.unsubscribe();
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

        browseSubscription = request.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
                                getView().hideProgress();
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
        if (favoriteSubscription == null || favoriteSubscription.isUnsubscribed()) {
            favoriteSubscription = moviesService.getFavoriteMovies().observeOn(AndroidSchedulers.mainThread())
                    .subscribe(movies -> {
                                Timber.d(String.valueOf(movies.size()));
                                getView().hideProgress();
                                if (movies.isEmpty()){
                                    getView().clearScreen();
                                    getView().showNoFavorites();
                                }else {
                                    if (movies.size() != favoriteMovies.size()){
                                        getView().clearScreen();
                                        getView().showMovies(movies);
                                    }
                                }
                                favoriteMovies = movies;
                            }
                            , throwable -> Timber.d(throwable, throwable.getMessage())
                            , () -> Timber.d("Finished getting fav movies"));
        }else {
            getView().hideProgress();
            if (favoriteMovies.isEmpty()){
                getView().showNoFavorites();
            }else {
                getView().showMovies(favoriteMovies);
            }
        }

    }
}

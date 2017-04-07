package com.rashwan.reactive_popular_movies.feature.favoriteMovies;

import com.rashwan.reactive_popular_movies.common.BasePresenter;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.service.MoviesService;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

/**
 * Created by rashwan on 4/7/17.
 */

public class FavoriteMoviesPresenter extends BasePresenter<FavoriteMoviesView> {
    private final MoviesService moviesService;
    private Subscription favoriteSubscription;
    private List<Movie> favoriteMovies = new ArrayList<>();


    public FavoriteMoviesPresenter(MoviesService moviesService) {
        this.moviesService = moviesService;
    }
    @Override
    public void detachView() {
        super.detachView();
        if (favoriteSubscription != null) favoriteSubscription.unsubscribe();
    }
    public void getFavoriteMovies() {
        if (favoriteSubscription == null || favoriteSubscription.isUnsubscribed()) {
            favoriteSubscription = moviesService.getFavoriteMovies().observeOn(AndroidSchedulers.mainThread())
                    .subscribe(movies -> {
                                Timber.d(String.valueOf(movies.size()));
                                if (movies.isEmpty()){
                                    getView().showNoFavorites();
                                }else {
                                    if (movies.size() != favoriteMovies.size()){
                                        getView().showMovies(movies);
                                    }
                                }
                                favoriteMovies = movies;
                            }
                            , throwable -> Timber.d(throwable, throwable.getMessage())
                            , () -> Timber.d("Finished getting fav movies"));
        }else {
            if (favoriteMovies.isEmpty()){
                getView().showNoFavorites();
            }else {
                getView().showMovies(favoriteMovies);
            }
        }

    }
}

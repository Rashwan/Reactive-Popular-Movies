package com.rashwan.reactive_popular_movies.feature.favoriteMovies;

import com.rashwan.reactive_popular_movies.common.BasePresenter;
import com.rashwan.reactive_popular_movies.dI.PerFragment;
import com.rashwan.reactive_popular_movies.data.MoviesRepository;
import com.rashwan.reactive_popular_movies.data.model.Movie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

/**
 * Created by rashwan on 4/7/17.
 */
@PerFragment
public class FavoriteMoviesPresenter extends BasePresenter<FavoriteMoviesView> {
    private final MoviesRepository moviesRepository;
    private Subscription favoriteSubscription;
    private List<Movie> favoriteMovies = new ArrayList<>();

    @Inject
    public FavoriteMoviesPresenter(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }
    @Override
    public void detachView() {
        super.detachView();
        if (favoriteSubscription != null) favoriteSubscription.unsubscribe();
    }
    public void getFavoriteMovies() {
        if (favoriteSubscription == null || favoriteSubscription.isUnsubscribed()) {
            favoriteSubscription = moviesRepository.getFavoriteMovies().observeOn(AndroidSchedulers.mainThread())
                    .subscribe(movies -> {
                                Timber.d(String.valueOf(movies.size()));
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
                            , () -> Timber.d("Getting fav movies completed"));
        }else {
            if (favoriteMovies.isEmpty()){
                getView().showNoFavorites();
            }else {
                getView().showMovies(favoriteMovies);
            }
        }

    }
}

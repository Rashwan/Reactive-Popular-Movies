package com.rashwan.reactive_popular_movies.feature.watchlistMovies;

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
 * Created by rashwan on 4/9/17.
 */
@PerFragment
public class WatchlistPresenter extends BasePresenter<WatchlistView> {
    private final MoviesRepository moviesRepository;
    private Subscription watchlistSubscription;
    private List<Movie> watchlistMovies = new ArrayList<>();

    @Inject
    public WatchlistPresenter(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (watchlistSubscription!= null) watchlistSubscription = null;
    }
    public void getWatchlistMovies(){
        if (watchlistSubscription == null || watchlistSubscription.isUnsubscribed()) {
            watchlistSubscription = moviesRepository.getWatchlistMovies().observeOn(AndroidSchedulers.mainThread())
                    .subscribe(movies -> {
                                Timber.d(String.valueOf(movies.size()));
                                if (movies.isEmpty()){
                                    getView().clearScreen();
                                    getView().showEmptyWatchlist();
                                }else {
                                    if (movies.size() != watchlistMovies.size()){
                                        getView().clearScreen();
                                        getView().showMovies(movies);
                                    }
                                }
                                watchlistMovies = movies;
                            }
                            , throwable -> Timber.d(throwable, throwable.getMessage())
                            , () -> Timber.d("Finished getting fav movies"));
        }else {
            if (watchlistMovies.isEmpty()){
                getView().showEmptyWatchlist();
            }else {
                getView().showMovies(watchlistMovies);
            }
        }
    }
}


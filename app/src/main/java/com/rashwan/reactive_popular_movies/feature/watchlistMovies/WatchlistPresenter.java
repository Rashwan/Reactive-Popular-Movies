package com.rashwan.reactive_popular_movies.feature.watchlistMovies;

import com.rashwan.reactive_popular_movies.common.BasePresenter;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.service.MoviesService;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * Created by rashwan on 4/9/17.
 */

public class WatchlistPresenter extends BasePresenter<WatchlistView> {
    private final MoviesService moviesService;
    private Subscription watchlistSubscription;
    private List<Movie> watchlaterMovies = new ArrayList<>();

    public WatchlistPresenter(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (watchlistSubscription!= null) watchlistSubscription = null;
    }
    public void getWatchlaterMovies(){

    }
}


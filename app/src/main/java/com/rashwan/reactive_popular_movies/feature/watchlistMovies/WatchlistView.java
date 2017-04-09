package com.rashwan.reactive_popular_movies.feature.watchlistMovies;

import com.rashwan.reactive_popular_movies.common.MvpView;
import com.rashwan.reactive_popular_movies.data.model.Movie;

import java.util.List;

/**
 * Created by rashwan on 4/9/17.
 */

public interface WatchlistView extends MvpView{
    void showEmptyWatchlist();
    void showMovies(List<Movie> movies);
    void clearScreen();
}

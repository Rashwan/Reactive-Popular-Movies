package com.rashwan.reactive_popular_movies.feature.browseMovies;

import com.rashwan.reactive_popular_movies.common.MvpView;
import com.rashwan.reactive_popular_movies.data.model.Movie;

import java.util.List;

/**
 * Created by rashwan on 6/26/16.
 */

public interface BrowseMoviesView extends MvpView{
    void showMovies(List<Movie> movies);
    void showProgress();
    void hideProgress();
    void clearScreen();
    void showOfflineLayout();
    void showOfflineSnackbar();
    void showNoFavorites();
}

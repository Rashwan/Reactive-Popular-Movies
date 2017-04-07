package com.rashwan.reactive_popular_movies.feature.favoriteMovies;

import com.rashwan.reactive_popular_movies.common.MvpView;
import com.rashwan.reactive_popular_movies.data.model.Movie;

import java.util.List;

/**
 * Created by rashwan on 4/7/17.
 */

public interface FavoriteMoviesView extends MvpView {
    void showMovies(List<Movie> movies);
    void showNoFavorites();

}

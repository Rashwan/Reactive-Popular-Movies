package com.rashwan.reactive_popular_movies.feature.movieDetails.movieInfo;

import com.rashwan.reactive_popular_movies.common.MvpView;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.data.model.MovieDetails;
import com.rashwan.reactive_popular_movies.data.model.Trailer;

import java.util.List;

/**
 * Created by rashwan on 7/7/16.
 */

public interface MovieInfoView extends MvpView {
    void showTrailers(List<Trailer> trailers);
    void showOfflineLayout();
    void hideOfflineLayout();

//    void showPlayTrailerButton();
    void showMovieRuntime(String runtime);
    void showOmdbDetails(MovieDetails movieDetails);
    void showSimilarMovies(List<Movie> movies);

}

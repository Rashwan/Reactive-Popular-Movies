package com.rashwan.reactive_popular_movies.data;

import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.data.model.MovieDetails;
import com.rashwan.reactive_popular_movies.data.model.Review;
import com.rashwan.reactive_popular_movies.data.model.Trailer;

import java.util.List;

import rx.Observable;

/**
 * Created by rashwan on 5/25/17.
 */

public interface MoviesDataSource {
    Observable<List<Movie>> getPopularMovies(int page);
    Observable<List<Movie>> getTopRatedMovies(int page);
    Observable<List<Movie>> getUpcomingMovies(int page);
    Observable<List<Trailer>> getMovieTrailers(long id);
    Observable<List<Review>> getMovieReview(long id);
    Observable<List<Movie>> getSimilarMovies(long id);
    Observable<Movie> getMovieDetails(long id);
    Observable<MovieDetails> getMovieOMDBDetails(String tmdbId) ;
    Observable<Movie> getMovieById(Long id);
    Observable<Long> findMovieByID(Long movieId);
    Observable<List<Movie>> getFavoriteMovies();
    Observable<List<Long>> getFavoriteMoviesIds();
    Observable<Boolean> isMovieFavorite(Long movieId);
    Observable<List<Movie>> getWatchlistMovies();
    Observable<Boolean> isMovieInWatchlist(Long movieId);
}

package com.rashwan.reactive_popular_movies.data;

import com.rashwan.reactive_popular_movies.data.di.qualifier.Local;
import com.rashwan.reactive_popular_movies.data.di.qualifier.Remote;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.data.model.MovieDetails;
import com.rashwan.reactive_popular_movies.data.model.ReviewResponse;
import com.rashwan.reactive_popular_movies.data.model.Trailer;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by rashwan on 5/25/17.
 */
@Singleton
public class MoviesRepository implements MoviesDataSource{
    private final MoviesDataSource moviesLocalDataSource;
    private final MoviesDataSource moviesRemoteDataSource;

    @Inject
    public MoviesRepository(@Local MoviesDataSource moviesLocalDataSource, @Remote MoviesDataSource moviesRemoteDataSource) {
        this.moviesLocalDataSource = moviesLocalDataSource;
        this.moviesRemoteDataSource = moviesRemoteDataSource;
    }


    @Override
    public Observable<List<Movie>> getPopularMovies(int page) {
        return moviesRemoteDataSource.getPopularMovies(page);
    }

    @Override
    public Observable<List<Movie>> getTopRatedMovies(int page) {
        return moviesRemoteDataSource.getTopRatedMovies(page);
    }

    @Override
    public Observable<List<Movie>> getUpcomingMovies(int page) {
        return moviesRemoteDataSource.getUpcomingMovies(page);
    }

    @Override
    public Observable<List<Trailer>> getMovieTrailers(long id) {
        return moviesRemoteDataSource.getMovieTrailers(id);
    }

    @Override
    public Observable<ReviewResponse> getMovieReview(long id) {
        return moviesRemoteDataSource.getMovieReview(id);
    }

    @Override
    public Observable<List<Movie>> getSimilarMovies(long id) {
        return moviesRemoteDataSource.getSimilarMovies(id);
    }

    @Override
    public Observable<Movie> getMovieDetails(long id) {
        return moviesRemoteDataSource.getMovieDetails(id);
    }

    @Override
    public Observable<MovieDetails> getMovieOMDBDetails(String tmdbId) {
        return moviesRemoteDataSource.getMovieOMDBDetails(tmdbId);
    }

    @Override
    public Observable<Movie> getMovieById(Long id) {
        return moviesLocalDataSource.getMovieById(id);
    }

    @Override
    public Observable<Long> findMovieByID(Long movieId) {
        return moviesLocalDataSource.findMovieByID(movieId);
    }

    @Override
    public Observable<List<Movie>> getFavoriteMovies() {
        return moviesLocalDataSource.getFavoriteMovies();
    }

    @Override
    public Observable<List<Long>> getFavoriteMoviesIds() {
        return moviesLocalDataSource.getFavoriteMoviesIds();
    }

    @Override
    public Observable<Boolean> isMovieFavorite(Long movieId) {
        return moviesLocalDataSource.isMovieFavorite(movieId);
    }

    @Override
    public Observable<List<Movie>> getWatchlistMovies() {
        return moviesLocalDataSource.getWatchlistMovies();
    }

    @Override
    public Observable<Boolean> isMovieInWatchlist(Long movieId) {
        return moviesLocalDataSource.isMovieInWatchlist(movieId);
    }
}

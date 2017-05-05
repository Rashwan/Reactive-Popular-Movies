package com.rashwan.reactive_popular_movies.service;

import com.rashwan.reactive_popular_movies.data.model.ActorTaggedImage;
import com.rashwan.reactive_popular_movies.data.model.Cast;
import com.rashwan.reactive_popular_movies.data.model.CastDetails;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.data.model.ReviewResponse;
import com.rashwan.reactive_popular_movies.data.model.Trailer;

import java.util.List;

import rx.Observable;

/**
 * Created by rashwan on 6/23/16.
 */

public interface TMDBService {
    Observable<List<Movie>> getPopularMovies(int page);
    Observable<List<Movie>> getTopRatedMovies(int page);
    Observable<List<Movie>> getUpcomingMovies(int page);
    Observable<List<Trailer>> getMovieTrailers(long id);
    Observable<ReviewResponse> getMovieReview(long id);
    Observable<List<Movie>> getSimilarMovies(long id);
    Observable<Movie> getMovieDetails(long id);
    Observable<Movie> getMovieById(Long id);
    Observable<Long> findMovieByID(Long movieId);
    Observable<List<Movie>> getFavoriteMovies();
    Observable<List<Long>> getFavoriteMoviesIds();
    Observable<Boolean> isMovieFavorite(Long movieId);
    Observable<List<Movie>> getWatchlistMovies();
    Observable<Boolean> isMovieInWatchlist(Long movieId);
    Observable<List<Cast>> getMovieCast(long movieId);
    Observable<CastDetails> getActorDetails(long castId);
    Observable<List<ActorTaggedImage>> getActorTaggedImages(long castId);

}

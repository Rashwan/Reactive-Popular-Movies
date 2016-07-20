package com.rashwan.reactive_popular_movies.service;

import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.data.model.ReviewResponse;
import com.rashwan.reactive_popular_movies.data.model.TrailersResponse;

import java.util.List;

import rx.Observable;

/**
 * Created by rashwan on 6/23/16.
 */

public interface MoviesService {
    Observable<List<Movie>> getPopularMovies(int page);
    Observable<List<Movie>> getTopRatedMovies(int page);
    Observable<List<Movie>> getFavoriteMovies();
    Observable<TrailersResponse> getMovieTrailers(long id);
    Observable<ReviewResponse> getMovieReview(long id);
}

package com.rashwan.reactive_popular_movies.service;

import com.rashwan.reactive_popular_movies.model.MoviesResponse;
import com.rashwan.reactive_popular_movies.model.ReviewResponse;
import com.rashwan.reactive_popular_movies.model.TrailersResponse;

import rx.Observable;

/**
 * Created by rashwan on 6/23/16.
 */

public interface MoviesService {
     Observable<MoviesResponse> getPopularMovies();
     Observable<TrailersResponse> getMovieTrailers(int id);
     Observable<ReviewResponse> getMovieReview(int id);
}

package com.rashwan.reactive_popular_movies.services;

import com.rashwan.reactive_popular_movies.model.MoviesResponse;

import rx.Observable;

/**
 * Created by rashwan on 6/23/16.
 */

public interface MoviesService {
     Observable<MoviesResponse> getPopularMovies();
}

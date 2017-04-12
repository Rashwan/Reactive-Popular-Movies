package com.rashwan.reactive_popular_movies.service;

import com.rashwan.reactive_popular_movies.data.model.MovieDetails;

import rx.Observable;

/**
 * Created by rashwan on 4/12/17.
 */

public interface OMDBService {
    Observable<MovieDetails> getMovieDetails(String tmdbId);
}

package com.rashwan.reactive_popular_movies.common;

import com.rashwan.reactive_popular_movies.model.MoviesResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by rashwan on 6/23/16.
 */

public interface TMDBApi {
    @GET("movie/popular")
    Observable<MoviesResponse> getPopularMovies();
}

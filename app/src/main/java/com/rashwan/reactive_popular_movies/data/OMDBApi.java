package com.rashwan.reactive_popular_movies.data;

import com.rashwan.reactive_popular_movies.data.model.MovieDetails;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by rashwan on 4/12/17.
 */

public interface OMDBApi {
    @GET(".")
    Observable<MovieDetails> getMovieDetails(@Query("i") String imdbId);


}

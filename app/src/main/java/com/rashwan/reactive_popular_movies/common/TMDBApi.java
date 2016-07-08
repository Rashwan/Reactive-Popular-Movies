package com.rashwan.reactive_popular_movies.common;

import com.rashwan.reactive_popular_movies.model.MoviesResponse;
import com.rashwan.reactive_popular_movies.model.ReviewResponse;
import com.rashwan.reactive_popular_movies.model.TrailersResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by rashwan on 6/23/16.
 */

public interface TMDBApi {
    @GET("movie/popular")
    Observable<MoviesResponse> getPopularMovies();

    @GET("movie/{id}/videos")
    Observable<TrailersResponse> getMovieTrailers(@Path("id")int id);

    @GET("movie/{id}/reviews")
    Observable<ReviewResponse> getMovieReviews(@Path("id") int id);
}

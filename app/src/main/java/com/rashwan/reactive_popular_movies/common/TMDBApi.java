package com.rashwan.reactive_popular_movies.common;

import com.rashwan.reactive_popular_movies.model.MoviesResponse;
import com.rashwan.reactive_popular_movies.model.ReviewResponse;
import com.rashwan.reactive_popular_movies.model.TrailersResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by rashwan on 6/23/16.
 */

public interface TMDBApi {
    @GET("movie/popular")
    Observable<MoviesResponse> getPopularMovies(@Query("page") int page);

    @GET("movie/top_rated")
    Observable<MoviesResponse> getTopRatedMovies(@Query("page") int page);

    @GET("movie/{id}/videos")
    Observable<TrailersResponse> getMovieTrailers(@Path("id")int id);

    @GET("movie/{id}/reviews")
    Observable<ReviewResponse> getMovieReviews(@Path("id") int id);
}

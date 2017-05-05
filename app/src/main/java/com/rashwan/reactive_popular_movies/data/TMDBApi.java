package com.rashwan.reactive_popular_movies.data;

import com.rashwan.reactive_popular_movies.data.model.ActorProfileImagesResponse;
import com.rashwan.reactive_popular_movies.data.model.ActorTaggedImagesResponse;
import com.rashwan.reactive_popular_movies.data.model.CastDetails;
import com.rashwan.reactive_popular_movies.data.model.CreditsResponse;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.data.model.MoviesResponse;
import com.rashwan.reactive_popular_movies.data.model.ReviewResponse;
import com.rashwan.reactive_popular_movies.data.model.TrailersResponse;

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

    @GET("movie/upcoming")
    Observable<MoviesResponse> getUpcomingMovies(@Query("page") int page,@Query("region") String region);

    @GET("movie/{id}")
    Observable<Movie> getMovieDetails(@Path("id") long id);

    @GET("movie/{id}/videos")
    Observable<TrailersResponse> getMovieTrailers(@Path("id")long id);

    @GET("movie/{id}/reviews")
    Observable<ReviewResponse> getMovieReviews(@Path("id") long id);

    @GET("movie/{id}/similar")
    Observable<MoviesResponse> getSimilarMovies(@Path("id") long id);

    @GET("movie/{id}/credits")
    Observable<CreditsResponse> getMovieCredits(@Path("id") long id);

    @GET("person/{id}")
    Observable<CastDetails> getActorDetails(@Path("id") long id);

    @GET("person/{id}/tagged_images")
    Observable<ActorTaggedImagesResponse> getActorTaggedImaged(@Path("id") long id);

    @GET("person/{id}/images")
    Observable<ActorProfileImagesResponse> getActorProfileImages(@Path("id") long id);
}

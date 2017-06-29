package com.rashwan.reactive_popular_movies.data.remote;

import com.rashwan.reactive_popular_movies.data.model.ActorMovie;
import com.rashwan.reactive_popular_movies.data.model.ActorProfileImagesResponse;
import com.rashwan.reactive_popular_movies.data.model.ActorTaggedImage;
import com.rashwan.reactive_popular_movies.data.model.Cast;
import com.rashwan.reactive_popular_movies.data.model.CastDetails;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.data.model.Review;
import com.rashwan.reactive_popular_movies.data.model.Trailer;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by rashwan on 6/23/16.
 */

public interface TMDBApi {
    @GET("movie/popular") @ResultsResponse
    Observable<List<Movie>> getPopularMovies(@Query("page") int page);

    @GET("movie/top_rated") @ResultsResponse
    Observable<List<Movie>> getTopRatedMovies(@Query("page") int page);

    @GET("movie/upcoming") @ResultsResponse
    Observable<List<Movie>> getUpcomingMovies(@Query("page") int page);

    @GET("movie/{id}")
    Observable<Movie> getMovieDetails(@Path("id") long id);

    @GET("movie/{id}/videos") @ResultsResponse
    Observable<List<Trailer>> getMovieTrailers(@Path("id")long id);

    @GET("movie/{id}/reviews") @ResultsResponse
    Observable<List<Review>> getMovieReviews(@Path("id") long id);

    @GET("movie/{id}/similar") @ResultsResponse
    Observable<List<Movie>> getSimilarMovies(@Path("id") long id);

    @GET("movie/{id}/credits") @CastResponse
    Observable<List<Cast>> getMovieCredits(@Path("id") long id);

    @GET("person/{id}")
    Observable<CastDetails> getActorDetails(@Path("id") long id);

    @GET("person/{id}/tagged_images") @ResultsResponse
    Observable<List<ActorTaggedImage>> getActorTaggedImaged(@Path("id") long id);

    @GET("person/{id}/images")
    Observable<ActorProfileImagesResponse> getActorProfileImages(@Path("id") long id);

    @GET("person/{id}/movie_credits") @CastResponse
    Observable<List<ActorMovie>> getActorMovies(@Path("id") long id);
}

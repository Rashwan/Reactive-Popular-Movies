package com.rashwan.reactive_popular_movies.service;

import android.app.Application;

import com.rashwan.reactive_popular_movies.MovieModel;
import com.rashwan.reactive_popular_movies.data.TMDBApi;
import com.rashwan.reactive_popular_movies.common.utilities.Exceptions;
import com.rashwan.reactive_popular_movies.common.utilities.Utilities;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.data.model.MoviesResponse;
import com.rashwan.reactive_popular_movies.data.model.ReviewResponse;
import com.rashwan.reactive_popular_movies.data.model.TrailersResponse;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by rashwan on 6/23/16.
 */

public class MoviesServiceImp implements MoviesService{
    private Retrofit retrofit;
    private Application application;
    private BriteDatabase db;
    public MoviesServiceImp(Application application, Retrofit retrofit, BriteDatabase db) {
        this.retrofit = retrofit;
        this.application = application;
        this.db = db;
    }

    @Override
    public Observable<List<Movie>> getPopularMovies(int page) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException(page == 1,"No internet connection"));
        }
        return retrofit.create(TMDBApi.class).getPopularMovies(page)
                .map(MoviesResponse::getMovies);
    }

    @Override
    public Observable<List<Movie>> getTopRatedMovies(int page) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException(page == 1,"No internet connection"));
        }
        return retrofit.create(TMDBApi.class).getTopRatedMovies(page)
                .map(MoviesResponse::getMovies);
    }

    @Override
    public Observable<List<Movie>> getFavoriteMovies() {
        return db.createQuery(MovieModel.TABLE_NAME,MovieModel.SELECT_ALL_MOVIES)
                .mapToList(Movie.MOVIES_MAPPER::map);
    }

    @Override
    public Observable<TrailersResponse> getMovieTrailers(long id) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException("No internet connection"));
        }
        return retrofit.create(TMDBApi.class).getMovieTrailers(id);
    }

    @Override
    public Observable<ReviewResponse> getMovieReview(long id) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException("No internet connection"));
        }
        return retrofit.create(TMDBApi.class).getMovieReviews(id);
    }

}

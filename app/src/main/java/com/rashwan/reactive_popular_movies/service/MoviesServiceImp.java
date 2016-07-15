package com.rashwan.reactive_popular_movies.service;

import android.app.Application;

import com.rashwan.reactive_popular_movies.common.TMDBApi;
import com.rashwan.reactive_popular_movies.common.utilities.Exceptions;
import com.rashwan.reactive_popular_movies.common.utilities.NetworkUtilities;
import com.rashwan.reactive_popular_movies.model.MoviesResponse;
import com.rashwan.reactive_popular_movies.model.ReviewResponse;
import com.rashwan.reactive_popular_movies.model.TrailersResponse;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by rashwan on 6/23/16.
 */

public class MoviesServiceImp implements MoviesService{
    private Retrofit retrofit;
    private Application application;
    public MoviesServiceImp(Application application,Retrofit retrofit) {
        this.retrofit = retrofit;
        this.application = application;
    }

    @Override
    public Observable<MoviesResponse> getPopularMovies(int page) {
        if (!NetworkUtilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException(page == 1,"No internet connection"));
        }
        return retrofit.create(TMDBApi.class).getPopularMovies(page);
    }

    @Override
    public Observable<MoviesResponse> getTopRatedMovies(int page) {
        if (!NetworkUtilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException(page == 1,"No internet connection"));
        }
        return retrofit.create(TMDBApi.class).getTopRatedMovies(page);
    }

    @Override
    public Observable<TrailersResponse> getMovieTrailers(int id) {
        if (!NetworkUtilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException("No internet connection"));
        }
        return retrofit.create(TMDBApi.class).getMovieTrailers(id);
    }

    @Override
    public Observable<ReviewResponse> getMovieReview(int id) {
        if (!NetworkUtilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException("No internet connection"));
        }
        return retrofit.create(TMDBApi.class).getMovieReviews(id);
    }

}

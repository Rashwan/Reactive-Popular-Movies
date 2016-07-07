package com.rashwan.reactive_popular_movies.service;

import com.rashwan.reactive_popular_movies.common.TMDBApi;
import com.rashwan.reactive_popular_movies.model.MoviesResponse;
import com.rashwan.reactive_popular_movies.model.TrailersResponse;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by rashwan on 6/23/16.
 */

public class MoviesServiceImp implements MoviesService{
    private Retrofit retrofit;
    public MoviesServiceImp(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Override
    public Observable<MoviesResponse> getPopularMovies() {
        return retrofit.create(TMDBApi.class).getPopularMovies();
    }

    @Override
    public Observable<TrailersResponse> getMovieTrailers(int id) {
        return retrofit.create(TMDBApi.class).getMovieTrailers(id);
    }
}

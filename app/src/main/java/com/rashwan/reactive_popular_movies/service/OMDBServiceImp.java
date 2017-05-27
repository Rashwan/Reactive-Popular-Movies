package com.rashwan.reactive_popular_movies.service;

import android.app.Application;

import com.rashwan.reactive_popular_movies.common.utilities.Exceptions;
import com.rashwan.reactive_popular_movies.common.utilities.Utilities;
import com.rashwan.reactive_popular_movies.data.model.MovieDetails;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by rashwan on 4/12/17.
 */

public class OMDBServiceImp implements OMDBService {
    private Retrofit retrofit;
    private Application application;

    public OMDBServiceImp(Retrofit retrofit, Application application) {
        this.retrofit = retrofit;
        this.application = application;
    }

    @Override
    public Observable<MovieDetails> getMovieOMDBDetails(String tmdbId) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException("No internet connection"));
        }
//        return retrofit.create(OMDBApi.class).getMovieOMDBDetails(tmdbId);
        return Observable.empty();
    }
}

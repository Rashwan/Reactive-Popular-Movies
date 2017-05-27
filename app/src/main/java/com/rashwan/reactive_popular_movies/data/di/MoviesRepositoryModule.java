package com.rashwan.reactive_popular_movies.data.di;

import android.app.Application;

import com.rashwan.reactive_popular_movies.data.MoviesDataSource;
import com.rashwan.reactive_popular_movies.data.di.qualifier.Local;
import com.rashwan.reactive_popular_movies.data.di.qualifier.Remote;
import com.rashwan.reactive_popular_movies.data.local.MoviesLocalDataSource;
import com.rashwan.reactive_popular_movies.data.remote.MoviesRemoteDataSource;
import com.squareup.sqlbrite.BriteDatabase;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by rashwan on 5/27/17.
 */
@Module
public class MoviesRepositoryModule {
    @Provides @Singleton @Local
    public MoviesDataSource provideMoviesLocalDataSource(BriteDatabase db){
        return new MoviesLocalDataSource(db);
    }
    @Provides @Singleton @Remote
    public MoviesDataSource provideMoviesRemoteDataSource(@Named("TMDBRetrofit") Retrofit retrofit, Application application){
        return new MoviesRemoteDataSource(retrofit,application);
    }
}

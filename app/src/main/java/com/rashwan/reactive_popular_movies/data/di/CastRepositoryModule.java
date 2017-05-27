package com.rashwan.reactive_popular_movies.data.di;

import android.app.Application;

import com.rashwan.reactive_popular_movies.data.CastDataSource;
import com.rashwan.reactive_popular_movies.data.di.qualifier.Local;
import com.rashwan.reactive_popular_movies.data.di.qualifier.Remote;
import com.rashwan.reactive_popular_movies.data.local.CastLocalDataSource;
import com.rashwan.reactive_popular_movies.data.remote.CastRemoteDataSource;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by rashwan on 5/27/17.
 */
@Module
public class CastRepositoryModule {
    @Local
    @Provides @Singleton
    CastDataSource provideCastLocalDataSource(){
        return new CastLocalDataSource();
    }

    @Remote
    @Provides @Singleton
    CastDataSource provideCastRemoteDataSource(Application application,@Named("TMDBRetrofit") Retrofit retrofit){
        return new CastRemoteDataSource(application,retrofit);
    }
}

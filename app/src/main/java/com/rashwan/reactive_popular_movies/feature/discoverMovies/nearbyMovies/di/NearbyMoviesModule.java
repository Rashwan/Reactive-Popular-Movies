package com.rashwan.reactive_popular_movies.feature.discoverMovies.nearbyMovies.di;

import com.rashwan.reactive_popular_movies.feature.discoverMovies.BrowseMoviesAdapter;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.nearbyMovies.NearbyMoviesPresenter;
import com.rashwan.reactive_popular_movies.service.TMDBService;
import com.squareup.moshi.Moshi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rashwan on 3/15/17.
 */
@Module
public class NearbyMoviesModule {
    @Provides
    NearbyMoviesPresenter provideNearbyMoviesPresenter(TMDBService TMDBService, Moshi moshi){
        return new NearbyMoviesPresenter(TMDBService,moshi);
    }
    @Provides
    BrowseMoviesAdapter provideBrowseMoviesAdapter(){
        return new BrowseMoviesAdapter();
    }
}

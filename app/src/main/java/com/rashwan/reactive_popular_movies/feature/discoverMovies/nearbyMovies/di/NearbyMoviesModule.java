package com.rashwan.reactive_popular_movies.feature.discoverMovies.nearbyMovies.di;

import com.rashwan.reactive_popular_movies.feature.discoverMovies.BrowseMoviesAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rashwan on 3/15/17.
 */
@Module
public class NearbyMoviesModule {
    @Provides
    BrowseMoviesAdapter provideBrowseMoviesAdapter(){
        return new BrowseMoviesAdapter();
    }
}

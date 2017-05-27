package com.rashwan.reactive_popular_movies.feature.discoverMovies.di;

import com.rashwan.reactive_popular_movies.feature.discoverMovies.BrowseMoviesAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rashwan on 7/26/16.
 */

@Module
public class BrowseMoviesModule {

    @Provides
    public BrowseMoviesAdapter provideBrowseMoviesAdapter(){
        return new BrowseMoviesAdapter();
    }
}

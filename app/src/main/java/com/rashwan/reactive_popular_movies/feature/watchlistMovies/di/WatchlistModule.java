package com.rashwan.reactive_popular_movies.feature.watchlistMovies.di;

import com.rashwan.reactive_popular_movies.feature.discoverMovies.BrowseMoviesAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rashwan on 4/9/17.
 */

@Module
public class WatchlistModule {

    @Provides
    BrowseMoviesAdapter provideBrowseMoviesAdapter(){
        return new BrowseMoviesAdapter();
    }
}

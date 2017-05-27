package com.rashwan.reactive_popular_movies.feature.watchlistMovies.di;

import com.rashwan.reactive_popular_movies.feature.discoverMovies.BrowseMoviesAdapter;
import com.rashwan.reactive_popular_movies.feature.watchlistMovies.WatchlistPresenter;
import com.rashwan.reactive_popular_movies.service.TMDBService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rashwan on 4/9/17.
 */

@Module
public class WatchlistModule {
    @Provides
    WatchlistPresenter provideWatchlistPresenter(TMDBService TMDBService){
        return new WatchlistPresenter(TMDBService);
    }
    @Provides
    BrowseMoviesAdapter provideBrowseMoviesAdapter(){
        return new BrowseMoviesAdapter();
    }
}

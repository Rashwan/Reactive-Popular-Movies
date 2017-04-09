package com.rashwan.reactive_popular_movies.feature.watchlistMovies.injection;

import com.rashwan.reactive_popular_movies.feature.discoverMovies.BrowseMoviesAdapter;
import com.rashwan.reactive_popular_movies.feature.watchlistMovies.WatchlistPresenter;
import com.rashwan.reactive_popular_movies.service.MoviesService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rashwan on 4/9/17.
 */

@Module
public class WatchlistModule {
    @Provides
    WatchlistPresenter provideWatchlistPresenter(MoviesService moviesService){
        return new WatchlistPresenter(moviesService);
    }
    @Provides
    BrowseMoviesAdapter provideBrowseMoviesAdapter(){
        return new BrowseMoviesAdapter();
    }
}

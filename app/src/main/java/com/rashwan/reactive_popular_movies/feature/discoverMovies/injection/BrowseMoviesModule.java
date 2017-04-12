package com.rashwan.reactive_popular_movies.feature.discoverMovies.injection;

import com.rashwan.reactive_popular_movies.feature.discoverMovies.BrowseMoviesAdapter;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.BrowseMoviesPresenter;
import com.rashwan.reactive_popular_movies.service.TMDBService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rashwan on 7/26/16.
 */

@Module
public class BrowseMoviesModule {

    @Provides
    public BrowseMoviesPresenter provideBrowseMoviesPresenter(TMDBService TMDBService){
        return new BrowseMoviesPresenter(TMDBService);
    }
    @Provides
    public BrowseMoviesAdapter provideBrowseMoviesAdapter(){
        return new BrowseMoviesAdapter();
    }
}

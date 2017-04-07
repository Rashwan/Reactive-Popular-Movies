package com.rashwan.reactive_popular_movies.feature.discoverMovies.injection;

import com.rashwan.reactive_popular_movies.feature.discoverMovies.BrowseMoviesAdapter;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.BrowseMoviesPresenter;
import com.rashwan.reactive_popular_movies.service.MoviesService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rashwan on 7/26/16.
 */

@Module
public class BrowseMoviesModule {

    @Provides
    public BrowseMoviesPresenter provideBrowseMoviesPresenter(MoviesService moviesService){
        return new BrowseMoviesPresenter(moviesService);
    }
    @Provides
    public BrowseMoviesAdapter provideBrowseMoviesAdapter(){
        return new BrowseMoviesAdapter();
    }
}

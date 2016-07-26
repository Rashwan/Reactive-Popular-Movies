package com.rashwan.reactive_popular_movies.feature.browseMovies.injection;

import com.rashwan.reactive_popular_movies.feature.browseMovies.BrowseMoviesFragment;
import com.rashwan.reactive_popular_movies.feature.browseMovies.BrowseMoviesPresenter;
import com.rashwan.reactive_popular_movies.service.MoviesService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rashwan on 7/26/16.
 */

@Module
public class BrowseMoviesFragmentModule {
    private BrowseMoviesFragment browseMoviesFragment;


    @Provides
    public BrowseMoviesPresenter BrowseMoviesPresenter(MoviesService moviesService){
        return new BrowseMoviesPresenter(moviesService);
    }
}

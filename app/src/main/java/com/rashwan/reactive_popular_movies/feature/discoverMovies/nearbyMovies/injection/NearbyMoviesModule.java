package com.rashwan.reactive_popular_movies.feature.discoverMovies.nearbyMovies.injection;

import com.rashwan.reactive_popular_movies.feature.discoverMovies.BrowseMoviesAdapter;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.nearbyMovies.NearbyMoviesPresenter;
import com.rashwan.reactive_popular_movies.service.MoviesService;
import com.squareup.moshi.Moshi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rashwan on 3/15/17.
 */
@Module
public class NearbyMoviesModule {
    @Provides
    NearbyMoviesPresenter provideNearbyMoviesPresenter(MoviesService moviesService, Moshi moshi){
        return new NearbyMoviesPresenter(moviesService,moshi);
    }
    @Provides
    BrowseMoviesAdapter provideBrowseMoviesAdapter(){
        return new BrowseMoviesAdapter();
    }
}

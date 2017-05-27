package com.rashwan.reactive_popular_movies.feature.discoverMovies.nearbyMovies.di;

import com.rashwan.reactive_popular_movies.dI.PerFragment;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.nearbyMovies.NearbyMoviesFragment;

import dagger.Subcomponent;

/**
 * Created by rashwan on 3/15/17.
 */

@PerFragment
@Subcomponent(modules = NearbyMoviesModule.class)
public interface NearbyMoviesComponent {
    void inject(NearbyMoviesFragment target);
}

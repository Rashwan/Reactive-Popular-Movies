package com.rashwan.reactive_popular_movies.feature.nearbyMovies.injection;

import com.rashwan.reactive_popular_movies.DI.PerFragment;
import com.rashwan.reactive_popular_movies.feature.nearbyMovies.NearbyMoviesFragment;

import dagger.Subcomponent;

/**
 * Created by rashwan on 3/15/17.
 */

@PerFragment
@Subcomponent(modules = NearbyMoviesModule.class)
public interface NearbyMoviesComponent {
    void inject(NearbyMoviesFragment target);
}

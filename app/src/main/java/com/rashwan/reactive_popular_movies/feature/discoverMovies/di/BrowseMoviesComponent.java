package com.rashwan.reactive_popular_movies.feature.discoverMovies.di;

import com.rashwan.reactive_popular_movies.dI.PerFragment;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.BrowseMoviesFragment;

import dagger.Subcomponent;

/**
 * Created by rashwan on 7/26/16.
 */

@PerFragment
@Subcomponent(modules = {BrowseMoviesModule.class})
public interface BrowseMoviesComponent {
    void inject(BrowseMoviesFragment target);
}

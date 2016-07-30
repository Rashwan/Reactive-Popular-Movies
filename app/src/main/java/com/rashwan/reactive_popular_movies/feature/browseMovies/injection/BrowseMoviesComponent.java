package com.rashwan.reactive_popular_movies.feature.browseMovies.injection;

import com.rashwan.reactive_popular_movies.DI.PerFragment;
import com.rashwan.reactive_popular_movies.feature.browseMovies.BrowseMoviesFragment;

import dagger.Subcomponent;

/**
 * Created by rashwan on 7/26/16.
 */

@PerFragment
@Subcomponent(modules = BrowseMoviesModule.class)
public interface BrowseMoviesComponent {
    void inject(BrowseMoviesFragment target);
}

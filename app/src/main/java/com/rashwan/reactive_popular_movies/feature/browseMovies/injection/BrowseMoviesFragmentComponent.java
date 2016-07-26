package com.rashwan.reactive_popular_movies.feature.browseMovies.injection;

import com.rashwan.reactive_popular_movies.DI.PerFragment;
import com.rashwan.reactive_popular_movies.feature.browseMovies.BrowseMoviesFragment;

import dagger.Subcomponent;

/**
 * Created by rashwan on 7/26/16.
 */

@PerFragment
@Subcomponent(modules = BrowseMoviesFragmentModule.class)
public interface BrowseMoviesFragmentComponent {
    void inject(BrowseMoviesFragment target);
}

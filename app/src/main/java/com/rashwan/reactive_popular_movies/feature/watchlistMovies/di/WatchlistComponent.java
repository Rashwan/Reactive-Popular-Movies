package com.rashwan.reactive_popular_movies.feature.watchlistMovies.di;

import com.rashwan.reactive_popular_movies.dI.PerFragment;
import com.rashwan.reactive_popular_movies.feature.watchlistMovies.WatchlistFragment;

import dagger.Subcomponent;

/**
 * Created by rashwan on 4/9/17.
 */

@PerFragment
@Subcomponent(modules = WatchlistModule.class)
public interface WatchlistComponent {
    void inject(WatchlistFragment target);
}

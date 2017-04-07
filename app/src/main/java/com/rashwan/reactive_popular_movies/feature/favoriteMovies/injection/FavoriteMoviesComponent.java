package com.rashwan.reactive_popular_movies.feature.favoriteMovies.injection;

import com.rashwan.reactive_popular_movies.DI.PerFragment;
import com.rashwan.reactive_popular_movies.feature.favoriteMovies.FavoriteMoviesFragment;

import dagger.Subcomponent;

/**
 * Created by rashwan on 4/7/17.
 */
@PerFragment
@Subcomponent(modules = FavoriteMoviesModule.class)
public interface FavoriteMoviesComponent {
    void inject(FavoriteMoviesFragment target);
}

package com.rashwan.reactive_popular_movies.data;

import com.rashwan.reactive_popular_movies.feature.movieDetails.movieCast.MovieCastFragment;

import dagger.Subcomponent;

/**
 * Created by rashwan on 5/27/17.
 */
@Subcomponent(modules = CastRepositoryModule.class)
public interface CastRepositoryComponent {
    void inject(MovieCastFragment target);
}

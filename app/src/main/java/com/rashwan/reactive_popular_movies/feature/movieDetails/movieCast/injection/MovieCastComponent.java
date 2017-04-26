package com.rashwan.reactive_popular_movies.feature.movieDetails.movieCast.injection;

import com.rashwan.reactive_popular_movies.DI.PerFragment;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieCast.MovieCastFragment;

import dagger.Subcomponent;

/**
 * Created by rashwan on 4/21/17.
 */
@PerFragment
@Subcomponent(modules = MovieCastModule.class)
public interface MovieCastComponent {
    void inject(MovieCastFragment target);
}

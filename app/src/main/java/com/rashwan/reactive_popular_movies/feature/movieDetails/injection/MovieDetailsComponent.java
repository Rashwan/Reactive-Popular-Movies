package com.rashwan.reactive_popular_movies.feature.movieDetails.injection;

import com.rashwan.reactive_popular_movies.DI.PerFragment;
import com.rashwan.reactive_popular_movies.feature.movieDetails.MovieDetailsActivity;

import dagger.Subcomponent;

/**
 * Created by rashwan on 7/26/16.
 */
@PerFragment
@Subcomponent(modules = MovieDetailsModule.class)
public interface MovieDetailsComponent {
    void inject(MovieDetailsActivity target);
}

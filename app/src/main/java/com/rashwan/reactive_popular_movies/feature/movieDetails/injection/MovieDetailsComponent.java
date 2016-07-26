package com.rashwan.reactive_popular_movies.feature.movieDetails.injection;

import com.rashwan.reactive_popular_movies.feature.movieDetails.MovieDetailsFragment;

import dagger.Subcomponent;

/**
 * Created by rashwan on 7/26/16.
 */

@Subcomponent(modules = MovieDetailsModule.class)
public interface MovieDetailsComponent {
    void inject(MovieDetailsFragment target);
}

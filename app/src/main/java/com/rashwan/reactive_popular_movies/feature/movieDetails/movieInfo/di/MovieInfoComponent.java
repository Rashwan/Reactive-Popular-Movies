package com.rashwan.reactive_popular_movies.feature.movieDetails.movieInfo.di;

import com.rashwan.reactive_popular_movies.dI.PerFragment;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieInfo.MovieInfoFragment;

import dagger.Subcomponent;

/**
 * Created by rashwan on 4/20/17.
 */
@PerFragment
@Subcomponent(modules = MovieInfoModule.class)
public interface MovieInfoComponent {
    void inject(MovieInfoFragment target);
}

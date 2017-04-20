package com.rashwan.reactive_popular_movies.feature.movieDetails.movieInfo.injection;

import com.rashwan.reactive_popular_movies.DI.PerFragment;
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

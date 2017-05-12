package com.rashwan.reactive_popular_movies.feature.actorDetails.actorMovies.injection;

import com.rashwan.reactive_popular_movies.DI.PerFragment;
import com.rashwan.reactive_popular_movies.feature.actorDetails.actorMovies.ActorMoviesFragment;

import dagger.Subcomponent;

/**
 * Created by rashwan on 5/12/17.
 */
@PerFragment
@Subcomponent(modules = ActorMoviesModule.class)
public interface ActorMoviesComponent {
    void inject(ActorMoviesFragment target);
}

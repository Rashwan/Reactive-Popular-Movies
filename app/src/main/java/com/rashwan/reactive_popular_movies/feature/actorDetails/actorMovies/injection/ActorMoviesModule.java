package com.rashwan.reactive_popular_movies.feature.actorDetails.actorMovies.injection;

import com.rashwan.reactive_popular_movies.feature.actorDetails.actorMovies.ActorMoviesAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rashwan on 5/12/17.
 */
@Module
public class ActorMoviesModule {
    @Provides
    ActorMoviesAdapter provideActorMoviesAdapter(){
        return new ActorMoviesAdapter();
    }
}

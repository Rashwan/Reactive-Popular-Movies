package com.rashwan.reactive_popular_movies.feature.actorDetails.actorMovies.injection;

import com.rashwan.reactive_popular_movies.feature.actorDetails.actorMovies.ActorMoviesAdapter;
import com.rashwan.reactive_popular_movies.feature.actorDetails.actorMovies.ActorMoviesPresenter;
import com.rashwan.reactive_popular_movies.service.TMDBService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rashwan on 5/12/17.
 */
@Module
public class ActorMoviesModule {
    @Provides
    ActorMoviesPresenter provideActorMoviesPresenter(TMDBService tmdbService){
        return new ActorMoviesPresenter(tmdbService);
    }
    @Provides
    ActorMoviesAdapter provideActorMoviesAdapter(){
        return new ActorMoviesAdapter();
    }
}

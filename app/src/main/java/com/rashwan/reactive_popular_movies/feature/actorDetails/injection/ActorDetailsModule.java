package com.rashwan.reactive_popular_movies.feature.actorDetails.injection;

import com.rashwan.reactive_popular_movies.feature.actorDetails.ActorDetailsPresenter;
import com.rashwan.reactive_popular_movies.service.TMDBService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rashwan on 4/27/17.
 */
@Module
public class ActorDetailsModule {
    @Provides
    ActorDetailsPresenter provideActorDetailsPresenter(TMDBService tmdbService){
        return new ActorDetailsPresenter(tmdbService);
    }

}

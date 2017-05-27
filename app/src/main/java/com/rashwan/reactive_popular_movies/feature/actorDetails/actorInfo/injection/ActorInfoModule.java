package com.rashwan.reactive_popular_movies.feature.actorDetails.actorInfo.injection;

import com.rashwan.reactive_popular_movies.feature.actorDetails.actorInfo.ActorProfileImagesAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rashwan on 5/12/17.
 */
@Module
public class ActorInfoModule {

    @Provides
    ActorProfileImagesAdapter provideActorProfileImagesAdapter(){
        return new ActorProfileImagesAdapter();
    }
}

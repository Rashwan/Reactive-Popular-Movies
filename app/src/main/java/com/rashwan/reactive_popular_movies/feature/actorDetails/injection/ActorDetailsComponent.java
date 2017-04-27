package com.rashwan.reactive_popular_movies.feature.actorDetails.injection;

import com.rashwan.reactive_popular_movies.DI.PerFragment;
import com.rashwan.reactive_popular_movies.feature.actorDetails.ActorDetailsFragment;

import dagger.Subcomponent;

/**
 * Created by rashwan on 4/27/17.
 */
@PerFragment
@Subcomponent(modules = ActorDetailsModule.class)
public interface ActorDetailsComponent {
    void inject(ActorDetailsFragment target);
}

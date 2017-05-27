package com.rashwan.reactive_popular_movies.feature.actorDetails.actorInfo.injection;

import com.rashwan.reactive_popular_movies.dI.PerFragment;
import com.rashwan.reactive_popular_movies.feature.actorDetails.actorInfo.ActorInfoFragment;

import dagger.Subcomponent;

/**
 * Created by rashwan on 5/12/17.
 */
@PerFragment
@Subcomponent(modules = ActorInfoModule.class)
public interface ActorInfoComponent {
    void inject(ActorInfoFragment target);
}

package com.rashwan.reactive_popular_movies.feature.actorDetails;

import com.rashwan.reactive_popular_movies.common.MvpView;
import com.rashwan.reactive_popular_movies.data.model.ActorTaggedImage;

/**
 * Created by rashwan on 5/12/17.
 */

public interface ActorDetailsView extends MvpView{
    void showActorTaggedImage(ActorTaggedImage taggedImage);

}

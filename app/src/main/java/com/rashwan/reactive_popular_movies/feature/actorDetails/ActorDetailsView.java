package com.rashwan.reactive_popular_movies.feature.actorDetails;

import com.rashwan.reactive_popular_movies.common.MvpView;
import com.rashwan.reactive_popular_movies.data.model.CastDetails;

/**
 * Created by rashwan on 4/26/17.
 */

public interface ActorDetailsView extends MvpView {
    void showActorDetails(CastDetails castDetails);
}

package com.rashwan.reactive_popular_movies.feature.actorDetails.actorInfo;

import com.rashwan.reactive_popular_movies.common.MvpView;
import com.rashwan.reactive_popular_movies.data.model.ActorProfileImage;
import com.rashwan.reactive_popular_movies.data.model.CastDetails;

import java.util.List;

/**
 * Created by rashwan on 4/26/17.
 */

public interface ActorInfoView extends MvpView {
    void showActorDetails(CastDetails castDetails);
    void showActorProfileImages(List<ActorProfileImage> profileImages);
    void showActorWithNoBio();
}

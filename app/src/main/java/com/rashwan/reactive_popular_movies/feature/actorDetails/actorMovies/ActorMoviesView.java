package com.rashwan.reactive_popular_movies.feature.actorDetails.actorMovies;

import com.rashwan.reactive_popular_movies.common.MvpView;
import com.rashwan.reactive_popular_movies.data.model.ActorMovie;

import java.util.List;

/**
 * Created by rashwan on 5/12/17.
 */

public interface ActorMoviesView extends MvpView{
    void showActorMovies(List<ActorMovie> actorMovies);
}

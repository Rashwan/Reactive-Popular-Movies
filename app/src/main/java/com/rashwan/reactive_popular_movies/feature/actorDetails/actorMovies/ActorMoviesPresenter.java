package com.rashwan.reactive_popular_movies.feature.actorDetails.actorMovies;

import com.rashwan.reactive_popular_movies.common.BasePresenter;
import com.rashwan.reactive_popular_movies.common.utilities.Utilities;
import com.rashwan.reactive_popular_movies.service.TMDBService;

import rx.Subscription;
import timber.log.Timber;

/**
 * Created by rashwan on 5/12/17.
 */

public class ActorMoviesPresenter extends BasePresenter<ActorMoviesView>{
    private TMDBService tmdbService;
    private Subscription actorMoviesSubscription;
    public ActorMoviesPresenter(TMDBService tmdbService) {
        this.tmdbService = tmdbService;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (actorMoviesSubscription != null && actorMoviesSubscription.isUnsubscribed()){
            actorMoviesSubscription.unsubscribe();
        }
    }
    public void getActorMovies(long castId){
        actorMoviesSubscription = tmdbService.getActorMovies(castId).compose(Utilities.applySchedulers())
                .subscribe(actorMovies -> getView().showActorMovies(actorMovies)
                    ,throwable -> Timber.e(throwable, "error retrieving actor movies")
                    ,() -> Timber.d("finished getting actor movies"));
    }
}

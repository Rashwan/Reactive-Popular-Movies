package com.rashwan.reactive_popular_movies.feature.actorDetails.actorInfo;

import com.rashwan.reactive_popular_movies.common.BasePresenter;
import com.rashwan.reactive_popular_movies.common.utilities.Utilities;
import com.rashwan.reactive_popular_movies.service.TMDBService;

import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by rashwan on 4/26/17.
 */

public class ActorInfoPresenter extends BasePresenter<ActorInfoView> {
    private TMDBService tmdbService;
    private CompositeSubscription detailsSubscription;

    public ActorInfoPresenter(TMDBService tmdbService) {
        this.tmdbService = tmdbService;
        detailsSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        super.detachView();
        detailsSubscription.unsubscribe();
    }
    public void getActorDetails(long castId){
        detailsSubscription.add(tmdbService.getActorDetails(castId).compose(Utilities.applySchedulers())
        .subscribe(castDetails -> getView().showActorDetails(castDetails)
        ,throwable -> {
                    Timber.e(throwable, "error retrieving actor details");
                    getView().showActorWithNoBio();
                }
        ,() -> Timber.d("Finished getting actor details")));
    }

    public void getActorProfileImages(long castId){
        detailsSubscription.add(tmdbService.getActorProfileImages(castId)
        .compose(Utilities.applySchedulers())
        .subscribe(profileImages -> getView().showActorProfileImages(profileImages)
        ,throwable -> Timber.e(throwable,"error retrieving actor profile images")
        ,() -> Timber.d("finished getting actor profile images")));
    }
}
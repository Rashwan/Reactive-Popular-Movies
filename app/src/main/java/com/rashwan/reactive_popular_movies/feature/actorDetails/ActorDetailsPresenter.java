package com.rashwan.reactive_popular_movies.feature.actorDetails;

import com.rashwan.reactive_popular_movies.common.BasePresenter;
import com.rashwan.reactive_popular_movies.common.utilities.Utilities;
import com.rashwan.reactive_popular_movies.service.TMDBService;

import rx.Subscription;
import timber.log.Timber;

/**
 * Created by rashwan on 5/12/17.
 */

public class ActorDetailsPresenter extends BasePresenter<ActorDetailsView>{
    private TMDBService tmdbService;
    private Subscription taggedImagesSubscription;

    public ActorDetailsPresenter(TMDBService tmdbService) {
        this.tmdbService = tmdbService;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (taggedImagesSubscription != null && taggedImagesSubscription.isUnsubscribed())
        taggedImagesSubscription.unsubscribe();
    }

    public void getActorTaggedImages(long castId){
        taggedImagesSubscription = (tmdbService.getActorTaggedImages(castId)
                .compose(Utilities.applySchedulers())
                .subscribe(taggedImages -> {
                            getView().showActorTaggedImage(taggedImages);
                            Timber.d("No of tagged backdrops: %d",taggedImages.size());
                        }

                        ,throwable -> Timber.e(throwable,"error retrieving actor tagged images")
                        ,() -> Timber.d("finished getting actor tagged images")));
    }
}

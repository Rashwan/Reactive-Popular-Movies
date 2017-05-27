package com.rashwan.reactive_popular_movies.feature.actorDetails.actorInfo;

import com.rashwan.reactive_popular_movies.common.BasePresenter;
import com.rashwan.reactive_popular_movies.common.utilities.Utilities;
import com.rashwan.reactive_popular_movies.dI.PerFragment;
import com.rashwan.reactive_popular_movies.data.CastRepository;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by rashwan on 4/26/17.
 */
@PerFragment
public class ActorInfoPresenter extends BasePresenter<ActorInfoView> {
    private CompositeSubscription detailsSubscription;
    private CastRepository castRepository;

    @Inject
    public ActorInfoPresenter(CastRepository castRepository) {
        this.castRepository = castRepository;
        detailsSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        super.detachView();
        detailsSubscription.unsubscribe();
    }
    public void getActorDetails(long castId){
        detailsSubscription.add(castRepository.getActorDetails(castId).compose(Utilities.applySchedulers())
        .subscribe(castDetails -> getView().showActorDetails(castDetails)
        ,throwable -> {
                    Timber.e(throwable, "error retrieving actor details");
                    getView().showActorWithNoBio();
                }
        ,() -> Timber.d("Finished getting actor details")));
    }

    public void getActorProfileImages(long castId){
        detailsSubscription.add(castRepository.getActorProfileImages(castId)
        .compose(Utilities.applySchedulers())
        .subscribe(profileImages -> getView().showActorProfileImages(profileImages)
        ,throwable -> Timber.e(throwable,"error retrieving actor profile images")
        ,() -> Timber.d("finished getting actor profile images")));
    }
}

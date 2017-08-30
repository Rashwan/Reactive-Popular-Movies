package com.rashwan.reactive_popular_movies.feature.actorDetails;

import com.rashwan.reactive_popular_movies.common.BasePresenter;
import com.rashwan.reactive_popular_movies.common.utilities.Utilities;
import com.rashwan.reactive_popular_movies.dI.PerFragment;
import com.rashwan.reactive_popular_movies.data.CastRepository;

import javax.inject.Inject;

import retrofit2.HttpException;
import rx.Subscription;
import timber.log.Timber;

/**
 * Created by rashwan on 5/12/17.
 */
@PerFragment
public class ActorDetailsPresenter extends BasePresenter<ActorDetailsView>{
    private CastRepository castRepository;
    private Subscription taggedImagesSubscription;
    @Inject
    public ActorDetailsPresenter(CastRepository castRepository) {
        this.castRepository = castRepository;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (taggedImagesSubscription != null && taggedImagesSubscription.isUnsubscribed())
        taggedImagesSubscription.unsubscribe();
    }

    public void getActorTaggedImages(long castId){
        taggedImagesSubscription = (castRepository.getActorTaggedImages(castId)
                .compose(Utilities.applySchedulers())
                .subscribe(taggedImages -> {
                            if(!taggedImages.isEmpty()) {
                                getView().showActorTaggedImage(taggedImages);
                                Timber.d("No of tagged backdrops: %d", taggedImages.size());
                            }
                        }
                        ,throwable -> {
                            if (throwable instanceof HttpException) {
                                HttpException httpException = (HttpException) throwable;
                                Timber.e("Error retrieving actor tagged images. Status Code: %d"
                                        ,httpException.code());
                            } else {
                                Timber.e(throwable, "error retrieving actor tagged images");

                            }
                        }
                        ,() -> Timber.d("finished getting actor tagged images")));
    }
}

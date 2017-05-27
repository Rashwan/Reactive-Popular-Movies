package com.rashwan.reactive_popular_movies.feature.actorDetails.actorMovies;

import com.rashwan.reactive_popular_movies.common.BasePresenter;
import com.rashwan.reactive_popular_movies.common.utilities.Utilities;
import com.rashwan.reactive_popular_movies.dI.PerFragment;
import com.rashwan.reactive_popular_movies.data.CastRepository;

import javax.inject.Inject;

import rx.Subscription;
import timber.log.Timber;

/**
 * Created by rashwan on 5/12/17.
 */
@PerFragment
public class ActorMoviesPresenter extends BasePresenter<ActorMoviesView>{
    private CastRepository castRepository;
    private Subscription actorMoviesSubscription;
    @Inject
    public ActorMoviesPresenter(CastRepository castRepository) {
        this.castRepository = castRepository;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (actorMoviesSubscription != null && actorMoviesSubscription.isUnsubscribed()){
            actorMoviesSubscription.unsubscribe();
        }
    }
    public void getActorMovies(long castId){
        actorMoviesSubscription = castRepository.getActorMovies(castId).compose(Utilities.applySchedulers())
                .subscribe(actorMovies -> getView().showActorMovies(actorMovies)
                    ,throwable -> Timber.e(throwable, "error retrieving actor movies")
                    ,() -> Timber.d("finished getting actor movies"));
    }
}

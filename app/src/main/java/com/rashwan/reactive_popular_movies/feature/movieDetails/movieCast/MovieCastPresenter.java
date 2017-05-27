package com.rashwan.reactive_popular_movies.feature.movieDetails.movieCast;

import com.rashwan.reactive_popular_movies.common.BasePresenter;
import com.rashwan.reactive_popular_movies.common.utilities.Exceptions.NoInternetException;
import com.rashwan.reactive_popular_movies.common.utilities.Utilities;
import com.rashwan.reactive_popular_movies.data.CastRepository;

import javax.inject.Inject;

import rx.Subscription;
import timber.log.Timber;

/**
 * Created by rashwan on 4/21/17.
 */

public class MovieCastPresenter extends BasePresenter<MovieCastView> {
    private Subscription castSubscription;
    private CastRepository castRepository;

    @Inject
    public MovieCastPresenter(CastRepository castRepository) {
        this.castRepository = castRepository;
    }

    @Override
    public void detachView() {
        super.detachView();
        castSubscription.unsubscribe();
    }
    public void getMovieCast(long movieId){
        castSubscription = castRepository.getMovieCast(movieId)
                .compose(Utilities.applySchedulers())
                .subscribe(castList -> {
                    getView().hideOfflineLayout();
                    getView().showCast(castList);
                }
                ,throwable -> {
                    if (throwable instanceof NoInternetException){
                        Timber.d("error retrieving movie cast : %s",throwable.getMessage());
                        getView().showOfflineLayout();
                    }else {
                        Timber.d(throwable,"error retrieving movie cast");
                    }
                }
                ,() -> Timber.d("Finished getting movie cast"));
    }
}

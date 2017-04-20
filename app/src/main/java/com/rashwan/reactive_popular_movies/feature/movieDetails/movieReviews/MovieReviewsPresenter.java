package com.rashwan.reactive_popular_movies.feature.movieDetails.movieReviews;

import com.rashwan.reactive_popular_movies.common.BasePresenter;
import com.rashwan.reactive_popular_movies.common.utilities.Exceptions.NoInternetException;
import com.rashwan.reactive_popular_movies.data.model.ReviewResponse;
import com.rashwan.reactive_popular_movies.service.TMDBService;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by rashwan on 4/20/17.
 */

public class MovieReviewsPresenter extends BasePresenter<MovieReviewsView>{
    private TMDBService tmdbService;
    private ReviewResponse mReviewResponse ;
    private Subscription reviewSubscription;

    public MovieReviewsPresenter(TMDBService tmdbService) {
        this.tmdbService = tmdbService;
    }

    @Override
    public void detachView() {
        super.detachView();
        reviewSubscription.unsubscribe();
        mReviewResponse = null;
    }

    public void getReviews(long movieId){
        reviewSubscription = Observable
                .concat(Observable.just(mReviewResponse), tmdbService.getMovieReview(movieId))
                .takeFirst(reviewResponse -> reviewResponse != null).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reviewResponse -> {
                    mReviewResponse = reviewResponse;
                    getView().hideOfflineLayout();
                    if (!reviewResponse.isEmpty()){
                        getView().showReviews(reviewResponse.getReviews());
                        Timber.d(String.valueOf(reviewResponse.getReviews().size()));
                    }else {
                        getView().showNoReviewsMsg();
                        Timber.d("This movie has no Reviews");
                    }
                }
                ,throwable -> {
                    if (throwable instanceof NoInternetException){
                        Timber.d("error retrieving reviews : %s",throwable.getMessage());
                        getView().showOfflineLayout();
                    }else {
                        Timber.d(throwable, "error retrieving reviews");
                    }
                }
                ,() -> Timber.d("Finished getting reviews"));
    }
}

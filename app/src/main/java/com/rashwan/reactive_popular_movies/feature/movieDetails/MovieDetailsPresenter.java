package com.rashwan.reactive_popular_movies.feature.movieDetails;

import com.rashwan.reactive_popular_movies.common.BasePresenter;
import com.rashwan.reactive_popular_movies.model.ReviewResponse;
import com.rashwan.reactive_popular_movies.model.TrailersResponse;
import com.rashwan.reactive_popular_movies.service.MoviesService;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by rashwan on 7/7/16.
 */

public class MovieDetailsPresenter extends BasePresenter<MovieDetailsView> {

    private Subscription trailersSubscription;
    private Subscription reviewsSubscription;
    private MoviesService moviesService;
    private ReviewResponse mReviewResponse;
    private TrailersResponse mTrailersResponse;

    @Inject
    public MovieDetailsPresenter(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (trailersSubscription != null)trailersSubscription.unsubscribe();
        if (reviewsSubscription != null)reviewsSubscription.unsubscribe();

    }

    public void getTrailers(int movieId){
        Observable<TrailersResponse> trailersRequest;
        if (mTrailersResponse != null){
            trailersRequest = Observable.just(mTrailersResponse);
        }else {
            trailersRequest = moviesService.getMovieTrailers(movieId);
        }
        trailersSubscription = trailersRequest.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(trailersResponse ->
                {
                    mTrailersResponse = trailersResponse;
                    getView().showTrailers(trailersResponse.getTrailers());
                    Timber.d(String.valueOf(trailersResponse.getTrailers().size()));
                }
                ,throwable -> Timber.d(throwable,"error retrieving trailers")
                ,() -> Timber.d("Finished getting trailers"));
    }

    public void getReviews(int movieId){
        Observable<ReviewResponse> reviewsRequest;
        if (mReviewResponse != null){
            reviewsRequest = Observable.just(mReviewResponse);
        }else {
            reviewsRequest = moviesService.getMovieReview(movieId).cache();
        }
        reviewsSubscription = reviewsRequest.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(reviewResponse -> {

                    mReviewResponse = reviewResponse;
                    getView().showReviews(reviewResponse.getReviews());
                    Timber.d(String.valueOf(reviewResponse.getReviews().size()));
                }
                ,throwable -> Timber.d(throwable,"error retrieving reviews")
                ,() -> Timber.d("Finished getting reviews"));
    }
}

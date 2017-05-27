package com.rashwan.reactive_popular_movies.feature.movieDetails.movieReviews;

import com.rashwan.reactive_popular_movies.common.BasePresenter;
import com.rashwan.reactive_popular_movies.common.utilities.Exceptions.NoInternetException;
import com.rashwan.reactive_popular_movies.common.utilities.Utilities;
import com.rashwan.reactive_popular_movies.dI.PerFragment;
import com.rashwan.reactive_popular_movies.data.MoviesRepository;
import com.rashwan.reactive_popular_movies.data.model.ReviewResponse;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import timber.log.Timber;

/**
 * Created by rashwan on 4/20/17.
 */
@PerFragment
public class MovieReviewsPresenter extends BasePresenter<MovieReviewsView>{
    private MoviesRepository moviesRepository;
    private ReviewResponse mReviewResponse ;
    private Subscription reviewSubscription;

    @Inject
    public MovieReviewsPresenter(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    @Override
    public void detachView() {
        super.detachView();
        reviewSubscription.unsubscribe();
        mReviewResponse = null;
    }

    public void getReviews(long movieId){
        reviewSubscription = Observable
                .concat(Observable.just(mReviewResponse), moviesRepository.getMovieReview(movieId))
                .takeFirst(reviewResponse -> reviewResponse != null).compose(Utilities.applySchedulers())
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

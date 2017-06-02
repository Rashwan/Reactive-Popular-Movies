package com.rashwan.reactive_popular_movies.feature.movieDetails.movieReviews;

import com.rashwan.reactive_popular_movies.common.BasePresenter;
import com.rashwan.reactive_popular_movies.common.utilities.Exceptions.NoInternetException;
import com.rashwan.reactive_popular_movies.common.utilities.Utilities;
import com.rashwan.reactive_popular_movies.dI.PerFragment;
import com.rashwan.reactive_popular_movies.data.MoviesRepository;

import javax.inject.Inject;

import rx.Subscription;
import timber.log.Timber;

/**
 * Created by rashwan on 4/20/17.
 */
@PerFragment
public class MovieReviewsPresenter extends BasePresenter<MovieReviewsView>{
    private MoviesRepository moviesRepository;
    private Subscription reviewSubscription;

    @Inject
    public MovieReviewsPresenter(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    @Override
    public void detachView() {
        super.detachView();
        reviewSubscription.unsubscribe();
    }

    public void getReviews(long movieId){
        reviewSubscription = moviesRepository.getMovieReview(movieId)
                .compose(Utilities.applySchedulers())
                .subscribe(reviewsList -> {
                    getView().hideOfflineLayout();
                    if (!reviewsList.isEmpty()){
                        getView().showReviews(reviewsList);
                        Timber.d(String.valueOf(reviewsList.size()));
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

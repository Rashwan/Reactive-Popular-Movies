package com.rashwan.reactive_popular_movies.feature.movieDetails;

import com.rashwan.reactive_popular_movies.common.BasePresenter;
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

    private Observable<TrailersResponse> trailersRequest;
    private Subscription trailersSubscription;
    private MoviesService moviesService;
    @Inject
    public MovieDetailsPresenter(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (trailersSubscription != null)trailersSubscription.unsubscribe();
    }

    public void getTrailers(int movieId){
        trailersRequest = moviesService.getMovieTrailers(movieId);

        trailersSubscription = trailersRequest.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(trailersResponse ->
                {
                    getView().showTrailers(trailersResponse.getTrailers());
                    Timber.d(String.valueOf(trailersResponse.getTrailers().size()));
                }
                ,throwable -> Timber.d(throwable,"error retrieving trailers")
                ,() -> Timber.d("Finished getting trailers"));
    }
}

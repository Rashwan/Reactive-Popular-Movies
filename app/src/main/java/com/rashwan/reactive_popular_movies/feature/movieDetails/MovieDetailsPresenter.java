package com.rashwan.reactive_popular_movies.feature.movieDetails;

import com.rashwan.reactive_popular_movies.common.BasePresenter;
import com.rashwan.reactive_popular_movies.common.utilities.Exceptions;
import com.rashwan.reactive_popular_movies.data.MovieDatabaseCrud;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.data.model.ReviewResponse;
import com.rashwan.reactive_popular_movies.data.model.TrailersResponse;
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
    private Subscription movieSubscription;
    private MoviesService moviesService;
    private ReviewResponse mReviewResponse ;
    private TrailersResponse mTrailersResponse ;
    private MovieDatabaseCrud db;

    @Inject
    public MovieDetailsPresenter(MoviesService moviesService, MovieDatabaseCrud db) {
        this.moviesService = moviesService;
        this.db = db;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (trailersSubscription != null)trailersSubscription.unsubscribe();
        if (reviewsSubscription != null)reviewsSubscription.unsubscribe();
        if (movieSubscription != null)movieSubscription.unsubscribe();
    }

    public void getTrailers(long movieId){
        Observable<TrailersResponse> trailersRequest = Observable
                .concat(Observable.just(mTrailersResponse),moviesService.getMovieTrailers(movieId))
                .takeFirst(trailersResponse -> trailersResponse != null ).subscribeOn(Schedulers.io())

                .observeOn(AndroidSchedulers.mainThread());
        trailersSubscription = trailersRequest.subscribe(trailersResponse ->
                {
                    mTrailersResponse = trailersResponse;
                    getView().hideOfflineLayout();
                    if (!trailersResponse.isEmpty()){
                        getView().showTrailers(trailersResponse.getTrailers());
                        Timber.d(String.valueOf(trailersResponse.getTrailers().size()));
                    }else {
                        Timber.d("This movie has no trailers");
                    }

                }
                ,throwable -> {
                            if (throwable instanceof Exceptions.NoInternetException){
                                Timber.d("error retrieving trailers : %s",throwable.getMessage());
                                getView().showOfflineLayout();
                            }else {
                                Timber.d(throwable, "error retrieving trailers");
                            }

                        }
                ,() -> Timber.d("Finished getting trailers"));
    }

    public void getReviews(long movieId){
        Observable<ReviewResponse> reviewsRequest = Observable
                .concat(Observable.just(mReviewResponse),moviesService.getMovieReview(movieId))
                .takeFirst(reviewResponse -> reviewResponse != null).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        reviewsSubscription = reviewsRequest.subscribe(reviewResponse -> {
                    mReviewResponse = reviewResponse;
                    getView().hideOfflineLayout();
                    if (!reviewResponse.isEmpty()){
                        getView().showReviews(reviewResponse.getReviews());
                        Timber.d(String.valueOf(reviewResponse.getReviews().size()));
                    }else {
                        Timber.d("This movie has no Reviews");
                    }
                }
                ,throwable -> {
                        if (throwable instanceof Exceptions.NoInternetException){
                            Timber.d("error retrieving reviews : %s",throwable.getMessage());
                            getView().showOfflineLayout();
                        }else {
                            Timber.d(throwable, "error retrieving reviews");
                        }
                        }
                ,() -> Timber.d("Finished getting reviews"));
    }

    public void isMovieFavorite(Long movieId){
        Observable<Boolean> favoriteObservable = db.isMovieFavorite(movieId).observeOn(AndroidSchedulers.mainThread());
        movieSubscription =  favoriteObservable.subscribe(favorite ->  {
                    if (favorite) {
                        Timber.d("A Favorite!");
                        getView().showFavoriteMovie();
                    }else {
                        Timber.d("Not A Favorite!");
                        getView().showNormalMovie();
                    }
                }
                ,throwable -> Timber.d("Error getting movie state")
                ,() -> Timber.d("finished querying if movie is favorite"));

    }

    public void addMovieToFavorites(Movie movie){
        db.insert(movie.id(),movie.title(),movie.release_date(),movie.vote_average()
        ,movie.overview(),movie.poster_path(),movie.backdrop_path());

    }
    public void removeMovieFromFavorites(Long movieId){
        db.delete(movieId);
    }
}

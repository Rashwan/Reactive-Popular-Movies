package com.rashwan.reactive_popular_movies.feature.movieDetails;

import com.rashwan.reactive_popular_movies.common.BasePresenter;
import com.rashwan.reactive_popular_movies.common.utilities.Exceptions;
import com.rashwan.reactive_popular_movies.data.MovieDatabaseHelper;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.data.model.ReviewResponse;
import com.rashwan.reactive_popular_movies.data.model.TrailersResponse;
import com.rashwan.reactive_popular_movies.service.MoviesService;
import com.squareup.sqlbrite.BriteDatabase;

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
    MovieDatabaseHelper movieDatabaseHelper;
    BriteDatabase db;

    @Inject
    public MovieDetailsPresenter(MoviesService moviesService,MovieDatabaseHelper databaseHelper,BriteDatabase db) {
        this.moviesService = moviesService;
        this.movieDatabaseHelper = databaseHelper;
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
                .takeFirst(trailersResponse -> trailersResponse != null );
        trailersSubscription = trailersRequest.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trailersResponse ->
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
                .takeFirst(reviewResponse -> reviewResponse != null);
        reviewsSubscription = reviewsRequest.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reviewResponse -> {
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
        Observable<Movie> movieObservable = movieDatabaseHelper.getMovie(db,movieId);
        movieSubscription = movieObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(movie ->  getView().showFavoriteMovie()
                        ,throwable -> Timber.d("Not a Favorite")
                        ,() -> Timber.d("finished querying if movie is favorite"));
    }

    public void addMovieToFavorites(Movie movie){
        movieDatabaseHelper.insert(db,movie.id(),movie.title(),movie.release_date(),movie.vote_average()
        ,movie.overview(),movie.poster_path(),movie.backdrop_path());
        getView().showFavoriteMovie();
    }
    public void removeMovieFromFavorites(Long movieId){
        movieDatabaseHelper.delete(db,movieId);
        getView().showNormalMovie();
    }
}

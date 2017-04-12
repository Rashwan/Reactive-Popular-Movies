package com.rashwan.reactive_popular_movies.feature.movieDetails;

import android.net.Uri;

import com.rashwan.reactive_popular_movies.common.BasePresenter;
import com.rashwan.reactive_popular_movies.common.utilities.Exceptions;
import com.rashwan.reactive_popular_movies.data.MovieDatabaseCrud;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.data.model.ReviewResponse;
import com.rashwan.reactive_popular_movies.data.model.Trailer;
import com.rashwan.reactive_popular_movies.service.TMDBService;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by rashwan on 7/7/16.
 */

public class MovieDetailsPresenter extends BasePresenter<MovieDetailsView> {

    private CompositeSubscription detailsSubscription = new CompositeSubscription();
    private TMDBService TMDBService;
    private ReviewResponse mReviewResponse ;
    private Uri officialTrailerUri;
    private MovieDatabaseCrud db;

    public MovieDetailsPresenter(TMDBService TMDBService, MovieDatabaseCrud db) {
        this.TMDBService = TMDBService;
        this.db = db;
    }

    @Override
    public void detachView() {
        super.detachView();
        detailsSubscription.unsubscribe();
    }

    public void getMovieDetails(long movieId){
        Observable<Movie> movieDetailsRequest = TMDBService.getMovieDetails(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        detailsSubscription.add(movieDetailsRequest.subscribe(movie -> getView().showMovieDetails(movie)
        ,throwable -> {
                if (throwable instanceof Exceptions.NoInternetException){
                    Timber.d("error retrieving movie details : %s",throwable.getMessage());
                    getView().showOfflineLayout();
                }else {
                    Timber.d(throwable, "error retrieving movie details");
                }
            }
            ,() -> Timber.d("Finished getting movie details")));
    }


    public void getTrailers(long movieId){
        Observable<List<Trailer>> trailersRequest = TMDBService.getMovieTrailers(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        detailsSubscription.add(trailersRequest.subscribe(trailersList ->
                {
                    getView().hideOfflineLayout();
                    if (!trailersList.isEmpty()){
                        getView().showTrailers(trailersList);
                        Observable.from(trailersList)
                                .filter(trailer -> trailer.type().equals("Trailer"))
                                .first().subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(trailer -> {
                                        officialTrailerUri = trailer.getFullYoutubeUri();
                                        getView().showPlayTrailerButton();
                                    }
                                    ,throwable -> Timber.d("error searching for official trailers")
                                    ,() -> Timber.d("finished searching for official trailers"));
                        Timber.d(String.valueOf(trailersList.size()));
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
                ,() -> Timber.d("Finished getting trailers")));
    }

    public void getReviews(long movieId){
        Observable<ReviewResponse> reviewsRequest = Observable
                .concat(Observable.just(mReviewResponse), TMDBService.getMovieReview(movieId))
                .takeFirst(reviewResponse -> reviewResponse != null).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        detailsSubscription.add(reviewsRequest.subscribe(reviewResponse -> {
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
                ,() -> Timber.d("Finished getting reviews")));
    }

    public void isMovieFavorite(Long movieId){
        Observable<Boolean> favoriteObservable = TMDBService.isMovieFavorite(movieId)
                .observeOn(AndroidSchedulers.mainThread());
        detailsSubscription.add(favoriteObservable.subscribe(favorite ->  {
                    if (favorite) {
                        Timber.d("A Favorite!");
                        getView().showFavoriteMovie();
                    }else {
                        Timber.d("Not A Favorite!");
                        getView().showNonFavoriteMovie();
                    }
                }
                ,throwable -> Timber.d("Error getting movie state")
                ,() -> Timber.d("finished querying if movie is favorite")));

    }

    public void addMovieToFavorites(Movie movie){
        Observable<Long> findMovieObservable = TMDBService.findMovieByID(movie.id())
                .observeOn(AndroidSchedulers.mainThread());
        detailsSubscription.add(findMovieObservable.subscribe(movieDbId -> {
                    if (movieDbId != -1L) {
                        Timber.d("We already have this movie id DB so we only update its fav state");
                        db.updateFavorite(true, movie.id());
                    }else {
                        Timber.d("We don't have this movie in DB so we insert it with fav state true");
                        db.insertMovie(movie.id(),movie.title(),movie.release_date(),movie.vote_average(),
                                movie.overview(),movie.poster_path(),movie.backdrop_path(),movie.runtime(),
                                true,movie.is_watchlist());
                    }
                }
                ,throwable -> Timber.d(throwable,"Error searching for movie in DB")
                ,() -> Timber.d("Find movies completed")));

    }
    public void removeMovieFromFavorites(Long movieId){
        if (db.updateFavorite(false,movieId) == 1){
            if (db.deleteMovie(false,false)==1){
                Timber.d("Cleaned up movie");
            }else {
                Timber.d("This movie is also in watchlist so we won't delete it");
            }
        }else {
            Timber.d("failed to update movie");
        }

    }

    public void isMovieInWatchlist(Long movieId){
        Observable<Boolean> watchListObservable = TMDBService.isMovieInWatchlist(movieId)
                .observeOn(AndroidSchedulers.mainThread());
        detailsSubscription.add(watchListObservable.subscribe(inWatchlist ->  {
                    if (inWatchlist) {
                        Timber.d("In Watchlist!");
                        getView().showWatchlistMovie();
                    }else {
                        Timber.d("Not in Watchlist!");
                        getView().showNormalMovie();
                    }
                }
                ,throwable -> Timber.d("Error getting movie state")
                ,() -> Timber.d("finished querying if movie is in watchlist")));
    }

    public void addMovieToWatchlist(Movie movie){
        Observable<Long> findMovieObservable = TMDBService.findMovieByID(movie.id())
                .observeOn(AndroidSchedulers.mainThread());
        detailsSubscription.add(findMovieObservable.subscribe(movieDbId -> {
                if (movieDbId != -1L) {
                    Timber.d("We already have this movie id DB so we only update its watchlist state");
                    db.updateWatchlist(true, movie.id());
                }else {
                    Timber.d("We don't have this movie in DB so we insert it with watchlist state true");
                    db.insertMovie(movie.id(),movie.title(),movie.release_date(),movie.vote_average(),
                            movie.overview(),movie.poster_path(),movie.backdrop_path(),movie.runtime(),
                            movie.is_favorite(),true);
                }
            }
            ,throwable -> Timber.d(throwable,"Error searching for movie in DB")
            ,() -> Timber.d("Find movies completed")));

    }
    public void removeMovieFromWatchlist(Long movieId) {

        if (db.updateWatchlist(false,movieId) == 1){
            if (db.deleteMovie(false,false) == 1){
                Timber.d("Cleaned up movie");
            }else {
                Timber.d("This movie is also in favorites so we won't delete it");
            }
        }else {
            Timber.d("failed to update movie");
        }
    }

    public Uri getOfficialTrailerUri() {
        return officialTrailerUri;
    }
}


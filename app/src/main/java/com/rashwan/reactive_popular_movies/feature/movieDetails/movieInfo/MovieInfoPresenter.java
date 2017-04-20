package com.rashwan.reactive_popular_movies.feature.movieDetails.movieInfo;

import android.net.Uri;

import com.rashwan.reactive_popular_movies.common.BasePresenter;
import com.rashwan.reactive_popular_movies.common.utilities.Exceptions.NoInternetException;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.data.model.Trailer;
import com.rashwan.reactive_popular_movies.service.OMDBService;
import com.rashwan.reactive_popular_movies.service.TMDBService;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by rashwan on 4/20/17.
 */

public class MovieInfoPresenter extends BasePresenter<MovieInfoView>{
    private TMDBService tmdbService;
    private OMDBService omdbService;
    private Uri officialTrailerUri;
    private CompositeSubscription detailsSubscription;

    public MovieInfoPresenter(TMDBService tmdbService, OMDBService omdbService) {
        this.tmdbService = tmdbService;
        this.omdbService = omdbService;
        detailsSubscription = new CompositeSubscription();
    }


    private Subscription createOmdbDetailsObservable(String tmdbId){
        return omdbService.getMovieDetails(tmdbId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        movieDetails -> getView().showOmdbDetails(movieDetails)
                        ,throwable -> {
                            if (throwable instanceof NoInternetException) {
                                Timber.d("error retrieving movie details : %s", throwable.getMessage());
                                getView().showOfflineLayout();
                            } else {
                                Timber.d(throwable, "error retrieving movie details");
                            }
                        }
                        ,() -> Timber.d("Finished getting Omdb details"));
    }


    public void getMovieDetails(long movieId){
        Observable<Movie> movieDetailsRequest = tmdbService.getMovieDetails(movieId)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        detailsSubscription.add(movieDetailsRequest.subscribe(movie -> {
                    getView().showMovieRuntime(movie.getFormattedRuntime(movie.runtime()));
                    createOmdbDetailsObservable(movie.imdb_id());
                }
                ,throwable -> {
                    if (throwable instanceof NoInternetException){
                        Timber.d("error retrieving movie details : %s",throwable.getMessage());
                        getView().showOfflineLayout();
                    }else {
                        Timber.d(throwable, "error retrieving movie details");
                    }
                }
                ,() -> Timber.d("Finished getting movie details")));
    }

    public void getTrailers(long movieId){
        Observable<List<Trailer>> trailersRequest = tmdbService.getMovieTrailers(movieId)
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
//                                            getView().showPlayTrailerButton();
                                        }
                                        ,throwable -> Timber.d("error searching for official trailers")
                                        ,() -> Timber.d("finished searching for official trailers"));
                        Timber.d(String.valueOf(trailersList.size()));
                    }else {
                        Timber.d("This movie has no trailers");
                    }
                }
                ,throwable -> {
                    if (throwable instanceof NoInternetException){
                        Timber.d("error retrieving trailers : %s",throwable.getMessage());
                                getView().showOfflineLayout();
                    }else {
                        Timber.d(throwable, "error retrieving trailers");
                    }
                }
                ,() -> Timber.d("Finished getting trailers")));
    }

    public void getSimilarMovies(long movieId){
        detailsSubscription.add(tmdbService.getSimilarMovies(movieId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        movies -> getView().showSimilarMovies(movies)
                        , throwable -> {
                            if (throwable instanceof NoInternetException) {
                                NoInternetException exception = (NoInternetException) throwable;
                                Timber.d("Error retrieving movies: %s . First page: %s", exception.message, exception.firstPage);
                                getView().showOfflineLayout();
                            } else {
                                Timber.d(throwable, "Error retrieving similar movies");
                            }
                        }, () -> Timber.d("finished getting similar movies")
                ));

    }


}

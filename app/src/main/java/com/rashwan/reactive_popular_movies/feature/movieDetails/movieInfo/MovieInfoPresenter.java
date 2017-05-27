package com.rashwan.reactive_popular_movies.feature.movieDetails.movieInfo;

import com.rashwan.reactive_popular_movies.common.BasePresenter;
import com.rashwan.reactive_popular_movies.common.utilities.Exceptions.NoInternetException;
import com.rashwan.reactive_popular_movies.common.utilities.Utilities;
import com.rashwan.reactive_popular_movies.dI.PerFragment;
import com.rashwan.reactive_popular_movies.data.MoviesRepository;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.data.model.Trailer;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by rashwan on 4/20/17.
 */
@PerFragment
public class MovieInfoPresenter extends BasePresenter<MovieInfoView>{
    private MoviesRepository moviesRepository;
    private CompositeSubscription detailsSubscription;

    @Inject
    public MovieInfoPresenter(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
        detailsSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        super.detachView();
        detailsSubscription.unsubscribe();
    }

    private Subscription createOmdbDetailsObservable(String tmdbId){
        return moviesRepository.getMovieOMDBDetails(tmdbId).compose(Utilities.applySchedulers()).subscribe(
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
        Observable<Movie> movieDetailsRequest = moviesRepository.getMovieDetails(movieId)
               .compose(Utilities.applySchedulers());
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
        Observable<List<Trailer>> trailersRequest = moviesRepository.getMovieTrailers(movieId)
               .compose(Utilities.applySchedulers());
        detailsSubscription.add(trailersRequest.subscribe(trailersList ->
                {
                    getView().hideOfflineLayout();
                    if (!trailersList.isEmpty()){
                        getView().showTrailers(trailersList);
                        Observable.from(trailersList)
                                .filter(trailer -> trailer.type().equals("Trailer"))
                                .first().compose(Utilities.applySchedulers())
                                .subscribe(trailer -> {
                                            getView().showShareIcon(trailer.getFullYoutubeUri().toString());
                                            getView().showPlayMainTrailer(trailer.getFullYoutubeUri());
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
        detailsSubscription.add(moviesRepository.getSimilarMovies(movieId).compose(Utilities.applySchedulers())
                .subscribe(
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

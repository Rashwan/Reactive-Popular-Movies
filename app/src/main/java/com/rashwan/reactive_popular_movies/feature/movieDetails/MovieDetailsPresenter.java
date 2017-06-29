package com.rashwan.reactive_popular_movies.feature.movieDetails;

import com.rashwan.reactive_popular_movies.common.BasePresenter;
import com.rashwan.reactive_popular_movies.data.MoviesRepository;
import com.rashwan.reactive_popular_movies.data.local.MovieDatabaseCrud;
import com.rashwan.reactive_popular_movies.data.model.Movie;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by rashwan on 7/7/16.
 */

public class MovieDetailsPresenter extends BasePresenter<MovieDetailsView> {

    private CompositeSubscription detailsSubscription = new CompositeSubscription();
    private MoviesRepository moviesRepository;
    private MovieDatabaseCrud db;

    @Inject
    public MovieDetailsPresenter(MoviesRepository moviesRepository, MovieDatabaseCrud db) {
        this.moviesRepository = moviesRepository;
        this.db = db;
    }

    @Override
    public void detachView() {
        super.detachView();
        detailsSubscription.unsubscribe();
    }


    public void isMovieFavorite(Long movieId){
        Observable<Boolean> favoriteObservable = moviesRepository.isMovieFavorite(movieId)
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
                ,throwable -> Timber.d(throwable,"Error getting movie state")
                ,() -> Timber.d("finished querying if movie is favorite")));

    }

    public void addMovieToFavorites(Movie movie){
        Observable<Long> findMovieObservable = moviesRepository.findMovieByID(movie.id())
                .observeOn(AndroidSchedulers.mainThread());
        detailsSubscription.add(findMovieObservable.subscribe(movieDbId -> {
                    if (movieDbId != -1L) {
                        Timber.d("We already have this movie id DB so we only update its fav state");
                        db.updateFavorite(true, movie.id());
                    }else {
                        Timber.d("We don't have this movie in DB so we insert it with fav state true");
                        db.insertMovie(movie.id(),movie.title(),movie.releaseDate(),
                                movie.overview(),movie.posterPath(),movie.backdropPath(),movie.runtime(),
                                true,false);
                    }
                }
                ,throwable -> Timber.d(throwable,"Error searching for movie in DB")
                ,() -> Timber.d("Find movies completed")));

    }
    public void removeMovieFromFavorites(Long movieId){
        if (db.updateFavorite(false,movieId) == 1){
            if (db.deleteMovie()==1){
                Timber.d("Cleaned up movie");
            }else {
                Timber.d("This movie is also in watchlist so we won't delete it");
            }
        }else {
            Timber.d("failed to update movie");
        }

    }

    public void isMovieInWatchlist(Long movieId){
        Observable<Boolean> watchListObservable = moviesRepository.isMovieInWatchlist(movieId)
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
                ,throwable -> Timber.d(throwable,"Error getting movie state")
                ,() -> Timber.d("finished querying if movie is in watchlist")));
    }

    public void addMovieToWatchlist(Movie movie){
        Observable<Long> findMovieObservable = moviesRepository.findMovieByID(movie.id())
                .observeOn(AndroidSchedulers.mainThread());
        detailsSubscription.add(findMovieObservable.subscribe(movieDbId -> {
                if (movieDbId != -1L) {
                    Timber.d("We already have this movie id DB so we only update its watchlist state");
                    db.updateWatchlist(true, movie.id());
                }else {
                    Timber.d("We don't have this movie in DB so we insert it with watchlist state true");
                    db.insertMovie(movie.id(),movie.title(),movie.releaseDate(),
                            movie.overview(),movie.posterPath(),movie.backdropPath(),movie.runtime(),
                            false,true);
                }
            }
            ,throwable -> Timber.d(throwable,"Error searching for movie in DB")
            ,() -> Timber.d("Find movies completed")));

    }
    public void removeMovieFromWatchlist(Long movieId) {

        if (db.updateWatchlist(false,movieId) == 1){
            if (db.deleteMovie() == 1){
                Timber.d("Cleaned up movie");
            }else {
                Timber.d("This movie is also in favorites so we won't delete it");
            }
        }else {
            Timber.d("failed to update movie");
        }
    }

}


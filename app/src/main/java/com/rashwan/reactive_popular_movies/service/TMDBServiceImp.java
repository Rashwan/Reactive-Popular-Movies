package com.rashwan.reactive_popular_movies.service;

import android.app.Application;
import android.database.Cursor;

import com.rashwan.reactive_popular_movies.MovieModel;
import com.rashwan.reactive_popular_movies.common.utilities.Exceptions;
import com.rashwan.reactive_popular_movies.common.utilities.Utilities;
import com.rashwan.reactive_popular_movies.data.TMDBApi;
import com.rashwan.reactive_popular_movies.data.model.ActorMovie;
import com.rashwan.reactive_popular_movies.data.model.ActorMoviesResponse;
import com.rashwan.reactive_popular_movies.data.model.ActorProfileImage;
import com.rashwan.reactive_popular_movies.data.model.ActorTaggedImage;
import com.rashwan.reactive_popular_movies.data.model.Cast;
import com.rashwan.reactive_popular_movies.data.model.CastDetails;
import com.rashwan.reactive_popular_movies.data.model.CreditsResponse;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.data.model.MoviesResponse;
import com.rashwan.reactive_popular_movies.data.model.ReviewResponse;
import com.rashwan.reactive_popular_movies.data.model.Trailer;
import com.rashwan.reactive_popular_movies.data.model.TrailersResponse;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqldelight.SqlDelightStatement;

import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by rashwan on 6/23/16.
 */

public class TMDBServiceImp implements TMDBService {
    private Retrofit retrofit;
    private Application application;
    private BriteDatabase db;

    public TMDBServiceImp(Application application, Retrofit retrofit, BriteDatabase db) {
        this.retrofit = retrofit;
        this.application = application;
        this.db = db;
    }

    @Override
    public Observable<List<Movie>> getPopularMovies(int page) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException(page == 1,"No internet connection"));
        }
        return retrofit.create(TMDBApi.class).getPopularMovies(page)
                .map(MoviesResponse::getMovies);
    }

    @Override
    public Observable<List<Movie>> getTopRatedMovies(int page) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException(page == 1,"No internet connection"));
        }
        return retrofit.create(TMDBApi.class).getTopRatedMovies(page)
                .map(MoviesResponse::getMovies);
    }

    @Override
    public Observable<List<Movie>> getUpcomingMovies(int page) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException(page == 1,"No internet connection"));
        }
        return retrofit.create(TMDBApi.class).getUpcomingMovies(page,"US")
                .map(MoviesResponse::getMovies);
    }



    @Override
    public Observable<List<Trailer>> getMovieTrailers(long id) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException("No internet connection"));
        }
        return retrofit.create(TMDBApi.class).getMovieTrailers(id)
                .map(TrailersResponse::getTrailers);
    }

    @Override
    public Observable<ReviewResponse> getMovieReview(long id) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException("No internet connection"));
        }
        return retrofit.create(TMDBApi.class).getMovieReviews(id);
    }

    @Override
    public Observable<List<Movie>> getSimilarMovies(long id) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException("No internet connection"));
        }
        return retrofit.create(TMDBApi.class).getSimilarMovies(id)
                .map((moviesResponse) -> moviesResponse.getMovies().subList(0,7));
    }

    @Override
    public Observable<Movie> getMovieDetails(long id) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException("No internet connection"));
        }

        return retrofit.create(TMDBApi.class).getMovieDetails(id);
    }

    @Override
    public Observable<Movie> getMovieById(Long id) {
        return db.createQuery(MovieModel.TABLE_NAME,Movie.FACTORY.select_movie_by_id(id).statement)
                .mapToOne(Movie.SELECT_MOVIE_BY_ID_MAPPER::map);
    }

    @Override
    public Observable<Long> findMovieByID(Long movieId) {
        return db.createQuery(MovieModel.TABLE_NAME,Movie.FACTORY.find_movie_by_id(movieId).statement)
                .mapToOneOrDefault(Movie.FIND_MOVIE_BY_ID_MAPPER::map,-1L).take(1);
    }

    @Override
    public Observable<List<Movie>> getFavoriteMovies() {
        return db.createQuery(MovieModel.TABLE_NAME
                , Movie.FACTORY.select_favorite_movies().statement)
                .mapToList(Movie.SELECT_FAVORITES_MAPPER::map);
    }



    @Override
    public Observable<List<Long>> getFavoriteMoviesIds() {
        return db.createQuery(MovieModel.TABLE_NAME
                , Movie.FACTORY.select_favorite_movies_ids().statement)
                .mapToList(Movie.SELECT_FAVORITES_IDS_MAPPER::map);
    }

    @Override
    public Observable<Boolean> isMovieFavorite(Long movieId){
        SqlDelightStatement statement = Movie.FACTORY.is_movie_favorite(movieId);
        return db.createQuery(MovieModel.TABLE_NAME,statement.statement,statement.args)
                .map(query -> {
                    Cursor cursor = query.run();
                    return cursor.moveToNext();
                });
    }

    @Override
    public Observable<List<Movie>> getWatchlistMovies() {
        return db.createQuery(MovieModel.TABLE_NAME, Movie.FACTORY.select_watchlist_movies().statement)
                .mapToList(Movie.SELECT_WATCHLIST_MAPPER::map);
    }

    @Override
    public Observable<Boolean> isMovieInWatchlist(Long movieId){
        SqlDelightStatement statement = Movie.FACTORY.is_movie_in_watchlist(movieId);
        return db.createQuery(MovieModel.TABLE_NAME,statement.statement,statement.args)
                .map(query -> {
                    Cursor cursor = query.run();
                    return cursor.moveToNext();
                });
    }

    @Override
    public Observable<List<Cast>> getMovieCast(long movieId) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException("No internet connection"));
        }
        return retrofit.create(TMDBApi.class).getMovieCredits(movieId)
                .map(CreditsResponse::cast);
    }

    @Override
    public Observable<CastDetails> getActorDetails(long castId) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException("No internet connection"));
        }
        return retrofit.create(TMDBApi.class).getActorDetails(castId);
    }

    @Override
    public Observable<List<ActorTaggedImage>> getActorTaggedImages(long castId) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException("No internet connection"));
        }
         return retrofit.create(TMDBApi.class).getActorTaggedImaged(castId)
             .flatMap((actorTaggedImagesResponse) ->
                    Observable.from(actorTaggedImagesResponse.taggedImages()))
             .filter(actorTaggedImage -> actorTaggedImage.imageType().equals("backdrop"))
             .toList();
    }

    @Override
    public Observable<List<ActorProfileImage>> getActorProfileImages(long castId) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException("No internet connection"));
        }
        return retrofit.create(TMDBApi.class).getActorProfileImages(castId)
                .flatMap(actorProfileImagesResponse ->
                        Observable.from(actorProfileImagesResponse.actorProfileImages()))
                .take(7)
                .toList();
    }

    @Override
    public Observable<List<ActorMovie>> getActorMovies(long castId) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException("No internet connection"));
        }
        return retrofit.create(TMDBApi.class).getActorMovies(castId)
                .map(ActorMoviesResponse::actorMovies);
    }

}

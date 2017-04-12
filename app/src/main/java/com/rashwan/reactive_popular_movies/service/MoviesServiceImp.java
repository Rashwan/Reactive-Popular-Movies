package com.rashwan.reactive_popular_movies.service;

import android.app.Application;
import android.database.Cursor;

import com.rashwan.reactive_popular_movies.MovieModel;
import com.rashwan.reactive_popular_movies.common.utilities.Exceptions;
import com.rashwan.reactive_popular_movies.common.utilities.Utilities;
import com.rashwan.reactive_popular_movies.data.TMDBApi;
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

public class MoviesServiceImp implements MoviesService{
    private Retrofit retrofit;
    private Application application;
    private BriteDatabase db;

    public MoviesServiceImp(Application application, Retrofit retrofit, BriteDatabase db) {
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
//        return db.createQuery(FavoriteMoviesModel.TABLE_NAME, Movie.FAVORITES_FACTORY.select_all_movies().statement)
//                .mapToList(Movie.FAVORITE_MOVIES_MAPPER::map);
        return db.createQuery(MovieModel.TABLE_NAME
                , Movie.FACTORY.select_favorite_movies().statement)
                .mapToList(Movie.SELECT_FAVORITES_MAPPER::map);
    }



    @Override
    public Observable<List<Long>> getFavoriteMoviesIds() {
//        return db.createQuery(FavoriteMoviesModel.TABLE_NAME, Movie.FAVORITES_FACTORY.select_all_movies_ids().statement)
//                .mapToList(Movie.FAVORITE_MOVIES_IDS_MAPPER::map);
        return db.createQuery(MovieModel.TABLE_NAME
                , Movie.FACTORY.select_favorite_movies_ids().statement)
                .mapToList(Movie.SELECT_FAVORITES_IDS_MAPPER::map);
    }

    @Override
    public Observable<Boolean> isMovieFavorite(Long movieId){
//        SqlDelightStatement statement = Movie.FAVORITES_FACTORY.select_by_movie_id(movieId);
//        return db.createQuery(FavoriteMoviesModel.TABLE_NAME, statement.statement, statement.args)
//                .map(query -> {
//                    Cursor cursor =  query.run();
//                    return cursor.moveToNext();
//                });
        SqlDelightStatement statement = Movie.FACTORY.is_movie_favorite(movieId);
        return db.createQuery(MovieModel.TABLE_NAME,statement.statement,statement.args)
                .map(query -> {
                    Cursor cursor = query.run();
                    return cursor.moveToNext();
                });
    }

    @Override
    public Observable<List<Movie>> getWatchlistMovies() {
//        return db.createQuery(WatchlistMoviesModel.TABLE_NAME, Movie.WATCHLIST_FACTORY.select_all_movies().statement)
//                .mapToList(Movie.WATCHLIST_MOVIES_MAPPER::map);
        return db.createQuery(MovieModel.TABLE_NAME, Movie.FACTORY.select_watchlist_movies().statement)
                .mapToList(Movie.SELECT_WATCHLIST_MAPPER::map);
    }

    @Override
    public Observable<Boolean> isMovieInWatchlist(Long movieId){
//        SqlDelightStatement statement = Movie.WATCHLIST_FACTORY.select_by_movie_id(movieId);
//        return db.createQuery(WatchlistMoviesModel.TABLE_NAME, statement.statement, statement.args)
//                .map(query -> {
//                    Cursor cursor =  query.run();
//                    return cursor.moveToNext();
//                });
        SqlDelightStatement statement = Movie.FACTORY.is_movie_in_watchlist(movieId);
        return db.createQuery(MovieModel.TABLE_NAME,statement.statement,statement.args)
                .map(query -> {
                    Cursor cursor = query.run();
                    return cursor.moveToNext();
                });
    }

}

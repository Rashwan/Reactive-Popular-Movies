package com.rashwan.reactive_popular_movies.data.local;

import android.database.Cursor;

import com.rashwan.reactive_popular_movies.MovieModel;
import com.rashwan.reactive_popular_movies.data.MoviesDataSource;
import com.rashwan.reactive_popular_movies.data.di.qualifier.Local;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.data.model.MovieDB;
import com.rashwan.reactive_popular_movies.data.model.MovieDetails;
import com.rashwan.reactive_popular_movies.data.model.Review;
import com.rashwan.reactive_popular_movies.data.model.Trailer;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqldelight.SqlDelightStatement;

import java.util.List;

import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by rashwan on 5/25/17.
 */
@Singleton
@Local
public class MoviesLocalDataSource implements MoviesDataSource {

    private BriteDatabase db;

    public MoviesLocalDataSource(BriteDatabase db) {
        this.db = db;
    }


    @Override
    public Observable<List<Movie>> getPopularMovies(int page) {
        return null;
    }

    @Override
    public Observable<List<Movie>> getTopRatedMovies(int page) {
        return null;
    }

    @Override
    public Observable<List<Movie>> getUpcomingMovies(int page) {
        return null;
    }

    @Override
    public Observable<List<Trailer>> getMovieTrailers(long id) {
        return null;
    }

    @Override
    public Observable<List<Review>> getMovieReview(long id) {
        return null;
    }

    @Override
    public Observable<List<Movie>> getSimilarMovies(long id) {
        return null;
    }

    @Override
    public Observable<Movie> getMovieDetails(long id) {
        return null;
    }

    @Override
    public Observable<MovieDetails> getMovieOMDBDetails(String tmdbId) {
        return null;
    }

    @Override
    public Observable<Movie> getMovieById(Long id) {
        return db.createQuery(MovieModel.TABLE_NAME, MovieDB.FACTORY.select_movie_by_id(id).statement)
                .mapToOne(MovieDB.SELECT_MOVIE_BY_ID_MAPPER::map)
                .map(movieDB -> Movie.create(movieDB.movie_id(),movieDB.imdb_id(),movieDB.title()
                        ,movieDB.overview(),movieDB.runtime(),movieDB.tmdb_rating(),movieDB.release_date()
                        ,movieDB.backdrop_path(),movieDB.poster_path()));
    }

    @Override
    public Observable<Long> findMovieByID(Long movieId) {
        return db.createQuery(MovieModel.TABLE_NAME, MovieDB.FACTORY.find_movie_by_id(movieId).statement)
                .mapToOneOrDefault(MovieDB.FIND_MOVIE_BY_ID_MAPPER::map, -1L).take(1);
    }

    @Override
    public Observable<List<Movie>> getFavoriteMovies() {
        return db.createQuery(MovieModel.TABLE_NAME
                , MovieDB.FACTORY.select_favorite_movies().statement)
                .mapToList(MovieDB.SELECT_FAVORITES_MAPPER::map)
                .take(1)
                .flatMap(Observable::from)
                .map(movieDB -> Movie.create(movieDB.movie_id(), movieDB.imdb_id(), movieDB.title()
                        , movieDB.overview(), movieDB.runtime(),movieDB.tmdb_rating(), movieDB.release_date()
                        , movieDB.backdrop_path(), movieDB.poster_path()))
                .toList();
    }


    @Override
    public Observable<List<Long>> getFavoriteMoviesIds() {
        return db.createQuery(MovieModel.TABLE_NAME
                , MovieDB.FACTORY.select_favorite_movies_ids().statement)
                .mapToList(MovieDB.SELECT_FAVORITES_IDS_MAPPER::map);
    }

    @Override
    public Observable<Boolean> isMovieFavorite(Long movieId) {
        SqlDelightStatement statement = MovieDB.FACTORY.is_movie_favorite(movieId);
        return db.createQuery(MovieModel.TABLE_NAME, statement.statement, statement.args)
                .map(query -> {
                    Cursor cursor = query.run();
                    return cursor.moveToNext();
                });
    }

    @Override
    public Observable<List<Movie>> getWatchlistMovies() {
        return db.createQuery(MovieModel.TABLE_NAME, MovieDB.FACTORY.select_watchlist_movies().statement)
                .mapToList(MovieDB.SELECT_WATCHLIST_MAPPER::map)
                .take(1)
                .flatMap(Observable::from)
                .map(movieDB -> Movie.create(movieDB.movie_id(),movieDB.imdb_id(),movieDB.title()
                        ,movieDB.overview(),movieDB.runtime(),movieDB.tmdb_rating(),movieDB.release_date()
                        ,movieDB.backdrop_path(),movieDB.poster_path()))
                .toList();
    }

    @Override
    public Observable<Boolean> isMovieInWatchlist(Long movieId) {
        SqlDelightStatement statement = MovieDB.FACTORY.is_movie_in_watchlist(movieId);
        return db.createQuery(MovieModel.TABLE_NAME, statement.statement, statement.args)
                .map(query -> {
                    Cursor cursor = query.run();
                    return cursor.moveToNext();
                });
    }
}
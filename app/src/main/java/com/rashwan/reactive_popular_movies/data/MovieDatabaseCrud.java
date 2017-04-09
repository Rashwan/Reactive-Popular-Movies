package com.rashwan.reactive_popular_movies.data;

import com.rashwan.reactive_popular_movies.FavoriteMoviesModel;
import com.rashwan.reactive_popular_movies.WatchlistMoviesModel;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.squareup.sqlbrite.BriteDatabase;

import javax.inject.Inject;

/**
 * Created by rashwan on 7/18/16.
 */

public class MovieDatabaseCrud {
    private BriteDatabase db;

    @Inject
    public MovieDatabaseCrud(BriteDatabase db) {
        this.db = db;
    }

    public void insertIntoFavorites(Long movieId, String title, String releaseDate, String voteAverage, String overview, String posterPath, String backdropPath){
        Movie.Insert_into_favorites insertIntoFavorites =
                new FavoriteMoviesModel.Insert_into_favorites(db.getWritableDatabase());
        insertIntoFavorites
                .bind(movieId,title,releaseDate,voteAverage,overview,posterPath,backdropPath);
        db.executeInsert(FavoriteMoviesModel.TABLE_NAME, insertIntoFavorites.program);

    }

    public void deleteFromFavorites(Long movieId){
        Movie.Remove_from_favorites removeFromFavorites =
                new FavoriteMoviesModel.Remove_from_favorites(db.getWritableDatabase());
        removeFromFavorites.bind(movieId);

        db.executeUpdateDelete(FavoriteMoviesModel.TABLE_NAME,removeFromFavorites.program);
    }

    public void insertIntoWatchlist(Long movieId, String title, String releaseDate, String voteAverage, String overview, String posterPath, String backdropPath){
        Movie.Insert_into_watchlist insertIntoWatchlist =
                new WatchlistMoviesModel.Insert_into_watchlist(db.getWritableDatabase());
        insertIntoWatchlist
                .bind(movieId,title,releaseDate,voteAverage,overview,posterPath,backdropPath);
        db.executeInsert(WatchlistMoviesModel.TABLE_NAME, insertIntoWatchlist.program);

    }

    public void deleteFromWatchlist(Long movieId){
        Movie.Remove_from_watchlist removeFromWatchlist =
                new WatchlistMoviesModel.Remove_from_watchlist(db.getWritableDatabase());
        removeFromWatchlist.bind(movieId);

        db.executeUpdateDelete(WatchlistMoviesModel.TABLE_NAME,removeFromWatchlist.program);
    }
}

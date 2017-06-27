package com.rashwan.reactive_popular_movies.data.local;

import com.rashwan.reactive_popular_movies.MovieModel;
import com.rashwan.reactive_popular_movies.data.model.MovieDB;
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

    public long insertMovie(long id,String title,String releaseDate,String overview,
                String posterPath,String backdropPath,Long runtime,Boolean isFavorite,Boolean isWatchlist){
        MovieDB.Insert_movie insertMovie = new MovieModel.Insert_movie(db.getWritableDatabase());
        insertMovie.bind(id,title,releaseDate,overview,posterPath,backdropPath,runtime,
                isFavorite,isWatchlist);
        return db.executeInsert(MovieDB.TABLE_NAME,insertMovie.program);
    }

    public int updateFavorite(boolean isFavorite, Long movieId){
        MovieDB.Update_favorite updateFavorite =
                new MovieDB.Update_favorite(db.getWritableDatabase());
        updateFavorite.bind(isFavorite,movieId);
        return db.executeUpdateDelete(MovieDB.TABLE_NAME,updateFavorite.program);

    }

    public int updateWatchlist(boolean isWatchlist, Long movieId){
        MovieDB.Update_watchlist updateWatchlist =
                new MovieDB.Update_watchlist(db.getWritableDatabase());
        updateWatchlist.bind(isWatchlist,movieId);
        return db.executeUpdateDelete(MovieDB.TABLE_NAME,updateWatchlist.program);
    }


    public int deleteMovie(Boolean isFavorite,Boolean isWatchlist){
        MovieDB.Delete_movie deleteMovie =
                new MovieDB.Delete_movie(db.getWritableDatabase());
        deleteMovie.bind(isFavorite,isWatchlist);
        return db.executeUpdateDelete(MovieDB.TABLE_NAME,deleteMovie.program);
    }
}

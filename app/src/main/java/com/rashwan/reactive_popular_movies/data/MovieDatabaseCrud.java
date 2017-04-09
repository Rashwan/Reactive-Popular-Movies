package com.rashwan.reactive_popular_movies.data;

import com.rashwan.reactive_popular_movies.FavoriteMoviesModel;
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

    public void insert(Long movieId,String title,String releaseDate,String voteAverage,String overview,String posterPath,String backdropPath){
        Movie.Insert_to_favorites insertToFavorites =
                new FavoriteMoviesModel.Insert_to_favorites(db.getWritableDatabase());
        insertToFavorites
                .bind(movieId,title,releaseDate,voteAverage,overview,posterPath,backdropPath);
        db.executeInsert(FavoriteMoviesModel.TABLE_NAME, insertToFavorites.program);

    }

    public void delete(Long movieId){
        Movie.Remove_from_favorites removeFromFavorites =
                new FavoriteMoviesModel.Remove_from_favorites(db.getWritableDatabase());
        removeFromFavorites.bind(movieId);

        db.executeUpdateDelete(FavoriteMoviesModel.TABLE_NAME,removeFromFavorites.program);
    }
}

package com.rashwan.reactive_popular_movies.data;

import android.database.Cursor;

import com.rashwan.reactive_popular_movies.MovieModel;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by rashwan on 7/18/16.
 */

public class MovieDatabaseCrud {
    BriteDatabase db;

    @Inject
    public MovieDatabaseCrud(BriteDatabase db) {
        this.db = db;
    }

    public void insert(Long movieId,String title,String releaseDate,String voteAverage,String overview,String posterPath,String backdropPath){
        db.insert(MovieModel.TABLE_NAME, Movie.FACTORY.marshal()
                .id(movieId).title(title).release_date(releaseDate).vote_average(voteAverage)
                .overview(overview).poster_path(posterPath).backdrop_path(backdropPath).asContentValues());
    }

    public void delete(Long movieId){
        db.delete(MovieModel.TABLE_NAME, "id = ?",movieId.toString());
    }

    public Observable<Boolean> isMovieFavorite(Long movieId){
        return db.createQuery(MovieModel.TABLE_NAME,MovieModel.SELECT_BY_MOVIE_ID, movieId.toString())
                .map(query -> {
                    Cursor cursor =  query.run();
                    return cursor.moveToNext();
                });
    }

    public Observable<Movie> getMovie(Long movieID){
        return db.createQuery(MovieModel.TABLE_NAME,MovieModel.SELECT_BY_MOVIE_ID, movieID.toString())
                .map(query -> {
                    Cursor cursor = query.run();
                    try {
                        if (!cursor.moveToNext()) {
                            throw new AssertionError("No rows");
                        }
                        return Movie.MOVIE_MAPPER.map(cursor);
                    } finally {
                        cursor.close();
                    }
                });
    }

    public Observable<List<Movie>> getMovies(){
        List<Movie> movies = new ArrayList<>();
        return db.createQuery(MovieModel.TABLE_NAME,MovieModel.SELECT_ALL_MOVIES)
                .map(query -> {
                    Cursor cursor = query.run();
                    try {
                        while (cursor.moveToNext()) {
                            movies.add(Movie.MOVIES_MAPPER.map(cursor));
                        }
                        return movies;
                    } finally {
                        cursor.close();
                    }
                });
    }
}

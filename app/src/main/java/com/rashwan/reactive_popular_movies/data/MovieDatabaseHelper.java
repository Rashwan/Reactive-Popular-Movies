package com.rashwan.reactive_popular_movies.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rashwan.reactive_popular_movies.MovieModel;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rashwan on 7/16/16.
 */

public class MovieDatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "moviesDatabase";
    private static final int DATABASE_VERSION = 1;

    public MovieDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MovieModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieModel.TABLE_NAME);
        onCreate(db);
    }

    public void insert(BriteDatabase db,Long movieId,String title,String releaseDate,String voteAverage,String overview,String posterPath,String backdropPath){
        db.insert(MovieModel.TABLE_NAME, Movie.FACTORY.marshal()
        .id(movieId).title(title).release_date(releaseDate).vote_average(voteAverage)
        .overview(overview).poster_path(posterPath).backdrop_path(backdropPath).asContentValues());
    }

    public Movie getMovie(BriteDatabase db, Long movieID){
         db.createQuery(MovieModel.TABLE_NAME,MovieModel.SELECT_BY_MOVIE_ID, movieID.toString())
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
        return null;
    }
    public List<Movie> getMovies(BriteDatabase db){
        List<Movie> movies = new ArrayList<>();
        db.createQuery(MovieModel.TABLE_NAME,MovieModel.TABLE_NAME,MovieModel.SELECT_ALL_MOVIES,null)
        .map(query -> {
            Cursor cursor = query.run();
            try {
                while (cursor.moveToNext()){
                    movies.add(Movie.MOVIES_MAPPER.map(cursor));
                }
                return movies;
            }finally {
                cursor.close();
            }
        });
        return null;
    }
}

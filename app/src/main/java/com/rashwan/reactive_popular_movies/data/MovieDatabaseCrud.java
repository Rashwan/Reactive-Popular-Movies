package com.rashwan.reactive_popular_movies.data;

import com.rashwan.reactive_popular_movies.MovieModel;
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
        db.insert(MovieModel.TABLE_NAME, Movie.FACTORY.marshal()
                .id(movieId).title(title).release_date(releaseDate).vote_average(voteAverage)
                .overview(overview).poster_path(posterPath).backdrop_path(backdropPath).asContentValues());
    }

    public void delete(Long movieId){
        db.delete(MovieModel.TABLE_NAME, "id = ?",movieId.toString());
    }
}

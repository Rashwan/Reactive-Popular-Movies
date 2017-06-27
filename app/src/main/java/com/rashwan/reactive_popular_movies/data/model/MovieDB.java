package com.rashwan.reactive_popular_movies.data.model;

import com.google.auto.value.AutoValue;
import com.rashwan.reactive_popular_movies.MovieModel;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.sqldelight.RowMapper;

/**
 * Created by rashwan on 6/27/17.
 */
@AutoValue
public abstract class MovieDB implements MovieModel {


    public static final Factory<MovieDB> FACTORY = new Factory<>(AutoValue_MovieDB::new);
    public static final RowMapper<MovieDB> SELECT_MOVIE_BY_ID_MAPPER = FACTORY.select_movie_by_idMapper();
    public static final RowMapper<Long> FIND_MOVIE_BY_ID_MAPPER = FACTORY.find_movie_by_idMapper();
    public static final RowMapper<MovieDB> SELECT_FAVORITES_MAPPER = FACTORY.select_favorite_moviesMapper();
    public static final RowMapper<MovieDB> SELECT_WATCHLIST_MAPPER = FACTORY.select_watchlist_moviesMapper();
    public static final RowMapper<Long> SELECT_FAVORITES_IDS_MAPPER = FACTORY.select_favorite_movies_idsMapper();


    public static JsonAdapter<MovieDB> jsonAdapter(Moshi moshi){
        return new AutoValue_MovieDB.MoshiJsonAdapter(moshi);
    }
}

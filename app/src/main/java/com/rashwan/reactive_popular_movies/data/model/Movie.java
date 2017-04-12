package com.rashwan.reactive_popular_movies.data.model;

import android.app.Application;
import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.rashwan.reactive_popular_movies.MovieModel;
import com.rashwan.reactive_popular_movies.PopularMoviesApplication;
import com.rashwan.reactive_popular_movies.R;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.sqldelight.RowMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

/**
 * Created by rashwan on 6/23/16.
 */

 @AutoValue public abstract class Movie implements MovieModel,Parcelable{
    public static final String QUALITY_LOW = "w342";
    public static final String QUALITY_MEDIUM = "w500";
    public static final String QUALITY_HIGH = "w780";
    @Inject public transient Application context;


    public Movie() {
        PopularMoviesApplication.getApplicationComponent().inject(this);
    }

    public static final Factory<Movie> FACTORY = new Factory<>(AutoValue_Movie::new);
    public static final RowMapper<Movie> SELECT_MOVIE_BY_ID_MAPPER = FACTORY.select_movie_by_idMapper();
    public static final RowMapper<Long> FIND_MOVIE_BY_ID_MAPPER = FACTORY.find_movie_by_idMapper();
    public static final RowMapper<Movie> SELECT_FAVORITES_MAPPER = FACTORY.select_favorite_moviesMapper();
    public static final RowMapper<Movie> SELECT_WATCHLIST_MAPPER = FACTORY.select_watchlist_moviesMapper();
    public static final RowMapper<Long> SELECT_FAVORITES_IDS_MAPPER = FACTORY.select_favorite_movies_idsMapper();



    public static JsonAdapter<Movie> jsonAdapter(Moshi moshi){
        return AutoValue_Movie.jsonAdapter(moshi);
    }

    public String getFullPosterPath(String quality){
        String baseUrl = context.getString(R.string.poster_base_url);
        return baseUrl + quality + this.poster_path();
    }

    public String getFullBackdropPath(String quality){
        String baseUrl = context.getString(R.string.poster_base_url);
        return baseUrl + quality + this.backdrop_path();
    }
    public String getFormattedReleaseDate(String releaseDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());

        try {
            Date date = dateFormat.parse(releaseDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return newDateFormat.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return releaseDate;
    }
    public String getFormattedRuntime(Long runtime){
        int hours = (int) (runtime / 60);
        int minutes = (int) (runtime % 60);
        return hours > 1 ? String.format(Locale.getDefault(), "%d hrs %02d mins", hours, minutes)
                : String.format(Locale.getDefault(), "%d hr %02d mins", hours, minutes);
    }

}

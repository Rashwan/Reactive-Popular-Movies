package com.rashwan.reactive_popular_movies.model;

import android.app.Application;
import android.os.Parcelable;
import android.support.annotation.Nullable;

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
        PopularMoviesApplication.getComponent().inject(this);
    }


    public static JsonAdapter<Movie> jsonAdapter(Moshi moshi){
        return AutoValue_Movie.jsonAdapter(moshi);
    }

    public static final MovieModel.Factory<Movie> FACTORY = new Factory<>(new MovieModel.Creator<Movie>() {
        @Override
        public Movie create(long _id, long movie_id, @Nullable String title, @Nullable String release_date, @Nullable String vote_average, @Nullable String overview, @Nullable String poster_path, @Nullable String backdrop_path) {
            return new AutoValue_Movie(_id,movie_id,title,overview,release_date,poster_path,vote_average,backdrop_path);
        }
    });
    public static final RowMapper<Movie> MAPPER = FACTORY.select_by_movie_idMapper();

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

    public String getFormattedVoteAverage(String voteAverage){
        return voteAverage + " / 10";
    }
}

package com.rashwan.reactive_popular_movies.model;

import android.app.Application;
import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.rashwan.reactive_popular_movies.PopularMoviesApplication;
import com.rashwan.reactive_popular_movies.R;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by rashwan on 6/23/16.
 */

 @AutoValue public abstract class Movie implements Parcelable{
    public static final String QUALITY_LOW = "w342";
    public static final String QUALITY_MEDIUM = "w500";
    public static final String QUALITY_HIGH = "w780";
    @Inject public transient Application context;
    public abstract int id();
    public abstract String title();
    public abstract String overview();
    @Json(name = "release_date") public abstract String releaseDate();
    @Json(name = "poster_path") public abstract String posterPath();
    @Json(name = "vote_average") public abstract String voteAverage();
    @Json(name = "backdrop_path") public abstract String backdropPath();
    public transient List<Trailer> trailers = null;
    public transient List<Review> reviews = null;

    public Movie() {
        PopularMoviesApplication.getComponent().inject(this);
    }


    public static JsonAdapter<Movie> jsonAdapter(Moshi moshi){
        return AutoValue_Movie.jsonAdapter(moshi);
    }


    public String getFullPosterPath(String quality){
        String baseUrl = context.getString(R.string.poster_base_url);
        return baseUrl + quality + this.posterPath();
    }
}

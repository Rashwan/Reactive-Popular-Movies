package com.rashwan.reactive_popular_movies.data.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.Locale;

/**
 * Created by rashwan on 6/23/16.
 */

 @AutoValue public abstract class Movie implements Parcelable{
    public static final String QUALITY_LOW = "w342";
    public static final String QUALITY_MEDIUM = "w500";
    public static final String QUALITY_HIGH = "w780";

    public abstract long id();
    @Nullable @Json(name = "imdb_id") public abstract String ImdbId();
    public abstract String title();
    @Nullable public abstract String overview();
    public abstract long runtime();
    @Json(name = "vote_average") public abstract float tmdbRating();
    @Json(name = "release_date") public abstract String releaseDate();
    @Nullable @Json(name = "backdrop_path") public abstract String backdropPath();
    @Nullable @Json(name = "poster_path") public abstract String posterPath();


    public static Movie create(long id, String ImdbId,String title, String overview, Long runtime,
           float tmdbRating,String releaseDate, String backdropPath, String posterPath) {
        return new AutoValue_Movie(id,ImdbId,title,overview,runtime,tmdbRating,releaseDate,backdropPath,posterPath);
    }



    public static JsonAdapter<Movie> jsonAdapter(Moshi moshi){
        return new AutoValue_Movie.MoshiJsonAdapter(moshi);
    }


    public String getFormattedRuntime(Long runtime){
        int hours = (int) (runtime / 60);
        int minutes = (int) (runtime % 60);
        return hours > 1 ? String.format(Locale.getDefault(), "%d hrs %02d mins", hours, minutes)
                : String.format(Locale.getDefault(), "%d hr %02d mins", hours, minutes);
    }

}

package com.rashwan.reactive_popular_movies.data.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.List;

/**
 * Created by rashwan on 4/12/17.
 */
@AutoValue
public abstract class MovieDetails {
    public abstract String imdbID ();
    public abstract String imdbRating();
    @Json(name = "Ratings") public abstract List<Rating> ratings();
    @Json(name = "Rated") public abstract String rated();
    @Json(name = "Genre") public abstract String genre();
    @Json(name = "Metascore") public abstract String metascore();
    @Json(name = "BoxOffice") public abstract String boxOffice();
    @Json(name = "Awards") public abstract String awards();
    @Json(name = "Production") public abstract String production();
    @Json(name = "Website") public abstract String website();


    public static JsonAdapter<MovieDetails> jsonAdapter(Moshi moshi){
        return new AutoValue_MovieDetails.MoshiJsonAdapter(moshi);
    }

    public String getFormattedBoxOffice(){
        return this.boxOffice().substring(0,this.boxOffice().length()-3);
    }
}

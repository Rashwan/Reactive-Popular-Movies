package com.rashwan.reactive_popular_movies.data.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by rashwan on 4/12/17.
 */
@AutoValue
public abstract class Rating {
    public static final String ROTTEN_TOMATOES_KEY = "Rotten Tomatoes";
    @Json(name = "Source") public abstract String source();
    @Json(name = "Value") public abstract String value();

    public static JsonAdapter<Rating> jsonAdapter(Moshi moshi){
        return new AutoValue_Rating.MoshiJsonAdapter(moshi);
    }
}

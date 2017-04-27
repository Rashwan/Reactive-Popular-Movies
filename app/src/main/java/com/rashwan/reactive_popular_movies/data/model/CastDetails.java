package com.rashwan.reactive_popular_movies.data.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by rashwan on 4/26/17.
 */
@AutoValue
public abstract class CastDetails {
    public abstract long id();
    public abstract String name();
    public abstract String biography();
    public abstract String birthday();
    @Json(name = "place_of_birth") public abstract String placeOfBirth();
    @Json(name = "profile_path") public abstract String profilePath();


    public static JsonAdapter<CastDetails> jsonAdapter(Moshi moshi){
        return AutoValue_CastDetails.jsonAdapter(moshi);
    }
}

package com.rashwan.reactive_popular_movies.model;

import android.app.Application;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import javax.inject.Inject;

/**
 * Created by rashwan on 6/23/16.
 */

@AutoValue public abstract class Trailer {
    @Inject public transient Application context;
    @Json(name = "key") public abstract String youtubeUrl();
    public abstract String name();

    public static JsonAdapter<Trailer> jsonAdapter(Moshi moshi){
        return AutoValue_Trailer.jsonAdapter(moshi);
    }



}

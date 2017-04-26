package com.rashwan.reactive_popular_movies.data.model;

import android.app.Application;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.rashwan.reactive_popular_movies.PopularMoviesApplication;
import com.rashwan.reactive_popular_movies.R;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import javax.inject.Inject;

/**
 * Created by rashwan on 4/21/17.
 */
@AutoValue
public abstract class Cast {
    public static final String QUALITY_LOW = "w342";

    public abstract long id();
    public abstract String name();
    public abstract String character();
    @Nullable @Json(name = "profile_path") public abstract String profilePath();
    @Inject public transient Application context;

    public Cast() {
        PopularMoviesApplication.getApplicationComponent().inject(this);
    }

    public String getFullProfilePath(String quality){
        String baseUrl = context.getString(R.string.poster_base_url);
        return baseUrl + quality + this.profilePath();
    }
    public static JsonAdapter<Cast> jsonAdapter(Moshi moshi){
        return AutoValue_Cast.jsonAdapter(moshi);
    }
}

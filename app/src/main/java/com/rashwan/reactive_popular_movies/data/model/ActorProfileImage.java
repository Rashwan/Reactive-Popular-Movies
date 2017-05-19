package com.rashwan.reactive_popular_movies.data.model;

import android.app.Application;

import com.google.auto.value.AutoValue;
import com.rashwan.reactive_popular_movies.PopularMoviesApplication;
import com.rashwan.reactive_popular_movies.R;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import javax.inject.Inject;

/**
 * Created by rashwan on 5/5/17.
 */
@AutoValue
public abstract class ActorProfileImage {
    public static final String QUALITY_LOW = "w342";

    @Json(name = "file_path") public abstract String imagePath();
    @Inject public transient Application context;

    public ActorProfileImage() {
        PopularMoviesApplication.getApplicationComponent().inject(this);
    }

    public static JsonAdapter<ActorProfileImage> jsonAdapter(Moshi moshi){
        return new AutoValue_ActorProfileImage.MoshiJsonAdapter(moshi);
    }

    public String getFullImagePath(String quality){
        String baseUrl = context.getString(R.string.poster_base_url);
        return baseUrl + quality + this.imagePath();
    }
}

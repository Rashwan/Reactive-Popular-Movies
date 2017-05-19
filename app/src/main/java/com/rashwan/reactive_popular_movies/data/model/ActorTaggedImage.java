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
public abstract class ActorTaggedImage {
    public static final String QUALITY_LOW = "w342";
    public static final String QUALITY_MEDIUM = "w500";
    @Json(name = "file_path") public abstract String imagePath();
    @Json(name = "image_type") public abstract String imageType();
    @Inject public transient Application context;

    public ActorTaggedImage() {
        PopularMoviesApplication.getApplicationComponent().inject(this);
    }



    public static JsonAdapter<ActorTaggedImage> jsonAdapter(Moshi moshi){
        return new AutoValue_ActorTaggedImage.MoshiJsonAdapter(moshi);
    }
    public String getFullImagePath(String quality){
        String baseUrl = context.getString(R.string.poster_base_url);
        return baseUrl + quality + this.imagePath();
    }
}

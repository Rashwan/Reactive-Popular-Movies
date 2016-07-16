package com.rashwan.reactive_popular_movies.data.model;

import android.app.Application;
import android.net.Uri;

import com.google.auto.value.AutoValue;
import com.rashwan.reactive_popular_movies.PopularMoviesApplication;
import com.rashwan.reactive_popular_movies.R;
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

    public Trailer() {
        PopularMoviesApplication.getComponent().inject(this);
    }

    public static JsonAdapter<Trailer> jsonAdapter(Moshi moshi){
        return AutoValue_Trailer.jsonAdapter(moshi);
    }

    public String getTrailerThumbnail(){
        String baseUrl = context.getString(R.string.trailer_youtube_thumbnail);
        return String.format(baseUrl,youtubeUrl());
    }
    public Uri getFullYoutubeUri(){
        String baseUrl = context.getString(R.string.trailer_youtube_video);
        return Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter("v",this.youtubeUrl()).build();
    }

}

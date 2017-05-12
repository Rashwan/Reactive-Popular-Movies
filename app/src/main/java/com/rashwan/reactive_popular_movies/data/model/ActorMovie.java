package com.rashwan.reactive_popular_movies.data.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by rashwan on 5/12/17.
 */
@AutoValue
public abstract class ActorMovie {
    public abstract String character();
    public abstract String title();
    @Nullable @Json(name ="release_date") public abstract String releaseDate();
    @Nullable @Json(name ="poster_path") public abstract String posterPath();
    @Json(name = "id") public abstract long movieId();

    public static JsonAdapter<ActorMovie> jsonAdapter(Moshi moshi){
        return AutoValue_ActorMovie.jsonAdapter(moshi);
    }

}

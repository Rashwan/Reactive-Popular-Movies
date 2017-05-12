package com.rashwan.reactive_popular_movies.data.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.List;

/**
 * Created by rashwan on 5/12/17.
 */
@AutoValue
public abstract class ActorMoviesResponse {
    public abstract long id();
    @Json(name = "cast") public abstract List<ActorMovie> actorMovies();

    public static JsonAdapter<ActorMoviesResponse> jsonAdapter(Moshi moshi){
        return AutoValue_ActorMoviesResponse.jsonAdapter(moshi);
    }
}

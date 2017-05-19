package com.rashwan.reactive_popular_movies.data.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.List;

/**
 * Created by rashwan on 5/5/17.
 */
@AutoValue
public abstract class ActorProfileImagesResponse {
    @Json(name = "profiles") public  abstract List<ActorProfileImage> actorProfileImages();

    public static JsonAdapter<ActorProfileImagesResponse> jsonAdapter(Moshi moshi){
        return new AutoValue_ActorProfileImagesResponse.MoshiJsonAdapter(moshi);
    }
}

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
public abstract class ActorTaggedImagesResponse {
    public abstract int page();
    @Json(name = "results") public abstract List<ActorTaggedImage> taggedImages();

    public static JsonAdapter<ActorTaggedImagesResponse> jsonAdapter(Moshi moshi){
        return new AutoValue_ActorTaggedImagesResponse.MoshiJsonAdapter(moshi);
    }

}

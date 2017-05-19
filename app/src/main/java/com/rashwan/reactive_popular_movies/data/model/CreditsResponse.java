package com.rashwan.reactive_popular_movies.data.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.List;

/**
 * Created by rashwan on 4/21/17.
 */
@AutoValue
public abstract class CreditsResponse {
    public abstract long id();
    public abstract List<Cast> cast();

    public static JsonAdapter<CreditsResponse> jsonAdapter(Moshi moshi){
        return new AutoValue_CreditsResponse.MoshiJsonAdapter(moshi);
    }
}

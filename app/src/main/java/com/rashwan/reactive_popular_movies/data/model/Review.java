package com.rashwan.reactive_popular_movies.data.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by rashwan on 6/23/16.
 */

@AutoValue public abstract class Review {
    public abstract String author();
    public abstract String content();

    public static JsonAdapter<Review> jsonAdapter(Moshi moshi){
        return new AutoValue_Review.MoshiJsonAdapter(moshi);
    }
}

package com.rashwan.reactive_popular_movies.data.model;

import com.rashwan.reactive_popular_movies.data.AutoValueMoshi_MyAdapterFactory;
import com.ryanharter.auto.value.moshi.MoshiAdapterFactory;
import com.squareup.moshi.JsonAdapter;

/**
 * Created by rashwan on 1/7/17.
 */
@MoshiAdapterFactory
public abstract class MyAdapterFactory implements JsonAdapter.Factory {
    // Static factory method to access the package
    // private generated implementation
    public static JsonAdapter.Factory create() {
        return new AutoValueMoshi_MyAdapterFactory();
    }
}

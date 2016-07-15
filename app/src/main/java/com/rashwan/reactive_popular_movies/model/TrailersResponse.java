package com.rashwan.reactive_popular_movies.model;

import com.squareup.moshi.Json;

import java.util.List;

/**
 * Created by rashwan on 7/7/16.
 */

public class TrailersResponse {
    private int id ;
    @Json(name = "results") private List<Trailer> trailers;

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public Boolean isEmpty(){
        return this.trailers.isEmpty();
    }
}

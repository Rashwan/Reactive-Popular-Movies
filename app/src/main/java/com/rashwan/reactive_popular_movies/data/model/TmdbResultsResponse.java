package com.rashwan.reactive_popular_movies.data.model;

import com.squareup.moshi.Json;

import java.util.List;

/**
 * Created by rashwan on 5/28/17.
 */

public class TmdbResultsResponse<T> {
    public int id;
    public int page;
    @Json(name = "results") private List<T> results;

    public List<T> getResults() {
        return results;
    }

}

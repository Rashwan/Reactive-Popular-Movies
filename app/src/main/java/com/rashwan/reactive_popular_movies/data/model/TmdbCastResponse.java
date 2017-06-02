package com.rashwan.reactive_popular_movies.data.model;

import java.util.List;

/**
 * Created by rashwan on 6/2/17.
 */

public class TmdbCastResponse<T> {
    public int id;
    private List<T> cast;

    public List<T> getCast() {
        return cast;
    }
}

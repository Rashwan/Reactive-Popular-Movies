package com.rashwan.reactive_popular_movies.model;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.Json;

import java.util.List;

/**
 * Created by rashwan on 6/23/16.
 */

public class MoviesResponse {
    public int page;
    @Json(name = "results") public List<Movie> movies;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    @FromJson MoviesResponse blabla(MoviesResponse response){
        return response;
    }
    
}

package com.rashwan.reactive_popular_movies.model;

/**
 * Created by rashwan on 6/23/16.
 */

public class Trailer {
    private String youtubeUrl;
    private String name;

    public Trailer(String youtubeUrl, String name) {
        this.youtubeUrl = youtubeUrl;
        this.name = name;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public String getName() {
        return name;
    }
}

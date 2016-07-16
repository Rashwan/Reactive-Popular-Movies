package com.rashwan.reactive_popular_movies.data.model;

import com.squareup.moshi.Json;

import java.util.List;

/**
 * Created by rashwan on 7/8/16.
 */

public class ReviewResponse {
    public int id;
    @Json(name = "results") private List<Review> reviews;

    public List<Review> getReviews() {
        return reviews;
    }

    public Boolean isEmpty(){
        return this.reviews.isEmpty();
    }
}

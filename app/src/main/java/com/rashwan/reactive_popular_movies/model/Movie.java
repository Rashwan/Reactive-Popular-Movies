package com.rashwan.reactive_popular_movies.model;

import android.app.Application;

import com.rashwan.reactive_popular_movies.PopularMoviesApplication;
import com.rashwan.reactive_popular_movies.R;
import com.squareup.moshi.Json;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by rashwan on 6/23/16.
 */

public class Movie {
    @Inject public transient Application context;
    public int id;
    public String title;
    public String overview;
    @Json(name = "release_date") public String releaseDate;
    @Json(name = "poster_path") public String posterPath;
    @Json(name = "vote_average") public String voteAverage;
    @Json(name = "backdrop_path") public String backdropPath;
    public transient List<Trailer> trailers = null;
    public transient List<Review> reviews = null;

    //Empty constructor for Moshi
    public Movie() {
        PopularMoviesApplication.getComponent().inject(this);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getPosterPath() {
        Timber.d(context.getString(R.string.movies_api_base_url));
        String apiKey = "9c3654aee5aea28f21963eeebfd6f4a0";
        String fullPath = "http://image.tmdb.org/t/p/" + "w300" + posterPath + "?api_key=" + apiKey;
        Timber.d(fullPath);
        return fullPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "movie Title: " + getTitle();
    }
}

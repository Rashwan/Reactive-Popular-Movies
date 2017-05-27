package com.rashwan.reactive_popular_movies.feature.movieDetails.movieReviews.di;

import com.rashwan.reactive_popular_movies.feature.movieDetails.movieReviews.MovieReviewsAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rashwan on 4/20/17.
 */

@Module
public class MovieReviewsModule {
    @Provides
    public MovieReviewsAdapter provideMovieReviewsAdapter(){
        return new MovieReviewsAdapter();
    }
}

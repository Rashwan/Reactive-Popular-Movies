package com.rashwan.reactive_popular_movies.feature.movieDetails.movieReviews.injection;

import com.rashwan.reactive_popular_movies.feature.movieDetails.movieReviews.MovieReviewsAdapter;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieReviews.MovieReviewsPresenter;
import com.rashwan.reactive_popular_movies.service.TMDBService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rashwan on 4/20/17.
 */

@Module
public class MovieReviewsModule {
    @Provides
    public MovieReviewsPresenter provideMovieReviewsPresenter(TMDBService tmdbService){
        return new MovieReviewsPresenter(tmdbService);
    }
    @Provides
    public MovieReviewsAdapter provideMovieReviewsAdapter(){
        return new MovieReviewsAdapter();
    }
}

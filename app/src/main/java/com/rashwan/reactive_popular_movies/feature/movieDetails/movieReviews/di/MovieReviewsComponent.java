package com.rashwan.reactive_popular_movies.feature.movieDetails.movieReviews.di;

import com.rashwan.reactive_popular_movies.dI.PerFragment;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieReviews.MovieReviewsFragment;

import dagger.Subcomponent;

/**
 * Created by rashwan on 4/20/17.
 */

@PerFragment
@Subcomponent(modules = MovieReviewsModule.class)
public interface MovieReviewsComponent {
    void inject(MovieReviewsFragment target);
}

package com.rashwan.reactive_popular_movies.feature.movieDetails.movieInfo.di;

import com.rashwan.reactive_popular_movies.feature.movieDetails.movieInfo.MovieTrailersAdapter;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieInfo.SimilarMoviesAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rashwan on 4/20/17.
 */

@Module
public class MovieInfoModule {
    @Provides
    MovieTrailersAdapter providesMovieTrailersAdapter(){
        return new MovieTrailersAdapter();
    }
    @Provides
    SimilarMoviesAdapter providesSimilarMoviesAdapter(){
        return new SimilarMoviesAdapter();
    }
}

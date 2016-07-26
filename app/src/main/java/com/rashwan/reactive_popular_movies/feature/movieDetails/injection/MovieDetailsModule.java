package com.rashwan.reactive_popular_movies.feature.movieDetails.injection;

import com.rashwan.reactive_popular_movies.data.MovieDatabaseCrud;
import com.rashwan.reactive_popular_movies.feature.movieDetails.MovieDetailsPresenter;
import com.rashwan.reactive_popular_movies.feature.movieDetails.MovieReviewAdapter;
import com.rashwan.reactive_popular_movies.feature.movieDetails.MovieTrailersAdapter;
import com.rashwan.reactive_popular_movies.service.MoviesService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rashwan on 7/26/16.
 */

@Module
public class MovieDetailsModule {

    @Provides
    public MovieDetailsPresenter provideMovieDetailsPresenter(MoviesService moviesService, MovieDatabaseCrud dbHelper){
        return new MovieDetailsPresenter(moviesService,dbHelper);
    }
    @Provides
    public MovieTrailersAdapter provideMovieTrailersAdapter(){
        return new MovieTrailersAdapter();
    }
    @Provides
    public MovieReviewAdapter provideMovieReviewAdapter(){
        return new MovieReviewAdapter();
    }
}

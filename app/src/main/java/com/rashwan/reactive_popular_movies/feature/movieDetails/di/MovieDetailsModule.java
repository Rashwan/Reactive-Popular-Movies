package com.rashwan.reactive_popular_movies.feature.movieDetails.di;

import com.rashwan.reactive_popular_movies.data.local.MovieDatabaseCrud;
import com.rashwan.reactive_popular_movies.feature.movieDetails.MovieDetailsPresenter;
import com.rashwan.reactive_popular_movies.service.OMDBService;
import com.rashwan.reactive_popular_movies.service.TMDBService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rashwan on 7/26/16.
 */

@Module
public class MovieDetailsModule {
    @Provides
    public MovieDetailsPresenter provideMovieDetailsPresenter(TMDBService TMDBService, OMDBService omdbService, MovieDatabaseCrud dbHelper){
        return new MovieDetailsPresenter(TMDBService, omdbService, dbHelper);
    }
}

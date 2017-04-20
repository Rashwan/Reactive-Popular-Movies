package com.rashwan.reactive_popular_movies.feature.movieDetails.movieInfo.injection;

import com.rashwan.reactive_popular_movies.feature.movieDetails.movieInfo.MovieInfoPresenter;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieInfo.MovieTrailersAdapter;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieInfo.SimilarMoviesAdapter;
import com.rashwan.reactive_popular_movies.service.OMDBService;
import com.rashwan.reactive_popular_movies.service.TMDBService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rashwan on 4/20/17.
 */

@Module
public class MovieInfoModule {
    @Provides
    MovieInfoPresenter providesMovieInfoPresenter(TMDBService tmdbService, OMDBService omdbService){
        return new MovieInfoPresenter(tmdbService,omdbService);
    }
    @Provides
    MovieTrailersAdapter providesMovieTrailersAdapter(){
        return new MovieTrailersAdapter();
    }
    @Provides
    SimilarMoviesAdapter providesSimilarMoviesAdapter(){
        return new SimilarMoviesAdapter();
    }
}

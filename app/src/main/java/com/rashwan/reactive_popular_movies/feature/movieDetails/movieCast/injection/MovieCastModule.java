package com.rashwan.reactive_popular_movies.feature.movieDetails.movieCast.injection;

import com.rashwan.reactive_popular_movies.feature.movieDetails.movieCast.MovieCastAdapter;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieCast.MovieCastPresenter;
import com.rashwan.reactive_popular_movies.service.TMDBService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rashwan on 4/21/17.
 */
@Module
public class MovieCastModule {
    @Provides
    public MovieCastAdapter provideMovieCastAdapter(){
        return new MovieCastAdapter();
    }
    @Provides
    public MovieCastPresenter provideMovieCastPresenter(TMDBService tmdbService){
        return new MovieCastPresenter(tmdbService);
    }
}

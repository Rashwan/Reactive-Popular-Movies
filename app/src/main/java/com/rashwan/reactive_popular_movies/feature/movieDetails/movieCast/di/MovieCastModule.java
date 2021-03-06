package com.rashwan.reactive_popular_movies.feature.movieDetails.movieCast.di;

import com.rashwan.reactive_popular_movies.feature.movieDetails.movieCast.MovieCastAdapter;

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
}

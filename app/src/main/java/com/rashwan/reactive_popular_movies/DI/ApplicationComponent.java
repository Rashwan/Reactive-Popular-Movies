package com.rashwan.reactive_popular_movies.DI;

import com.rashwan.reactive_popular_movies.PopularMoviesApplication;
import com.rashwan.reactive_popular_movies.feature.browseMovies.BrowseMoviesFragment;
import com.rashwan.reactive_popular_movies.feature.movieDetails.MovieDetailsFragment;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.data.model.Trailer;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by rashwan on 6/24/16.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(PopularMoviesApplication target);
    void inject(BrowseMoviesFragment target);
    void inject(Movie target);
    void inject(MovieDetailsFragment target);
    void inject(Trailer target);
}

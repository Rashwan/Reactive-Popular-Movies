package com.rashwan.reactive_popular_movies.DI;

import com.rashwan.reactive_popular_movies.PopularMoviesApplication;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.data.model.Trailer;
import com.rashwan.reactive_popular_movies.feature.browseMovies.injection.BrowseMoviesFragmentComponent;
import com.rashwan.reactive_popular_movies.feature.browseMovies.injection.BrowseMoviesFragmentModule;
import com.rashwan.reactive_popular_movies.feature.movieDetails.injection.MovieDetailsComponent;
import com.rashwan.reactive_popular_movies.feature.movieDetails.injection.MovieDetailsModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by rashwan on 6/24/16.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(PopularMoviesApplication target);
    void inject(Movie target);
    void inject(Trailer target);

    BrowseMoviesFragmentComponent plus(BrowseMoviesFragmentModule browseMoviesFragmentModule);
    MovieDetailsComponent plus(MovieDetailsModule movieDetailsModule);
}

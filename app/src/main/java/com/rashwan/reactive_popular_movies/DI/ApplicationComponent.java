package com.rashwan.reactive_popular_movies.DI;

import com.rashwan.reactive_popular_movies.PopularMoviesApplication;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.data.model.Trailer;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.injection.BrowseMoviesComponent;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.injection.BrowseMoviesModule;
import com.rashwan.reactive_popular_movies.feature.favoriteMovies.injection.FavoriteMoviesComponent;
import com.rashwan.reactive_popular_movies.feature.favoriteMovies.injection.FavoriteMoviesModule;
import com.rashwan.reactive_popular_movies.feature.movieDetails.injection.MovieDetailsComponent;
import com.rashwan.reactive_popular_movies.feature.movieDetails.injection.MovieDetailsModule;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.nearbyMovies.injection.NearbyMoviesComponent;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.nearbyMovies.injection.NearbyMoviesModule;

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

    BrowseMoviesComponent plus(BrowseMoviesModule browseMoviesModule);
    MovieDetailsComponent plus(MovieDetailsModule movieDetailsModule);
    NearbyMoviesComponent plus(NearbyMoviesModule nearbyMoviesModule);
    FavoriteMoviesComponent plus(FavoriteMoviesModule favoriteMoviesModule);
}

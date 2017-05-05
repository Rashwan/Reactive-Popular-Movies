package com.rashwan.reactive_popular_movies.DI;

import com.rashwan.reactive_popular_movies.PopularMoviesApplication;
import com.rashwan.reactive_popular_movies.data.model.ActorTaggedImage;
import com.rashwan.reactive_popular_movies.data.model.Cast;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.data.model.Trailer;
import com.rashwan.reactive_popular_movies.feature.actorDetails.injection.ActorDetailsComponent;
import com.rashwan.reactive_popular_movies.feature.actorDetails.injection.ActorDetailsModule;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.injection.BrowseMoviesComponent;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.injection.BrowseMoviesModule;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.nearbyMovies.injection.NearbyMoviesComponent;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.nearbyMovies.injection.NearbyMoviesModule;
import com.rashwan.reactive_popular_movies.feature.favoriteMovies.injection.FavoriteMoviesComponent;
import com.rashwan.reactive_popular_movies.feature.favoriteMovies.injection.FavoriteMoviesModule;
import com.rashwan.reactive_popular_movies.feature.movieDetails.injection.MovieDetailsComponent;
import com.rashwan.reactive_popular_movies.feature.movieDetails.injection.MovieDetailsModule;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieCast.injection.MovieCastComponent;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieCast.injection.MovieCastModule;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieInfo.injection.MovieInfoComponent;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieInfo.injection.MovieInfoModule;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieReviews.injection.MovieReviewsComponent;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieReviews.injection.MovieReviewsModule;
import com.rashwan.reactive_popular_movies.feature.watchlistMovies.injection.WatchlistComponent;
import com.rashwan.reactive_popular_movies.feature.watchlistMovies.injection.WatchlistModule;

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
    void inject(Cast target);
    void inject(ActorTaggedImage target);

    BrowseMoviesComponent plus(BrowseMoviesModule browseMoviesModule);
    MovieDetailsComponent plus(MovieDetailsModule movieDetailsModule);
    MovieInfoComponent plus(MovieInfoModule movieInfoModule);
    MovieReviewsComponent plus(MovieReviewsModule movieReviewsModule);
    NearbyMoviesComponent plus(NearbyMoviesModule nearbyMoviesModule);
    FavoriteMoviesComponent plus(FavoriteMoviesModule favoriteMoviesModule);
    WatchlistComponent plus(WatchlistModule watchlistModule);
    MovieCastComponent plus(MovieCastModule movieCastModule);
    ActorDetailsComponent plus(ActorDetailsModule actorDetailsModule);
}

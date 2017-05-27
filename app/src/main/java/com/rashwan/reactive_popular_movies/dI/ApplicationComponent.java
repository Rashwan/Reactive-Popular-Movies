package com.rashwan.reactive_popular_movies.dI;

import com.rashwan.reactive_popular_movies.PopularMoviesApplication;
import com.rashwan.reactive_popular_movies.data.di.CastRepositoryModule;
import com.rashwan.reactive_popular_movies.data.di.MoviesRepositoryModule;
import com.rashwan.reactive_popular_movies.data.model.ActorProfileImage;
import com.rashwan.reactive_popular_movies.data.model.ActorTaggedImage;
import com.rashwan.reactive_popular_movies.data.model.Cast;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.data.model.Trailer;
import com.rashwan.reactive_popular_movies.feature.actorDetails.actorInfo.injection.ActorInfoComponent;
import com.rashwan.reactive_popular_movies.feature.actorDetails.actorInfo.injection.ActorInfoModule;
import com.rashwan.reactive_popular_movies.feature.actorDetails.actorMovies.injection.ActorMoviesComponent;
import com.rashwan.reactive_popular_movies.feature.actorDetails.actorMovies.injection.ActorMoviesModule;
import com.rashwan.reactive_popular_movies.feature.actorDetails.injection.ActorDetailsComponent;
import com.rashwan.reactive_popular_movies.feature.actorDetails.injection.ActorDetailsModule;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.di.BrowseMoviesComponent;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.di.BrowseMoviesModule;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.nearbyMovies.di.NearbyMoviesComponent;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.nearbyMovies.di.NearbyMoviesModule;
import com.rashwan.reactive_popular_movies.feature.favoriteMovies.di.FavoriteMoviesComponent;
import com.rashwan.reactive_popular_movies.feature.favoriteMovies.di.FavoriteMoviesModule;
import com.rashwan.reactive_popular_movies.feature.movieDetails.di.MovieDetailsComponent;
import com.rashwan.reactive_popular_movies.feature.movieDetails.di.MovieDetailsModule;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieCast.di.MovieCastComponent;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieCast.di.MovieCastModule;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieInfo.di.MovieInfoComponent;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieInfo.di.MovieInfoModule;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieReviews.di.MovieReviewsComponent;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieReviews.di.MovieReviewsModule;
import com.rashwan.reactive_popular_movies.feature.watchlistMovies.di.WatchlistComponent;
import com.rashwan.reactive_popular_movies.feature.watchlistMovies.di.WatchlistModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by rashwan on 6/24/16.
 */
@Singleton
@Component(modules = {ApplicationModule.class, CastRepositoryModule.class, MoviesRepositoryModule.class})
public interface ApplicationComponent {
    void inject(PopularMoviesApplication target);
    void inject(Movie target);
    void inject(Trailer target);
    void inject(Cast target);
    void inject(ActorTaggedImage target);
    void inject(ActorProfileImage target);

    BrowseMoviesComponent plus(BrowseMoviesModule browseMoviesModule);
    MovieDetailsComponent plus(MovieDetailsModule movieDetailsModule);
    MovieInfoComponent plus(MovieInfoModule movieInfoModule);
    MovieReviewsComponent plus(MovieReviewsModule movieReviewsModule);
    NearbyMoviesComponent plus(NearbyMoviesModule nearbyMoviesModule);
    FavoriteMoviesComponent plus(FavoriteMoviesModule favoriteMoviesModule);
    WatchlistComponent plus(WatchlistModule watchlistModule);
    MovieCastComponent plus(MovieCastModule movieCastModule);
    ActorDetailsComponent plus(ActorDetailsModule actorDetailsModule);
    ActorInfoComponent plus(ActorInfoModule actorInfoModule);
    ActorMoviesComponent plus(ActorMoviesModule actorMoviesModule);

}

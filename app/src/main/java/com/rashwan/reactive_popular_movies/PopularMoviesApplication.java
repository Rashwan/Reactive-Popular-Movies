package com.rashwan.reactive_popular_movies;

import android.app.Application;

import com.rashwan.reactive_popular_movies.DI.ApplicationComponent;
import com.rashwan.reactive_popular_movies.DI.ApplicationModule;
import com.rashwan.reactive_popular_movies.DI.DaggerApplicationComponent;
import com.rashwan.reactive_popular_movies.feature.actorDetails.actorInfo.injection.ActorInfoComponent;
import com.rashwan.reactive_popular_movies.feature.actorDetails.actorInfo.injection.ActorInfoModule;
import com.rashwan.reactive_popular_movies.feature.actorDetails.actorMovies.injection.ActorMoviesComponent;
import com.rashwan.reactive_popular_movies.feature.actorDetails.actorMovies.injection.ActorMoviesModule;
import com.rashwan.reactive_popular_movies.feature.actorDetails.injection.ActorDetailsComponent;
import com.rashwan.reactive_popular_movies.feature.actorDetails.injection.ActorDetailsModule;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.injection.BrowseMoviesComponent;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.injection.BrowseMoviesModule;
import com.rashwan.reactive_popular_movies.feature.favoriteMovies.injection.FavoriteMoviesComponent;
import com.rashwan.reactive_popular_movies.feature.favoriteMovies.injection.FavoriteMoviesModule;
import com.rashwan.reactive_popular_movies.feature.movieDetails.injection.MovieDetailsComponent;
import com.rashwan.reactive_popular_movies.feature.movieDetails.injection.MovieDetailsModule;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.nearbyMovies.injection.NearbyMoviesComponent;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.nearbyMovies.injection.NearbyMoviesModule;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieCast.injection.MovieCastComponent;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieCast.injection.MovieCastModule;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieInfo.injection.MovieInfoComponent;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieInfo.injection.MovieInfoModule;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieReviews.injection.MovieReviewsComponent;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieReviews.injection.MovieReviewsModule;
import com.rashwan.reactive_popular_movies.feature.watchlistMovies.injection.WatchlistComponent;
import com.rashwan.reactive_popular_movies.feature.watchlistMovies.injection.WatchlistModule;

import timber.log.Timber;

/**
 * Created by rashwan on 6/23/16.
 */

public class PopularMoviesApplication extends Application {
    private static ApplicationComponent applicationComponent;
    private BrowseMoviesComponent browseMoviesComponent;
    private MovieDetailsComponent movieDetailsComponent;
    private MovieInfoComponent movieInfoComponent;
    private NearbyMoviesComponent nearbyMoviesComponent;
    private FavoriteMoviesComponent favoriteMoviesComponent;
    private WatchlistComponent watchlistComponent;
    private MovieReviewsComponent movieReviewsComponent;
    private MovieCastComponent movieCastComponent;
    private ActorDetailsComponent actorDetailsComponent;
    private ActorInfoComponent actorInfoComponent;
    private ActorMoviesComponent actorMoviesComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = createAppComponent();

        Timber.plant(new Timber.DebugTree() {
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                return super.createStackElementTag(element) + ':' + element.getLineNumber();
            }
        });


    }

    private ApplicationComponent createAppComponent() {
        return DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this))
                .build();
    }

    public BrowseMoviesComponent createBrowseMoviesComponent(){
         browseMoviesComponent = applicationComponent.plus(new BrowseMoviesModule());
        return browseMoviesComponent;
    }

    public MovieDetailsComponent createMovieDetailsComponent(){
        movieDetailsComponent = applicationComponent.plus(new MovieDetailsModule());
        return movieDetailsComponent;
    }

    public NearbyMoviesComponent createNearbyMoviesComponent(){
        nearbyMoviesComponent = applicationComponent.plus(new NearbyMoviesModule());
        return nearbyMoviesComponent;
    }

    public FavoriteMoviesComponent createFavoriteMoviesComponent(){
        favoriteMoviesComponent = applicationComponent.plus(new FavoriteMoviesModule());
        return favoriteMoviesComponent;
    }

    public WatchlistComponent createWatchlistComponent(){
        watchlistComponent = applicationComponent.plus(new WatchlistModule());
        return watchlistComponent;
    }
    public MovieInfoComponent createMovieInfoComponent(){
        movieInfoComponent = applicationComponent.plus(new MovieInfoModule());
        return movieInfoComponent;
    }
    public MovieReviewsComponent createMovieReviewsComponent(){
        movieReviewsComponent = applicationComponent.plus(new MovieReviewsModule());
        return movieReviewsComponent;
    }
    public MovieCastComponent createMovieCastComponent(){
        movieCastComponent = applicationComponent.plus(new MovieCastModule());
        return movieCastComponent;
    }
    public ActorDetailsComponent createActorDetailsComponent(){
        actorDetailsComponent = applicationComponent.plus(new ActorDetailsModule());
        return actorDetailsComponent;
    }
    public ActorInfoComponent createActorInfoComponent(){
        actorInfoComponent = applicationComponent.plus(new ActorInfoModule());
        return actorInfoComponent;
    }

    public ActorMoviesComponent createActorMoviesComponent(){
        actorMoviesComponent = applicationComponent.plus(new ActorMoviesModule());
        return actorMoviesComponent;
    }

    public void releaseBrowseMoviesComponent(){
        browseMoviesComponent = null;
    }
    public void releaseMovieDetailsComponent(){
        movieDetailsComponent = null;
    }
    public void releaseNearbyMoviesComponent(){
        nearbyMoviesComponent = null;
    }
    public void releaseFavoriteMoviesComponent(){
        favoriteMoviesComponent = null;
    }
    public void releaseWatchlistMoviesComponent(){
        watchlistComponent = null;
    }
    public void releaseMovieInfoComponent(){
        movieInfoComponent = null;
    }
    public void releaseMovieReviewsComponent(){
        movieReviewsComponent = null;
    }
    public void releaseMovieCastComponent(){
        movieCastComponent = null;
    }
    public void releaseActorDetailsComponent(){actorDetailsComponent = null;}
    public void releaseActorInfoComponent(){actorInfoComponent = null;}
    public void releaseActorMoviesComponent(){actorMoviesComponent = null;}



    public static ApplicationComponent getApplicationComponent(){
        return applicationComponent;
    }
}

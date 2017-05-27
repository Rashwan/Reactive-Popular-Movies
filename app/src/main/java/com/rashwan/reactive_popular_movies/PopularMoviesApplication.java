package com.rashwan.reactive_popular_movies;

import android.app.Application;

import com.rashwan.reactive_popular_movies.dI.ApplicationComponent;
import com.rashwan.reactive_popular_movies.dI.ApplicationModule;
import com.rashwan.reactive_popular_movies.dI.DaggerApplicationComponent;
import com.rashwan.reactive_popular_movies.data.di.CastRepositoryModule;
import com.rashwan.reactive_popular_movies.data.di.MoviesRepositoryModule;
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
                .castRepositoryModule(new CastRepositoryModule())
                .moviesRepositoryModule(new MoviesRepositoryModule())
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

package com.rashwan.reactive_popular_movies;

import android.app.Application;

import com.rashwan.reactive_popular_movies.DI.ApplicationComponent;
import com.rashwan.reactive_popular_movies.DI.ApplicationModule;
import com.rashwan.reactive_popular_movies.DI.DaggerApplicationComponent;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.injection.BrowseMoviesComponent;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.injection.BrowseMoviesModule;
import com.rashwan.reactive_popular_movies.feature.favoriteMovies.injection.FavoriteMoviesComponent;
import com.rashwan.reactive_popular_movies.feature.favoriteMovies.injection.FavoriteMoviesModule;
import com.rashwan.reactive_popular_movies.feature.movieDetails.injection.MovieDetailsComponent;
import com.rashwan.reactive_popular_movies.feature.movieDetails.injection.MovieDetailsModule;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.nearbyMovies.injection.NearbyMoviesComponent;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.nearbyMovies.injection.NearbyMoviesModule;

import timber.log.Timber;

/**
 * Created by rashwan on 6/23/16.
 */

public class PopularMoviesApplication extends Application {
    private static ApplicationComponent applicationComponent;
    private BrowseMoviesComponent browseMoviesComponent;
    private MovieDetailsComponent movieDetailsComponent;
    private NearbyMoviesComponent nearbyMoviesComponent;
    private FavoriteMoviesComponent favoriteMoviesComponent;

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


    public static ApplicationComponent getApplicationComponent(){
        return applicationComponent;
    }
}

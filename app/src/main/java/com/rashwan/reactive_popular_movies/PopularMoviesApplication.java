package com.rashwan.reactive_popular_movies;

import android.app.Application;

import com.rashwan.reactive_popular_movies.DI.ApplicationComponent;
import com.rashwan.reactive_popular_movies.DI.ApplicationModule;
import com.rashwan.reactive_popular_movies.feature.browseMovies.injection.BrowseMoviesComponent;
import com.rashwan.reactive_popular_movies.feature.browseMovies.injection.BrowseMoviesModule;
import com.rashwan.reactive_popular_movies.DI.DaggerApplicationComponent;
import com.rashwan.reactive_popular_movies.feature.movieDetails.injection.MovieDetailsComponent;
import com.rashwan.reactive_popular_movies.feature.movieDetails.injection.MovieDetailsModule;

import timber.log.Timber;

/**
 * Created by rashwan on 6/23/16.
 */

public class PopularMoviesApplication extends Application {
    private static ApplicationComponent applicationComponent;
    private BrowseMoviesComponent browseMoviesComponent;
    private MovieDetailsComponent movieDetailsComponent;
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

    public void releaseBrowseMoviesComponent(){
        browseMoviesComponent = null;
    }
    public void releaseMovieDetailsComponent(){
        movieDetailsComponent = null;
    }

    public static ApplicationComponent getApplicationComponent(){
        return applicationComponent;
    }
}

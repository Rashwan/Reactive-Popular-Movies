package com.rashwan.reactive_popular_movies;

import android.app.Application;

import com.rashwan.reactive_popular_movies.DI.ApplicationComponent;
import com.rashwan.reactive_popular_movies.DI.ApplicationModule;
import com.rashwan.reactive_popular_movies.DI.DaggerApplicationComponent;
import com.rashwan.reactive_popular_movies.service.MoviesService;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by rashwan on 6/23/16.
 */

public class PopularMoviesApplication extends Application {
    private static ApplicationComponent component;
    @Inject MoviesService moviesServiceImp;
    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this))
                .build();
        Timber.plant(new Timber.DebugTree() {
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                return super.createStackElementTag(element) + ':' + element.getLineNumber();
            }
        });
        Timber.d("Hello!");

//        getComponent().inject(this);
//        moviesServiceImp.getPopularMovies().subscribe(
//                moviesResponse -> Timber.d(moviesResponse.getMovies().get(0).toString()),
//                Throwable::printStackTrace,
//                () -> Timber.d("Finished Movies Request"));

    }
    public static ApplicationComponent getComponent(){
        return component;
    }
}

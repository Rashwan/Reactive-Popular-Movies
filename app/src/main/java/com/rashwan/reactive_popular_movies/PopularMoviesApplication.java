package com.rashwan.reactive_popular_movies;

import android.app.Application;

import com.rashwan.reactive_popular_movies.DI.ApplicationComponent;
import com.rashwan.reactive_popular_movies.DI.ApplicationModule;
import com.rashwan.reactive_popular_movies.DI.DaggerApplicationComponent;
import com.rashwan.reactive_popular_movies.data.MovieDatabaseHelper;
import com.rashwan.reactive_popular_movies.service.MoviesService;
import com.squareup.sqlbrite.BriteDatabase;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by rashwan on 6/23/16.
 */

public class PopularMoviesApplication extends Application {
    private static ApplicationComponent component;
    @Inject MoviesService moviesServiceImp;
    @Inject MovieDatabaseHelper databaseHelper;
    @Inject BriteDatabase db;
    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this))
                .build();
//        component.inject(this);
        Timber.plant(new Timber.DebugTree() {
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                return super.createStackElementTag(element) + ':' + element.getLineNumber();
            }
        });

        Timber.d("Hello!");
//        Observable<Movie> movieObservable = databaseHelper.getMovie(db,Long.valueOf(489));
//        Observable<List<Movie>> moviesObservable = databaseHelper.getMovies(db);
//        databaseHelper.insert(db, Long.valueOf(54612), "Test Movie2", "5/5/2015", "8.8", "blaasdablbalba"
//            , "/adwdqweqwasd", "hjkfghvbghjghj");
//        movieObservable.observeOn(AndroidSchedulers.mainThread())
//                .subscribe(movie -> Timber.d(movie.toString()),Throwable::printStackTrace
//                ,() -> Timber.d("finished getting movie"));
//        moviesObservable.observeOn(AndroidSchedulers.mainThread())
//                .subscribe(movies ->
//                        Timber.d("List Size: %d , Second Movie: %s",movies.size(),movies.get(1).toString())
//                ,Throwable::printStackTrace,() -> Timber.d("finished getting movies"));



    }
    public static ApplicationComponent getComponent(){
        return component;
    }
}

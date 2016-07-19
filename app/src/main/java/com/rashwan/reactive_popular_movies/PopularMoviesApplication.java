package com.rashwan.reactive_popular_movies;

import android.app.Application;

import com.rashwan.reactive_popular_movies.DI.ApplicationComponent;
import com.rashwan.reactive_popular_movies.DI.ApplicationModule;
import com.rashwan.reactive_popular_movies.DI.DaggerApplicationComponent;
import com.rashwan.reactive_popular_movies.data.MovieDatabaseCrud;
import com.rashwan.reactive_popular_movies.service.MoviesService;

import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by rashwan on 6/23/16.
 */

public class PopularMoviesApplication extends Application {
    private static ApplicationComponent component;
    @Inject MoviesService moviesServiceImp;
    @Inject MovieDatabaseCrud db;
    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this))
                .build();
        component.inject(this);
        Timber.plant(new Timber.DebugTree() {
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                return super.createStackElementTag(element) + ':' + element.getLineNumber();
            }
        });

        Timber.d("Hello!");

        AtomicInteger queries = new AtomicInteger();

//        Observable<Movie> movieObservable = db.getMovie(48L);
//        Observable<List<Movie>> moviesObservable = db.getMovies();
//        movieObservable.observeOn(AndroidSchedulers.mainThread())
//                .subscribe(movie -> {
//                    queries.getAndIncrement();
//                    Timber.d("From inside  movie queries: %s",queries);
//                        },Throwable::printStackTrace
//                ,() -> Timber.d("finished getting movie"));
//        moviesObservable.observeOn(AndroidSchedulers.mainThread())
//                .subscribe(movies ->{
//                        queries.getAndIncrement();
//                    Timber.d("From inside all movies queries: %s",queries);
//                }
//                ,Throwable::printStackTrace,() -> Timber.d("finished getting movies"));
//
//        db.insert(48L,"Test 1","8/8/2018","7.1","asfdfedfasc","/asdqwea","/adqwedasqeqehj");
//        db.insert(4884L,"Test 2","8/8/2014","7.5","asfdasc","/asdqwadsqwea","/adasqeqehj");
//
//        Observable.interval(1000, TimeUnit.MILLISECONDS, Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                .take(7).subscribe(aLong ->Timber.d("Number of queries : %s",queries.toString())
//        );

    }
    public static ApplicationComponent getComponent(){
        return component;
    }
}

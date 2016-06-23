package com.rashwan.reactive_popular_movies.common;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by rashwan on 6/23/16.
 */

public class PopularMoviesApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree(){
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                return super.createStackElementTag(element) + ':' + element.getLineNumber();
            }
        });
        Timber.d("Hello!");
    }
}

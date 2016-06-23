package com.rashwan.reactive_popular_movies.DI;

import com.rashwan.reactive_popular_movies.common.PopularMoviesApplication;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by rashwan on 6/24/16.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(PopularMoviesApplication target);
}

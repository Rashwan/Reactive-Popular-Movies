package com.rashwan.reactive_popular_movies.feature.movieDetails.movieCast.injection;

import com.rashwan.reactive_popular_movies.DI.PerFragment;
import com.rashwan.reactive_popular_movies.data.CastRepositoryComponent;
import com.rashwan.reactive_popular_movies.data.CastRepositoryModule;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieCast.MovieCastFragment;

import dagger.Subcomponent;

/**
 * Created by rashwan on 4/21/17.
 */
@PerFragment
@Subcomponent(modules = {MovieCastModule.class})
public interface MovieCastComponent {
    @Subcomponent.Builder
    interface Builder{
        Builder movieCastModule(MovieCastModule movieCastModule);
        MovieCastComponent build();
    }
    CastRepositoryComponent plus(CastRepositoryModule castRepositoryModule);
    void inject(MovieCastFragment target);
}

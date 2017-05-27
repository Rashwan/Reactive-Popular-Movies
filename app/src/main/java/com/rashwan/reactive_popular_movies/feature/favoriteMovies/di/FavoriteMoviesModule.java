package com.rashwan.reactive_popular_movies.feature.favoriteMovies.di;

import com.rashwan.reactive_popular_movies.feature.discoverMovies.BrowseMoviesAdapter;
import com.rashwan.reactive_popular_movies.feature.favoriteMovies.FavoriteMoviesPresenter;
import com.rashwan.reactive_popular_movies.service.TMDBService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rashwan on 4/7/17.
 */
@Module
public class FavoriteMoviesModule {
    @Provides
    public BrowseMoviesAdapter provideBrowseMoviesAdapter(){
        return new BrowseMoviesAdapter();
    }
    @Provides
    public FavoriteMoviesPresenter provideFavoriteMoviesPresenter(TMDBService TMDBService){
        return new FavoriteMoviesPresenter(TMDBService);
    }
}

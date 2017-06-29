package com.rashwan.reactive_popular_movies.data.remote;

import android.app.Application;

import com.rashwan.reactive_popular_movies.common.utilities.Exceptions;
import com.rashwan.reactive_popular_movies.common.utilities.Utilities;
import com.rashwan.reactive_popular_movies.data.MoviesDataSource;
import com.rashwan.reactive_popular_movies.data.di.qualifier.Remote;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.data.model.MovieDetails;
import com.rashwan.reactive_popular_movies.data.model.Review;
import com.rashwan.reactive_popular_movies.data.model.Trailer;

import java.util.List;

import javax.inject.Singleton;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by rashwan on 5/25/17.
 */
@Singleton
@Remote
public class MoviesRemoteDataSource implements MoviesDataSource{
    private Retrofit tmdbRetrofit;
    private Retrofit omdbRetrofit;
    private Application application;

    public MoviesRemoteDataSource(Application application,Retrofit tmdbRetrofit, Retrofit omdbRetrofit) {
        this.tmdbRetrofit = tmdbRetrofit;
        this.omdbRetrofit = omdbRetrofit;
        this.application = application;
    }

    @Override
    public Observable<List<Movie>> getPopularMovies(int page) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException(page == 1,"No internet connection"));
        }
        return tmdbRetrofit.create(TMDBApi.class).getPopularMovies(page);
    }

    @Override
    public Observable<List<Movie>> getTopRatedMovies(int page) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException(page == 1,"No internet connection"));
        }
        return tmdbRetrofit.create(TMDBApi.class).getTopRatedMovies(page);
    }

    @Override
    public Observable<List<Movie>> getUpcomingMovies(int page) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException(page == 1,"No internet connection"));
        }
        return tmdbRetrofit.create(TMDBApi.class).getUpcomingMovies(page);
    }



    @Override
    public Observable<List<Trailer>> getMovieTrailers(long id) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException("No internet connection"));
        }
        return tmdbRetrofit.create(TMDBApi.class).getMovieTrailers(id)
                .map(trailers -> {
                    if (trailers.size() > 7){
                        return trailers.subList(0,7);
                    }
                    return trailers;
                });
    }

    @Override
    public Observable<List<Review>> getMovieReview(long id) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException("No internet connection"));
        }
        return tmdbRetrofit.create(TMDBApi.class).getMovieReviews(id);
    }

    @Override
    public Observable<List<Movie>> getSimilarMovies(long id) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException("No internet connection"));
        }
        return tmdbRetrofit.create(TMDBApi.class).getSimilarMovies(id).map(movies -> {
            if (movies.size() > 7){
                return movies.subList(0,7);
            }else {
                return movies;
            }
        });
    }

    @Override
    public Observable<Movie> getMovieDetails(long id) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException("No internet connection"));
        }

        return tmdbRetrofit.create(TMDBApi.class).getMovieDetails(id);
    }

    @Override
    public Observable<MovieDetails> getMovieOMDBDetails(String tmdbId) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException("No internet connection"));
        }
        return omdbRetrofit.create(OMDBApi.class).getMovieDetails(tmdbId);
    }

    @Override
    public Observable<Movie> getMovieById(Long id) {
        return null;
    }

    @Override
    public Observable<Long> findMovieByID(Long movieId) {
        return null;
    }

    @Override
    public Observable<List<Movie>> getFavoriteMovies() {
        return null;
    }

    @Override
    public Observable<List<Long>> getFavoriteMoviesIds() {
        return null;
    }

    @Override
    public Observable<Boolean> isMovieFavorite(Long movieId) {
        return null;
    }

    @Override
    public Observable<List<Movie>> getWatchlistMovies() {
        return null;
    }

    @Override
    public Observable<Boolean> isMovieInWatchlist(Long movieId) {
        return null;
    }
}

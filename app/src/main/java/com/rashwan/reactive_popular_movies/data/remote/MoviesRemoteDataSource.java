package com.rashwan.reactive_popular_movies.data.remote;

import android.app.Application;

import com.rashwan.reactive_popular_movies.common.utilities.Exceptions;
import com.rashwan.reactive_popular_movies.common.utilities.Utilities;
import com.rashwan.reactive_popular_movies.data.MoviesDataSource;
import com.rashwan.reactive_popular_movies.data.di.qualifier.Remote;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.data.model.MoviesResponse;
import com.rashwan.reactive_popular_movies.data.model.ReviewResponse;
import com.rashwan.reactive_popular_movies.data.model.Trailer;
import com.rashwan.reactive_popular_movies.data.model.TrailersResponse;

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
    private Retrofit retrofit;
    private Application application;

    public MoviesRemoteDataSource(Retrofit retrofit,Application application) {
        this.retrofit = retrofit;
        this.application = application;
    }

    @Override
    public Observable<List<Movie>> getPopularMovies(int page) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException(page == 1,"No internet connection"));
        }
        return retrofit.create(TMDBApi.class).getPopularMovies(page)
                .map(MoviesResponse::getMovies);
    }

    @Override
    public Observable<List<Movie>> getTopRatedMovies(int page) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException(page == 1,"No internet connection"));
        }
        return retrofit.create(TMDBApi.class).getTopRatedMovies(page)
                .map(MoviesResponse::getMovies);
    }

    @Override
    public Observable<List<Movie>> getUpcomingMovies(int page) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException(page == 1,"No internet connection"));
        }
        return retrofit.create(TMDBApi.class).getUpcomingMovies(page,"US")
                .map(MoviesResponse::getMovies);
    }



    @Override
    public Observable<List<Trailer>> getMovieTrailers(long id) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException("No internet connection"));
        }
        return retrofit.create(TMDBApi.class).getMovieTrailers(id)
                .map(TrailersResponse::getTrailers);
    }

    @Override
    public Observable<ReviewResponse> getMovieReview(long id) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException("No internet connection"));
        }
        return retrofit.create(TMDBApi.class).getMovieReviews(id);
    }

    @Override
    public Observable<List<Movie>> getSimilarMovies(long id) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException("No internet connection"));
        }
        return retrofit.create(TMDBApi.class).getSimilarMovies(id)
                .map((moviesResponse) -> moviesResponse.getMovies().subList(0,7));
    }

    @Override
    public Observable<Movie> getMovieDetails(long id) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException("No internet connection"));
        }

        return retrofit.create(TMDBApi.class).getMovieDetails(id);
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

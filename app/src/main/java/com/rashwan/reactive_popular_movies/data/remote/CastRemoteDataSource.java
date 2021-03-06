package com.rashwan.reactive_popular_movies.data.remote;

import android.app.Application;

import com.rashwan.reactive_popular_movies.common.utilities.Exceptions;
import com.rashwan.reactive_popular_movies.common.utilities.Utilities;
import com.rashwan.reactive_popular_movies.data.CastDataSource;
import com.rashwan.reactive_popular_movies.data.di.qualifier.Remote;
import com.rashwan.reactive_popular_movies.data.model.ActorMovie;
import com.rashwan.reactive_popular_movies.data.model.ActorProfileImage;
import com.rashwan.reactive_popular_movies.data.model.ActorTaggedImage;
import com.rashwan.reactive_popular_movies.data.model.Cast;
import com.rashwan.reactive_popular_movies.data.model.CastDetails;

import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by rashwan on 5/27/17.
 */
@Remote
public class CastRemoteDataSource implements CastDataSource {
    private Application application;
    private Retrofit retrofit;

    public CastRemoteDataSource(Application application, Retrofit retrofit) {
        this.application = application;
        this.retrofit = retrofit;
    }

    @Override
    public Observable<List<Cast>> getMovieCast(long movieId) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException("No internet connection"));
        }
        return retrofit.create(TMDBApi.class).getMovieCredits(movieId)
                .map(castList -> {
                    if (castList.size() > 20) {
                            return castList.subList(0, 20);
                        }
                        return castList;
                    }
                );
    }

    @Override
    public Observable<CastDetails> getActorDetails(long castId) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException("No internet connection"));
        }
        return retrofit.create(TMDBApi.class).getActorDetails(castId);
    }

    @Override
    public Observable<ActorTaggedImage> getActorTaggedImages(long castId) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException("No internet connection"));
        }
        return retrofit.create(TMDBApi.class).getActorTaggedImaged(castId)
                .flatMap(Observable::from)
                .filter(actorTaggedImage -> actorTaggedImage.aspectRatio() == 1.7777777777778)
                .take(1);
    }

    @Override
    public Observable<List<ActorProfileImage>> getActorProfileImages(long castId) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException("No internet connection"));
        }
        return retrofit.create(TMDBApi.class).getActorProfileImages(castId)
                .flatMap(actorProfileImagesResponse ->{
                    if (actorProfileImagesResponse != null) {
                        return Observable.from(actorProfileImagesResponse.actorProfileImages());
                    }
                    return Observable.empty();
                })
                .take(7)
                .toList();
    }

    @Override
    public Observable<List<ActorMovie>> getActorMovies(long castId) {
        if (!Utilities.isNetworkAvailable(application)){
            return Observable.error(new Exceptions.NoInternetException("No internet connection"));
        }
        return retrofit.create(TMDBApi.class).getActorMovies(castId);
    }

}

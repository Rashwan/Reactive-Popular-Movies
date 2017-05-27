package com.rashwan.reactive_popular_movies.data;

import com.rashwan.reactive_popular_movies.data.di.qualifier.Local;
import com.rashwan.reactive_popular_movies.data.di.qualifier.Remote;
import com.rashwan.reactive_popular_movies.data.model.ActorMovie;
import com.rashwan.reactive_popular_movies.data.model.ActorProfileImage;
import com.rashwan.reactive_popular_movies.data.model.ActorTaggedImage;
import com.rashwan.reactive_popular_movies.data.model.Cast;
import com.rashwan.reactive_popular_movies.data.model.CastDetails;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by rashwan on 5/27/17.
 */
@Singleton
public class CastRepository implements CastDataSource {
    private CastDataSource localCastDataSource;
    private CastDataSource remoteCastDataSource;

    @Inject
    public CastRepository(@Local CastDataSource localCastDataSource, @Remote CastDataSource remoteCastDataSource) {
        this.localCastDataSource = localCastDataSource;
        this.remoteCastDataSource = remoteCastDataSource;
    }

    @Override
    public Observable<List<Cast>> getMovieCast(long movieId) {
        return remoteCastDataSource.getMovieCast(movieId);
    }

    @Override
    public Observable<CastDetails> getActorDetails(long castId) {
        return remoteCastDataSource.getActorDetails(castId);
    }

    @Override
    public Observable<List<ActorTaggedImage>> getActorTaggedImages(long castId) {
        return remoteCastDataSource.getActorTaggedImages(castId);
    }

    @Override
    public Observable<List<ActorProfileImage>> getActorProfileImages(long castId) {
        return remoteCastDataSource.getActorProfileImages(castId);
    }

    @Override
    public Observable<List<ActorMovie>> getActorMovies(long castId) {
        return remoteCastDataSource.getActorMovies(castId);
    }
}

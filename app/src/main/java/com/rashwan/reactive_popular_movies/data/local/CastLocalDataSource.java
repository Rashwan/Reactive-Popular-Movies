package com.rashwan.reactive_popular_movies.data.local;

import com.rashwan.reactive_popular_movies.data.CastDataSource;
import com.rashwan.reactive_popular_movies.data.di.qualifier.Local;
import com.rashwan.reactive_popular_movies.data.model.ActorMovie;
import com.rashwan.reactive_popular_movies.data.model.ActorProfileImage;
import com.rashwan.reactive_popular_movies.data.model.ActorTaggedImage;
import com.rashwan.reactive_popular_movies.data.model.Cast;
import com.rashwan.reactive_popular_movies.data.model.CastDetails;

import java.util.List;

import rx.Observable;

/**
 * Created by rashwan on 5/25/17.
 */
@Local
public class CastLocalDataSource implements CastDataSource {
    @Override
    public Observable<List<Cast>> getMovieCast(long movieId) {
        return null;
    }

    @Override
    public Observable<CastDetails> getActorDetails(long castId) {
        return null;
    }

    @Override
    public Observable<List<ActorTaggedImage>> getActorTaggedImages(long castId) {
        return null;
    }

    @Override
    public Observable<List<ActorProfileImage>> getActorProfileImages(long castId) {
        return null;
    }

    @Override
    public Observable<List<ActorMovie>> getActorMovies(long castId) {
        return null;
    }
}

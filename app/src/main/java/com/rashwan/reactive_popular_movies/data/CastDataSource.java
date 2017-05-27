package com.rashwan.reactive_popular_movies.data;

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

public interface CastDataSource {
    Observable<List<Cast>> getMovieCast(long movieId);
    Observable<CastDetails> getActorDetails(long castId);
    Observable<List<ActorTaggedImage>> getActorTaggedImages(long castId);
    Observable<List<ActorProfileImage>> getActorProfileImages(long castId);
    Observable<List<ActorMovie>> getActorMovies(long castId);
}

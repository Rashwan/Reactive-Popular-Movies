package com.rashwan.reactive_popular_movies.feature.movieDetails;

import com.rashwan.reactive_popular_movies.common.MvpView;
import com.rashwan.reactive_popular_movies.model.Trailer;

import java.util.List;

/**
 * Created by rashwan on 7/7/16.
 */

public interface MovieDetailsView extends MvpView {
    void showTrailers(List<Trailer> trailers);
}

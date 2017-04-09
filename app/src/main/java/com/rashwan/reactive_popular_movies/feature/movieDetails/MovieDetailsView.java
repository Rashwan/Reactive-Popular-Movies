package com.rashwan.reactive_popular_movies.feature.movieDetails;

import com.rashwan.reactive_popular_movies.common.MvpView;
import com.rashwan.reactive_popular_movies.data.model.Review;
import com.rashwan.reactive_popular_movies.data.model.Trailer;

import java.util.List;

/**
 * Created by rashwan on 7/7/16.
 */

public interface MovieDetailsView extends MvpView {
    void showTrailers(List<Trailer> trailers);
    void showReviews(List<Review> reviews);
    void showOfflineLayout();
    void hideOfflineLayout();
    void showFavoriteMovie();
    void showNonFavoriteMovie();
    void showWatchlistMovie();
    void showNormalMovie();

}

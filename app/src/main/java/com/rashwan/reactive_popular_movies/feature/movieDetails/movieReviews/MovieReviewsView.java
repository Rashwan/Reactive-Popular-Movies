package com.rashwan.reactive_popular_movies.feature.movieDetails.movieReviews;

import com.rashwan.reactive_popular_movies.common.MvpView;
import com.rashwan.reactive_popular_movies.data.model.Review;

import java.util.List;

/**
 * Created by rashwan on 4/20/17.
 */

public interface MovieReviewsView extends MvpView{
    void showReviews(List<Review> reviews);
    void showOfflineLayout();
    void hideOfflineLayout();
    void showNoReviewsMsg();
}

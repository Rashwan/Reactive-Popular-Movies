package com.rashwan.reactive_popular_movies.feature.movieDetails;

import com.rashwan.reactive_popular_movies.common.MvpView;

/**
 * Created by rashwan on 4/20/17.
 */

public interface MovieDetailsView extends MvpView{
    void showWatchlistMovie();
    void showNormalMovie();
    void showFavoriteMovie();
    void showNonFavoriteMovie();

}

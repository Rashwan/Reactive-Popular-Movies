package com.rashwan.reactive_popular_movies.feature.discoverMovies.nearbyMovies;

import com.google.android.gms.common.ConnectionResult;
import com.rashwan.reactive_popular_movies.common.MvpView;
import com.rashwan.reactive_popular_movies.data.model.Movie;

/**
 * Created by rashwan on 1/30/17.
 */

public interface NearbyMoviesView extends MvpView {
    void resolveError(ConnectionResult connectionResult);
    void showNearbyMovie(Movie movie);
    void showProgress();
    void hideProgress();
    void clearScreen();
    void showOfflineLayout();
    void hideOfflineLayout();

}

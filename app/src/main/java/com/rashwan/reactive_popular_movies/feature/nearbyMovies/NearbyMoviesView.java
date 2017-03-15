package com.rashwan.reactive_popular_movies.feature.nearbyMovies;

import com.google.android.gms.common.ConnectionResult;
import com.rashwan.reactive_popular_movies.common.MvpView;

/**
 * Created by rashwan on 1/30/17.
 */

public interface NearbyMoviesView extends MvpView {
    void resolveError(ConnectionResult connectionResult);
    void hideNearbyStart();
    void showNearbyStart();
    void hideNearbyStop();
    void showNearbyStop();
    void showNearbyMovies();

}

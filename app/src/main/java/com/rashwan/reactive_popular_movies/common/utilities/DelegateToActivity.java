package com.rashwan.reactive_popular_movies.common.utilities;

import android.widget.ImageView;

import com.rashwan.reactive_popular_movies.data.model.Movie;

/**
 * Created by rashwan on 3/15/17.
 */

public interface DelegateToActivity {
    void delegateMovieClicked(Movie movie, ImageView sharedView);

}

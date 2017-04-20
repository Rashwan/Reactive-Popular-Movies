package com.rashwan.reactive_popular_movies.feature.movieDetails;

import android.net.Uri;

import com.rashwan.reactive_popular_movies.data.model.MovieDetails;

/**
 * Created by rashwan on 4/20/17.
 */

public interface ShowDetailsInActivity {
    void showRuntime(String runtime);
    void showOmdbDetails(MovieDetails movieDetails);
    void showReviewMessage(String message);
    void showShareIcon(String trailerUrl);
    void showPlayMainTrailer(Uri mainTrailerUrl);
}

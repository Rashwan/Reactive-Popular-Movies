package com.rashwan.reactive_popular_movies.feature.movieDetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.data.model.Movie;

/**
 * Created by rashwan on 7/3/16.
 */

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_MOVIE = "com.rashwan.reactive_popular_movies.feature.movieDetails.EXTRA_MOVIE";
    private static final String EXTRA_SHARED_ELEMENT_NAME = "com.rashwan.reactive_popular_movies.feature.movieDetails.EXTRA_SHARED_ELEMENT_NAME";
    private static final String MOVIE_DETAILS_FRAGMENT_TAG = "movie_details_fragment_tag";
    public static Intent getDetailsIntent(Context context,Movie movie,String sharedElementName){
        Intent intent = new Intent(context,MovieDetailsActivity.class);
        intent.putExtra(EXTRA_MOVIE,movie);
        intent.putExtra(EXTRA_SHARED_ELEMENT_NAME,sharedElementName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra(EXTRA_MOVIE);
        String sharedElementName = intent.getStringExtra(EXTRA_SHARED_ELEMENT_NAME);

        if (movie == null){
            throw new IllegalArgumentException("Movie Details Activity requires a Movie object");
        }
        setContentView(R.layout.activity_movie_details);
        FragmentManager fragmentManager = getSupportFragmentManager();
        MovieDetailsFragment movieDetailsFragment = (MovieDetailsFragment)
                fragmentManager.findFragmentByTag(MOVIE_DETAILS_FRAGMENT_TAG);
        if (savedInstanceState == null && movieDetailsFragment == null){
            movieDetailsFragment = MovieDetailsFragment.newInstance(movie,sharedElementName);
            fragmentManager.beginTransaction()
                    .replace(R.id.movie_details_container,movieDetailsFragment,MOVIE_DETAILS_FRAGMENT_TAG)
                    .commit();
        }
    }
    @Override
    public boolean onNavigateUp() {
        supportFinishAfterTransition();
        return true;
    }
}

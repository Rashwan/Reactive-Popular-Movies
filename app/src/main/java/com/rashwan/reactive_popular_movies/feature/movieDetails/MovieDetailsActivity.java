package com.rashwan.reactive_popular_movies.feature.movieDetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.model.Movie;

/**
 * Created by rashwan on 7/3/16.
 */

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_MOVIE = "com.rashwan.reactive_popular_movies.feature.movieDetails.EXTRA_MOVIE";

    public static Intent getDetailsIntent(Context context,Movie movie){
        Intent intent = new Intent(context,MovieDetailsActivity.class);
        intent.putExtra(EXTRA_MOVIE,movie);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra(EXTRA_MOVIE);
        if (movie == null){
            throw new IllegalArgumentException("Movie Details Activity requires a Movie object");
        }

        setContentView(R.layout.activity_movie_details);
        MovieDetailsFragment movieDetailsFragment = MovieDetailsFragment.newInstance(movie);
        if (getFragmentManager().findFragmentById(R.id.movie_details_container) == null){
            getFragmentManager().beginTransaction().add(R.id.movie_details_container,movieDetailsFragment).commit();
        }
    }
}

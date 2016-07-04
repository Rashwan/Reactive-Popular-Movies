package com.rashwan.reactive_popular_movies.feature.movieDetails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rashwan.reactive_popular_movies.R;

/**
 * Created by rashwan on 7/3/16.
 */

public class MovieDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        if (getFragmentManager().findFragmentById(R.id.movie_details_container) == null){
            getFragmentManager().beginTransaction().add(R.id.movie_details_container,new MovieDetailsFragment()).commit();
        }
    }
}

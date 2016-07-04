package com.rashwan.reactive_popular_movies.feature.browseMovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.feature.movieDetails.MovieDetailsFragment;

public class BrowseMoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_movies);
        if (getFragmentManager().findFragmentById(R.id.browse_container) == null){
            getFragmentManager().beginTransaction().add(R.id.browse_container,new MovieDetailsFragment()).commit();
        }
    }
}

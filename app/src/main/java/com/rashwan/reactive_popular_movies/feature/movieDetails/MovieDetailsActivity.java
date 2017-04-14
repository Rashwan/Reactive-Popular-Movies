package com.rashwan.reactive_popular_movies.feature.movieDetails;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.common.utilities.DelegateToActivity;
import com.rashwan.reactive_popular_movies.data.model.Movie;

/**
 * Created by rashwan on 7/3/16.
 */

public class MovieDetailsActivity extends AppCompatActivity implements DelegateToActivity{

    private static final String EXTRA_MOVIE = "com.rashwan.reactive_popular_movies.feature.movieDetails.EXTRA_MOVIE";
    private static final String EXTRA_SHARED_ELEMENT_NAME = "com.rashwan.reactive_popular_movies.feature.movieDetails.EXTRA_SHARED_ELEMENT_NAME";
    private static final String TAG_MOVIE_DETAILS_FRAGMENT = "TAG_MOVIE_DETAILS_FRAGMENT";

    public static Intent getDetailsIntent(Context context, Movie movie, String sharedElementName){
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
        setContentView(R.layout.activity_movie_details);
        FragmentManager fragmentManager = getSupportFragmentManager();
        MovieDetailsFragment movieDetailsFragment = (MovieDetailsFragment)
                fragmentManager.findFragmentByTag(TAG_MOVIE_DETAILS_FRAGMENT);
        if (savedInstanceState == null && movieDetailsFragment == null){
            movieDetailsFragment = MovieDetailsFragment.newInstance(movie,sharedElementName);
            fragmentManager.beginTransaction()
                    .replace(R.id.movie_details_container,movieDetailsFragment, TAG_MOVIE_DETAILS_FRAGMENT)
                    .commit();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.transparent_black));
        }
    }
    @Override
    public boolean onNavigateUp() {
        supportFinishAfterTransition();
        return true;
    }

    @Override
    public void delegateMovieClicked(Movie movie, ImageView sharedView) {
        //Dosen't handle master/detail views
        Intent intent;
        String transitionName = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            transitionName = sharedView.getTransitionName();
            intent = getDetailsIntent(this,movie,transitionName);
            ActivityOptions activityOptions = ActivityOptions
                    .makeSceneTransitionAnimation(this,sharedView,sharedView.getTransitionName());
            startActivity(intent,activityOptions.toBundle());

        }else {
            intent = MovieDetailsActivity.getDetailsIntent(this,movie,transitionName);
            startActivity(intent);
        }
    }
}

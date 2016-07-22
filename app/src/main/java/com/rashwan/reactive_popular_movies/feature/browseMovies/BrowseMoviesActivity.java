package com.rashwan.reactive_popular_movies.feature.browseMovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.feature.movieDetails.MovieDetailsActivity;
import com.rashwan.reactive_popular_movies.feature.movieDetails.MovieDetailsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BrowseMoviesActivity extends AppCompatActivity implements BrowseMoviesFragment.DelegateToActivity{
    @BindView(R.id.browse_toolbar)
    Toolbar toolbar;
    @Nullable @BindView(R.id.details_container)
    FrameLayout detailsContainer;
    private Unbinder unbinder;
    private static final String BROWSE_MOVIES_FRAGMENT_TAG = "browse_movies_fragment_tag";
    android.support.v4.app.FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_movies);
        unbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();
        BrowseMoviesFragment browseMoviesFragment = (BrowseMoviesFragment) fragmentManager
                .findFragmentByTag(BROWSE_MOVIES_FRAGMENT_TAG);

        if (savedInstanceState == null && browseMoviesFragment == null){
            fragmentManager.beginTransaction()
                    .replace(R.id.browse_container,new BrowseMoviesFragment(),BROWSE_MOVIES_FRAGMENT_TAG)
                    .commit();
        }
    }

    private Boolean isTwoPane(){
        return detailsContainer != null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void delegateMovieClicked(Movie movie) {
        if (isTwoPane()){
            MovieDetailsFragment movieDetailsFragment = MovieDetailsFragment.newInstance(movie);
            fragmentManager.beginTransaction().replace(R.id.details_container,movieDetailsFragment).commit();
        }else {
            Intent intent = MovieDetailsActivity.getDetailsIntent(this,movie);
            startActivity(intent);
        }
    }
}

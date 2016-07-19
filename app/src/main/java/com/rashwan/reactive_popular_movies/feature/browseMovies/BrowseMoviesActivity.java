package com.rashwan.reactive_popular_movies.feature.browseMovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.rashwan.reactive_popular_movies.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BrowseMoviesActivity extends AppCompatActivity {
    @BindView(R.id.browse_toolbar)
    Toolbar toolbar;
    private Unbinder unbinder;
    private static final String BROWSE_MOVIES_FRAGMENT_TAG = "browse_movies_fragment_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_movies);
        unbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        BrowseMoviesFragment browseMoviesFragment = (BrowseMoviesFragment) fragmentManager
                .findFragmentByTag(BROWSE_MOVIES_FRAGMENT_TAG);

        if (savedInstanceState == null && browseMoviesFragment == null){
            fragmentManager.beginTransaction()
                    .replace(R.id.browse_container,new BrowseMoviesFragment(),BROWSE_MOVIES_FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}

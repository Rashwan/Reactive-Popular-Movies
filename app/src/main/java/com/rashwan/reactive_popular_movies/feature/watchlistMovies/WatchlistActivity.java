package com.rashwan.reactive_popular_movies.feature.watchlistMovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;

import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.feature.BaseActivity;

import butterknife.BindView;

/**
 * Created by rashwan on 4/9/17.
 */

public class WatchlistActivity extends BaseActivity {
    @BindView(R.id.navigation_view) NavigationView navigationView;
    @BindView(R.id.browse_toolbar) Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist_movies);
        navigationView.setCheckedItem(R.id.nav_watchlist);
        toolbar.setTitle(R.string.nav_watchlist_title);
        super.onCreateBaseActivity(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}

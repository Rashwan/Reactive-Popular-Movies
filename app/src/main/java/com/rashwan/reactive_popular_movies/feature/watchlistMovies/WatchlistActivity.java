package com.rashwan.reactive_popular_movies.feature.watchlistMovies;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.feature.BaseActivity;

/**
 * Created by rashwan on 4/9/17.
 */

public class WatchlistActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist_movies);
        super.onCreateBaseActivity(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}

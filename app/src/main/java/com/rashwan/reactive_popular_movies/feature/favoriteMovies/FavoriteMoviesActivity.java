package com.rashwan.reactive_popular_movies.feature.favoriteMovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.feature.BaseActivity;

import butterknife.BindView;

/**
 * Created by rashwan on 4/7/17.
 */

public class FavoriteMoviesActivity extends BaseActivity {
    @BindView(R.id.browse_toolbar) Toolbar favoritesToolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movies);
        setSupportActionBar(favoritesToolbar);
    }
}

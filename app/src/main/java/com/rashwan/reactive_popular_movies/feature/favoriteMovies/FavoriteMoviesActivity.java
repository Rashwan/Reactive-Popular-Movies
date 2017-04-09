package com.rashwan.reactive_popular_movies.feature.favoriteMovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;

import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.common.utilities.DelegateToActivity;
import com.rashwan.reactive_popular_movies.feature.BaseActivity;

import butterknife.BindView;

/**
 * Created by rashwan on 4/7/17.
 */

public class FavoriteMoviesActivity extends BaseActivity implements DelegateToActivity {

    @BindView(R.id.navigation_view) NavigationView navigationView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movies);
        navigationView.setCheckedItem(R.id.nav_favorites);
        super.onCreateBaseActivity(savedInstanceState);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}

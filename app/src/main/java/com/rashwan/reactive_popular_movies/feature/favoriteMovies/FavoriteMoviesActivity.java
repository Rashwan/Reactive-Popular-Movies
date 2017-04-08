package com.rashwan.reactive_popular_movies.feature.favoriteMovies;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.common.utilities.DelegateToActivity;
import com.rashwan.reactive_popular_movies.feature.BaseActivity;

/**
 * Created by rashwan on 4/7/17.
 */

public class FavoriteMoviesActivity extends BaseActivity implements DelegateToActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movies);
        super.onCreateBaseActivity(savedInstanceState);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}

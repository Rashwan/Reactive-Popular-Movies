package com.rashwan.reactive_popular_movies.feature.discoverMovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.common.utilities.DelegateToActivity;
import com.rashwan.reactive_popular_movies.feature.BaseActivity;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.nearbyMovies.NearbyMoviesFragment;

import butterknife.BindView;

public class BrowseMoviesActivity extends BaseActivity implements DelegateToActivity {

    @BindView(R.id.slidingTabs) TabLayout tabLayout;
    @BindView(R.id.viewpager) ViewPager viewPager;
    @BindView(R.id.navigation_view) NavigationView navigationView;
    private BrowseMoviesPagerAdapter browseMoviesPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_movies);
        navigationView.setCheckedItem(R.id.nav_discover);
        browseMoviesPagerAdapter = new BrowseMoviesPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(browseMoviesPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        super.onCreateBaseActivity(savedInstanceState);

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == NearbyMoviesFragment.REQUEST_RESOLVE_ERROR){
            browseMoviesPagerAdapter.getItem(viewPager.getCurrentItem())
                    .onActivityResult(requestCode,resultCode,data);
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}

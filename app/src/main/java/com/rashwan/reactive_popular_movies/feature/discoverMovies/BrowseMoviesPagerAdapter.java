package com.rashwan.reactive_popular_movies.feature.discoverMovies;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rashwan.reactive_popular_movies.feature.discoverMovies.nearbyMovies.NearbyMoviesFragment;

/**
 * Created by rashwan on 1/7/17.
 */

public class BrowseMoviesPagerAdapter extends FragmentPagerAdapter {
    private static final int PAGE_COUNT = 4;
    private final String tabTitles[] = new String[] { "Popular", "Top Rated", "Upcoming","Nearby" };
    private NearbyMoviesFragment nearbyMoviesFragment;

    public BrowseMoviesPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 3){
            if (nearbyMoviesFragment == null){
                nearbyMoviesFragment = NearbyMoviesFragment.newInstance();
            }
            return nearbyMoviesFragment;
        }else {
            return BrowseMoviesFragment.newInstance(position);
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

}

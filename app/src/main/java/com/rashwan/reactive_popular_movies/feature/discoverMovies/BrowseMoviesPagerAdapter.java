package com.rashwan.reactive_popular_movies.feature.discoverMovies;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.RecyclerView;

import com.rashwan.reactive_popular_movies.feature.discoverMovies.nearbyMovies.NearbyMoviesFragment;

import timber.log.Timber;

/**
 * Created by rashwan on 1/7/17.
 */

public class BrowseMoviesPagerAdapter extends FragmentPagerAdapter {
    private static final int PAGE_COUNT = 4;
    private final String tabTitles[] = new String[] { "Popular", "Top Rated", "Upcoming","Nearby" };
    private NearbyMoviesFragment nearbyMoviesFragment;
    private RecyclerView.RecycledViewPool pool;
    private BrowseMoviesFragment popularMoviesFragment;
    private BrowseMoviesFragment topratedMoviesFragment;
    private BrowseMoviesFragment upcomingMoviesFragment;

    public BrowseMoviesPagerAdapter(FragmentManager fm) {
        super(fm);
        pool = new RecyclerView.RecycledViewPool(){
            @Override
            public RecyclerView.ViewHolder getRecycledView(int viewType) {
                RecyclerView.ViewHolder scrap = super.getRecycledView(viewType);
                Timber.d("get holder from pool: " + scrap );
                return scrap;
            }

            @Override
            public void putRecycledView(RecyclerView.ViewHolder scrap) {
                super.putRecycledView(scrap);
                Timber.d("put holder to pool: " + scrap);
            }
            @Override
            public String toString() {
                return "ViewPool in adapter@"+Integer.toHexString(hashCode());
            }
        };
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                if (popularMoviesFragment == null){
                    Timber.d("popular fragment is null");
                    popularMoviesFragment = BrowseMoviesFragment.newInstance(position);
                    popularMoviesFragment.setPool(pool);
                }
                return popularMoviesFragment;
            case 1:
                if (topratedMoviesFragment == null){
                    Timber.d("top rated fragment is null");

                    topratedMoviesFragment = BrowseMoviesFragment.newInstance(position);
                    topratedMoviesFragment.setPool(pool);
                }
                return topratedMoviesFragment;
            case 2:
                if (upcomingMoviesFragment == null){
                    Timber.d("upcoming fragment is null");

                    upcomingMoviesFragment = BrowseMoviesFragment.newInstance(position);
                    upcomingMoviesFragment.setPool(pool);
                }
                return upcomingMoviesFragment;
            case 3:
                if (nearbyMoviesFragment == null){
                    Timber.d("nearby fragment is null");

                    nearbyMoviesFragment = NearbyMoviesFragment.newInstance();
                    nearbyMoviesFragment.setPool(pool);
                }
                return nearbyMoviesFragment;
            default:
                if (popularMoviesFragment == null){
                    popularMoviesFragment = BrowseMoviesFragment.newInstance(position);
                    popularMoviesFragment.setPool(pool);
                }
                return popularMoviesFragment;
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

package com.rashwan.reactive_popular_movies.feature.movieDetails;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieCast.MovieCastFragment;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieInfo.MovieInfoFragment;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieReviews.MovieReviewsFragment;

/**
 * Created by rashwan on 4/20/17.
 */

public class MovieDetailsPagerAdapter extends FragmentPagerAdapter {
    private static final int PAGE_COUNT = 3;
    private final String tabTitles[] = new String[] { "Info", "Cast","Reviews" };
    private Movie movie;

    public MovieDetailsPagerAdapter(FragmentManager fm, Movie movie) {
        super(fm);
        this.movie = movie;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return MovieInfoFragment.newInstance(movie);
            case 1:
                return MovieCastFragment.newInstance(movie.id());
            case 2:
                return MovieReviewsFragment.newInstance(movie.id());

            default:
                return MovieInfoFragment.newInstance(movie);
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}

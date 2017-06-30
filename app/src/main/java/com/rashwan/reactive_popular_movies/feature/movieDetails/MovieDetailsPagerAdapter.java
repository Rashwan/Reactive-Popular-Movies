package com.rashwan.reactive_popular_movies.feature.movieDetails;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieCast.MovieCastFragment;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieInfo.MovieInfoFragment;
import com.rashwan.reactive_popular_movies.feature.movieDetails.movieReviews.MovieReviewsFragment;

import timber.log.Timber;

/**
 * Created by rashwan on 4/20/17.
 */

public class MovieDetailsPagerAdapter extends FragmentPagerAdapter {
    private static final int PAGE_COUNT = 3;
    private final String tabTitles[] = new String[] { "Info", "Cast","Reviews" };
    private Movie movie;
    private MovieInfoFragment movieInfoFragment;
    private MovieCastFragment movieCastFragment;
    private MovieReviewsFragment movieReviewsFragment;

    public MovieDetailsPagerAdapter(FragmentManager fm, Movie movie) {
        super(fm);
        this.movie = movie;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                if (movieInfoFragment == null){
                    Timber.d("movie info fragment is null");
                    movieInfoFragment =  MovieInfoFragment.newInstance
                            (movie.id(),movie.tmdbRating(),movie.overview());
                }
                return movieInfoFragment;
            case 1:
                if (movieCastFragment == null){
                    Timber.d("movie cast fragment is null");

                    movieCastFragment = MovieCastFragment.newInstance(movie.id());
                }
                return movieCastFragment;
            case 2:
                if (movieReviewsFragment == null){
                    Timber.d("movie reviews fragment is null");

                    movieReviewsFragment = MovieReviewsFragment.newInstance(movie.id());
                }
                return movieReviewsFragment;

            default:
                if (movieInfoFragment == null){
                    movieInfoFragment =  MovieInfoFragment.newInstance
                            (movie.id(),movie.tmdbRating(),movie.overview());
                }
                return movieInfoFragment;

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

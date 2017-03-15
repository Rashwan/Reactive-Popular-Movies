package com.rashwan.reactive_popular_movies.feature.browseMovies;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.common.utilities.DelegateToActivity;
import com.rashwan.reactive_popular_movies.common.utilities.Utilities;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.data.model.Trailer;
import com.rashwan.reactive_popular_movies.feature.movieDetails.MovieDetailsActivity;
import com.rashwan.reactive_popular_movies.feature.movieDetails.MovieDetailsFragment;
import com.rashwan.reactive_popular_movies.feature.nearbyMovies.NearbyMoviesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;
import timber.log.Timber;

public class BrowseMoviesActivity extends AppCompatActivity implements DelegateToActivity {
    private static final String TAG_MOVIE_DETAILS_FRAGMENT = "TAG_MOVIE_DETAILS_FRAGMENT";
    private static final String BUNDLE_MOVIE = "BUNDLE_MOVIE";
    private static final String BUNDLE_MOVIE_ID = "BUNDLE_MOVIE_ID";
    private Unbinder unbinder;
    private android.support.v4.app.FragmentManager fragmentManager;
    private Movie movie;
    private Boolean isTwoPane;
    private Long movieId = -1L;
    private MovieDetailsFragment movieDetailsFragment;
    private String transitionName = "";
    private Transition fade;
    BrowseMoviesPagerAdapter browseMoviesPagerAdapter;

    @BindView(R.id.browse_toolbar) Toolbar browseToolbar;
    @BindView(R.id.slidingTabs) TabLayout tabLayout;
    @BindView(R.id.viewpager) ViewPager viewPager;
    @Nullable @BindView(R.id.details_toolbar) Toolbar detailsToolbar;
    @Nullable @BindView(R.id.details_container) FrameLayout detailsContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_movies);
        unbinder = ButterKnife.bind(this);
        browseMoviesPagerAdapter = new BrowseMoviesPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(browseMoviesPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        //Delay shared element transition until the recyclerView is drawn
        supportPostponeEnterTransition();

        //Set browse toolbar as the primary toolbar which would receive all default menu callbacks.
        setSupportActionBar(browseToolbar);

        isTwoPane = determineTwoPane();
        fragmentManager = getSupportFragmentManager();

        movieDetailsFragment = (MovieDetailsFragment) fragmentManager
                .findFragmentByTag(TAG_MOVIE_DETAILS_FRAGMENT);

        //If we are in two pane mode inflate the details menu as the secondary toolbar.
        if(isTwoPane) inflateDetailsMenu();

        //If it's the first time this activity is created add the browse movies fragment.
        if (savedInstanceState != null){
            movie = savedInstanceState.getParcelable(BUNDLE_MOVIE);
            movieId = savedInstanceState.getLong(BUNDLE_MOVIE_ID);
            //If the activity is recreated from a config change,we are in two pane mode
            // and the user has selected a movie, make the details menu visible.
            if (isTwoPane && movie != null){
                detailsToolbar.setVisibility(View.VISIBLE);
                detailsToolbar.setTitle(movie.title());
                detailsToolbar.setTitleMarginStart(16);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fade = TransitionInflater.from(this).inflateTransition(android.R.transition.fade).setDuration(400L);
        }
    }

    /**
     * Handle when a user selects a movie based on whether we are in two pane mode or not
     * @param movie The movie the user selected.
     * @param view The image view of the selected movie to be used as a shared element.
     */
    @Override
    public void delegateMovieClicked(Movie movie, ImageView view) {
        //If we are in two pane mode and this movie is not already selected show the movie details menu and fragment.
        if (isTwoPane){
            if (movieId != movie.id()) {
                movieId = movie.id();
                this.movie = movie;
                detailsToolbar.setVisibility(View.VISIBLE);
                detailsToolbar.setTitle(movie.title());
                detailsToolbar.setTitleMarginStart(16);

                movieDetailsFragment = MovieDetailsFragment.newInstance(movie,transitionName);
                movieDetailsFragment.setEnterTransition(fade);
                fragmentManager.beginTransaction()
                        .replace(R.id.details_container, movieDetailsFragment, TAG_MOVIE_DETAILS_FRAGMENT).commit();

            }
        }else {
            Intent intent;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                transitionName = view.getTransitionName();
                intent = MovieDetailsActivity.getDetailsIntent(this,movie,transitionName);
                ActivityOptions activityOptions = ActivityOptions
                        .makeSceneTransitionAnimation(this,view,view.getTransitionName());
                startActivity(intent,activityOptions.toBundle());

            }else {
                intent = MovieDetailsActivity.getDetailsIntent(this,movie,transitionName);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (isTwoPane) {
            outState.putParcelable(BUNDLE_MOVIE, movie);
            outState.putLong(BUNDLE_MOVIE_ID,movieId);
        }
    }

    private Boolean determineTwoPane(){
        return detailsContainer != null;
    }

    /**
     * Inflate movie details menu as a secondary toolbar with custom onClick listener, the menu starts
     * hidden until a movie is chosen from the browse movies list.
     */
    private void inflateDetailsMenu() {
        detailsToolbar.inflateMenu(R.menu.activity_movie_details);
        detailsToolbar.setVisibility(View.INVISIBLE);
        detailsToolbar.setOnMenuItemClickListener(this::onDetailsMenuClicked);
    }


    /**
     * Handle onClick for movie details menu.
     * @param item menu item that was clicked
     * @return true if we can handle an item with this id, false otherwise.
     */
    private boolean onDetailsMenuClicked(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_share:
                Observable<Trailer> shareTrailerObservable = movieDetailsFragment.getShareTrailerObservable();
                //If a movie is selected create a share intent with its title and trailer.
                if (movie != null) {
                    //This needs improvement maybe use rx subjects
                    shareTrailerObservable.subscribe(trailer ->
                            Utilities.createShareIntent(this,movie.title(),trailer.getFullYoutubeUri().toString())
                            ,throwable -> Timber.d(throwable,"error in share trailer")
                            ,() -> Timber.d("finished getting share trailer"));
                    Timber.d("Share Clicked on %s", movie.title());
                }
                return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
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

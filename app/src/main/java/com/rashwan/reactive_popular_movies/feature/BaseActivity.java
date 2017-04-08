package com.rashwan.reactive_popular_movies.feature;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
import com.rashwan.reactive_popular_movies.feature.discoverMovies.BrowseMoviesActivity;
import com.rashwan.reactive_popular_movies.feature.favoriteMovies.FavoriteMoviesActivity;
import com.rashwan.reactive_popular_movies.feature.movieDetails.MovieDetailsActivity;
import com.rashwan.reactive_popular_movies.feature.movieDetails.MovieDetailsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import timber.log.Timber;

/**
 * Created by rashwan on 4/7/17.
 */

public class BaseActivity extends AppCompatActivity implements DelegateToActivity{
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view) NavigationView navigationView;
    @BindView(R.id.browse_toolbar) Toolbar toolbar;
    @Nullable @BindView(R.id.details_container) FrameLayout detailsContainer;
    @Nullable @BindView(R.id.details_toolbar) Toolbar detailsToolbar;

    private static final String TAG_MOVIE_DETAILS_FRAGMENT = "TAG_MOVIE_DETAILS_FRAGMENT";
    private static final String BUNDLE_MOVIE = "BUNDLE_MOVIE";
    private static final String BUNDLE_MOVIE_ID = "BUNDLE_MOVIE_ID";
    private ActionBarDrawerToggle drawerToggle;
    private boolean isTwoPane;
    private FragmentManager fragmentManager;
    private MovieDetailsFragment movieDetailsFragment;
    private Long movieId = -1L;
    private Movie movie;
    private Transition fade;
    private String transitionName = "";




    protected void onCreateDrawer() {
        ButterKnife.bind(this);
        setupNavDrawer();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        onCreateDrawer();
    }
    
    protected void onCreateBaseActivity(Bundle savedInstanceState){

        //Delay shared element transition until the recyclerView is drawn
        supportPostponeEnterTransition();

        //Set browse toolbar as the primary toolbar which would receive all default menu callbacks.
        setSupportActionBar(toolbar);

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

    private void setupNavDrawer(){
        drawerToggle = new ActionBarDrawerToggle
                (this,drawerLayout,toolbar,R.string.nav_open_drawer,R.string.nav_close_drawer);
        drawerLayout.addDrawerListener(drawerToggle);
        navigationView.setNavigationItemSelectedListener(item -> {
            selectDrawerItem(item);
            return true;
        });
    }

    private void selectDrawerItem(MenuItem item) {
        Intent intent;

        switch (item.getItemId()){
            case R.id.nav_discover:
                intent = new Intent(this,BrowseMoviesActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.nav_favorites:
                intent = new Intent(this, FavoriteMoviesActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        item.setChecked(true);
        drawerLayout.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
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

    private Boolean determineTwoPane(){
        return detailsContainer != null;
    }

    private void inflateDetailsMenu() {
        detailsToolbar.inflateMenu(R.menu.activity_movie_details);
        detailsToolbar.setVisibility(View.INVISIBLE);
        detailsToolbar.setOnMenuItemClickListener(this::onDetailsMenuClicked);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (isTwoPane) {
            outState.putParcelable(BUNDLE_MOVIE, movie);
            outState.putLong(BUNDLE_MOVIE_ID,movieId);
        }
    }
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }
}

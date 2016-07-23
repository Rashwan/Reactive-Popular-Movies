package com.rashwan.reactive_popular_movies.feature.browseMovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.common.utilities.Utilities;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.data.model.Trailer;
import com.rashwan.reactive_popular_movies.feature.movieDetails.MovieDetailsActivity;
import com.rashwan.reactive_popular_movies.feature.movieDetails.MovieDetailsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;
import timber.log.Timber;

public class BrowseMoviesActivity extends AppCompatActivity implements BrowseMoviesFragment.DelegateToActivity{
    @BindView(R.id.browse_toolbar)
    Toolbar browseToolbar;
    @Nullable @BindView(R.id.details_toolbar)
    Toolbar detailsToolbar;
    @Nullable @BindView(R.id.details_container)
    FrameLayout detailsContainer;
    private Unbinder unbinder;
    private static final String BROWSE_MOVIES_FRAGMENT_TAG = "browse_movies_fragment_tag";
    private static final String MOVIE_DETAILS_FRAGMENT_TAG = "movie_details_fragment_tag";
    private static final String MOVIE_PARCABLE_KEY = "movie_key";
    private static final String MOVIE_ID_PARCABLE_KEY = "movie_id_key";
    private android.support.v4.app.FragmentManager fragmentManager;
    private Movie movie;
    private Boolean isTwoPane;
    private Long movieId = -1L;
    private MovieDetailsFragment movieDetailsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_movies);
        unbinder = ButterKnife.bind(this);

        //Set browse toolbar as the primary toolbar which would receive all default menu callbacks.
        setSupportActionBar(browseToolbar);

        isTwoPane = determineTwoPane();
        fragmentManager = getSupportFragmentManager();

        BrowseMoviesFragment browseMoviesFragment = (BrowseMoviesFragment) fragmentManager
                .findFragmentByTag(BROWSE_MOVIES_FRAGMENT_TAG);
        movieDetailsFragment = (MovieDetailsFragment) fragmentManager
                .findFragmentByTag(MOVIE_DETAILS_FRAGMENT_TAG);

        //If we are in two pane mode inflate the details menu as the secondary toolbar.
        if(isTwoPane) inflateDetailsMenu();

        //If it's the first time this activity is created add the browse movies fragment.
        if (savedInstanceState == null){
            if (browseMoviesFragment == null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.browse_container, new BrowseMoviesFragment(), BROWSE_MOVIES_FRAGMENT_TAG)
                        .commit();
            }
        }else {
            movie = savedInstanceState.getParcelable(MOVIE_PARCABLE_KEY);
            movieId = savedInstanceState.getLong(MOVIE_ID_PARCABLE_KEY);
            //If the activity is recreated from a config change,we are in two pane mode
            // and the user has selected a movie, make the details menu visible.
            if (isTwoPane && movie != null){
                detailsToolbar.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * Handle when a user selects a movie based on whether we are in two pane mode or not
     * @param movie The movie the user selected.
     */
    @Override
    public void delegateMovieClicked(Movie movie) {
        //If we are in two pane mode and this movie is not already selected show the movie details menu and fragment.
        if (isTwoPane){
            if (movieId != movie.id()) {
                movieId = movie.id();
                this.movie = movie;
                detailsToolbar.setVisibility(View.VISIBLE);
                movieDetailsFragment = MovieDetailsFragment.newInstance(movie);
                fragmentManager.beginTransaction()
                        .replace(R.id.details_container, movieDetailsFragment,MOVIE_DETAILS_FRAGMENT_TAG).commit();

            }
        }else {
            Intent intent = MovieDetailsActivity.getDetailsIntent(this,movie);
            startActivity(intent);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (isTwoPane) {
            outState.putParcelable(MOVIE_PARCABLE_KEY, movie);
            outState.putLong(MOVIE_ID_PARCABLE_KEY,movieId);
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
        detailsToolbar.inflateMenu(R.menu.movie_details_menu);
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

}

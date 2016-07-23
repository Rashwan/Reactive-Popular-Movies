package com.rashwan.reactive_popular_movies.feature.browseMovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.rashwan.reactive_popular_movies.R;
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
    private android.support.v4.app.FragmentManager fragmentManager;
    private Movie movie;
    private Boolean isTwoPane;
    private Long movieId = -1L;
    MovieDetailsFragment movieDetailsFragment;
    private Observable<Trailer> shareTrailerObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_movies);
        unbinder = ButterKnife.bind(this);
        setSupportActionBar(browseToolbar);
        isTwoPane = determineTwoPane();
        fragmentManager = getSupportFragmentManager();

        BrowseMoviesFragment browseMoviesFragment = (BrowseMoviesFragment) fragmentManager
                .findFragmentByTag(BROWSE_MOVIES_FRAGMENT_TAG);
        movieDetailsFragment = (MovieDetailsFragment) fragmentManager
                .findFragmentByTag(MOVIE_DETAILS_FRAGMENT_TAG);

        if(isTwoPane) inflateDetailsMenu();

        if (savedInstanceState == null){
            if (browseMoviesFragment == null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.browse_container, new BrowseMoviesFragment(), BROWSE_MOVIES_FRAGMENT_TAG)
                        .commit();
            }
        }else {
            movie = savedInstanceState.getParcelable("Movie");
            movieId = savedInstanceState.getLong("MovieId");
            if (isTwoPane && movie != null){
                detailsToolbar.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void delegateMovieClicked(Movie movie) {
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
            outState.putParcelable("Movie", movie);
            outState.putLong("MovieId",movieId);
        }
    }

    private Boolean determineTwoPane(){
        return detailsContainer != null;
    }


    private void inflateDetailsMenu() {
        detailsToolbar.inflateMenu(R.menu.movie_details_menu);
        detailsToolbar.setVisibility(View.INVISIBLE);
        detailsToolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.menu_share:
                    shareTrailerObservable = movieDetailsFragment.getShareTrailerObservable();
                    if (movie != null) {
                        shareTrailerObservable.subscribe(trailer ->
                            createShareIntent(movie.title(),trailer.getFullYoutubeUri().toString())
                        ,throwable -> Timber.d(throwable,"error in share trailer")
                        ,() -> Timber.d("finished getting share trailer"));
                        Timber.d("Share Clicked on %s", movie.title());
                    }
                    return true;
            }
            return false;
        });
    }
    private void createShareIntent(String title, String trailerUrl) {
        ShareCompat.IntentBuilder builder = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText("check out the trailer for the movie " + title  + ", at : " + trailerUrl);
        startActivity(Intent.createChooser(builder.getIntent(), "Share Movie!"));
    }

}

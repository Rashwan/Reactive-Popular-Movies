package com.rashwan.reactive_popular_movies.feature.movieDetails;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.rashwan.reactive_popular_movies.PopularMoviesApplication;
import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.common.utilities.DelegateToActivity;
import com.rashwan.reactive_popular_movies.common.utilities.PaletteTransformation;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.data.model.MovieDetails;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rashwan on 7/3/16.
 */

public class MovieDetailsActivity extends AppCompatActivity implements DelegateToActivity
        ,MovieDetailsView,ShowDetailsInActivity{

    private static final String EXTRA_MOVIE = "com.rashwan.reactive_popular_movies.feature.movieDetails.EXTRA_MOVIE";
    private static final String EXTRA_SHARED_ELEMENT_NAME = "com.rashwan.reactive_popular_movies.feature.movieDetails.EXTRA_SHARED_ELEMENT_NAME";
    private static final String TAG_MOVIE_DETAILS_FRAGMENT = "TAG_MOVIE_DETAILS_FRAGMENT";
    @BindView(R.id.details_coordinator_layout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.details_sliding_tabs) TabLayout detailsTabLayout;
    @BindView(R.id.details_view_pager) ViewPager detailsViewPager;
    @BindView(R.id.image_backdrop) ImageView blurPoster;
    @BindView(R.id.image_poster) ImageView posterImage;
    @BindView(R.id.text_release) TextView release;
    @BindView(R.id.text_movie_title) TextView textMovieTitle;
    @BindView(R.id.toolbar_movie_title) TextView toolbarMovieTitle;
    @BindView(R.id.collapsing_toolbar_layout) CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.toggle_watchlist) ToggleButton toggleWatchlist;
    @BindView(R.id.button_play_main_trailer) ImageButton buttonPlayTrailer;
    @BindView(R.id.appbar) AppBarLayout appBarLayout;
    @BindView(R.id.text_runtime) TextView textRuntime;
    @BindView(R.id.text_movie_genres) TextView textGenres;
    @BindView(R.id.image_mpaa_rating) ImageView imageMpaaRating;
    @BindView(R.id.appbar_constraint_layout) ConstraintLayout appbarConstraintLayout;
    @BindView(R.id.fab_favorite) FloatingActionButton fab;
    @Nullable @BindView(R.id.toolbar_details) Toolbar toolbar;
    @BindColor(android.R.color.black) int blackColor;
    private String sharedElementName;
    private Movie movie;
    @Inject MovieDetailsPresenter presenter;
    private boolean isFavorite = false;
    private ConstantState fullHeartConstantState;
    private ConstantState emptyHeartConstantState;


    public static Intent getDetailsIntent(Context context, Movie movie, String sharedElementName){
        Intent intent = new Intent(context,MovieDetailsActivity.class);
        intent.putExtra(EXTRA_MOVIE,movie);
        intent.putExtra(EXTRA_SHARED_ELEMENT_NAME,sharedElementName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((PopularMoviesApplication)getApplication()).createMovieDetailsComponent().inject(this);
        Intent intent = getIntent();
        movie = intent.getParcelableExtra(EXTRA_MOVIE);
        sharedElementName = intent.getStringExtra(EXTRA_SHARED_ELEMENT_NAME);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        presenter.attachView(this);
        setupViews();

//        MovieInfoFragment movieDetailsFragment = (MovieInfoFragment)
//                fragmentManager.findFragmentByTag(TAG_MOVIE_DETAILS_FRAGMENT);
//        if (savedInstanceState == null && movieDetailsFragment == null){
//            movieDetailsFragment = MovieInfoFragment.newInstance(movie,sharedElementName);
//            fragmentManager.beginTransaction()
//                    .replace(R.id.movie_details_container,movieDetailsFragment, TAG_MOVIE_DETAILS_FRAGMENT)
//                    .commit();
//        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void enterReveal() {
        blurPoster.setVisibility(View.VISIBLE);
        final int finalRaidus =  Math.max(blurPoster.getWidth(), blurPoster.getHeight()) / 2;
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(blurPoster
                , blurPoster.getWidth()/2, blurPoster.getHeight()/2
                , 0, finalRaidus);
        circularReveal.start();
    }

    @Override
    public void showFavoriteMovie() {
        if (!isFavorite) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                AnimatedVectorDrawable emptyHeart = (AnimatedVectorDrawable)
                        emptyHeartConstantState.newDrawable();
                fab.setImageDrawable(emptyHeart);
                emptyHeart.start();

            } else {
                fab.setImageResource(R.drawable.heart_fill);
            }
            isFavorite = true;
        }
    }

    @Override
    public void showNonFavoriteMovie() {
        if (isFavorite) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                AnimatedVectorDrawable fullHeart = (AnimatedVectorDrawable)
                        fullHeartConstantState.newDrawable();
                fab.setImageDrawable(fullHeart);
                fullHeart.start();

            } else {
                fab.setImageResource(R.drawable.fab_heart_empty);
            }
            isFavorite = false;
        }
    }
    @Override
    public void showWatchlistMovie() {
        toggleWatchlist.setChecked(true);
    }

    @Override
    public void showNormalMovie() {
        toggleWatchlist.setChecked(false);
    }

    @Override
    public void showPlayTrailerButton() {
        buttonPlayTrailer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRuntime(String runtime) {
        textRuntime.setText(runtime);
    }

    @Override
    public void showOmdbDetails(MovieDetails movieDetails) {
        textGenres.setText(movieDetails.genre());
        imageMpaaRating.setImageDrawable(ContextCompat.getDrawable(this
                ,chooseRatingImage(movieDetails.rated())));
    }

    @Override
    public void showReviewMessage(String message) {
        Snackbar.make(coordinatorLayout,message,Snackbar.LENGTH_SHORT).show();
    }

    private @DrawableRes
    int chooseRatingImage(String rating){
        switch (rating){
            case "PG-13":
                return R.drawable.ic_rated_pg_13;
            case "PG":
                return R.drawable.ic_rated_pg;
            case "R":
                return R.drawable.ic_rated_r;
            case "G":
                return R.drawable.ic_rated_g;
            case "NC-17":
                return R.drawable.ic_rated_nc_17;
            default:
                return R.drawable.ic_not_applicable;
        }
    }

    @OnClick(R.id.fab_favorite)
    public void onFabClicked(){
        if (isFavorite){
            presenter.removeMovieFromFavorites(movie.id());
        }else {
            presenter.addMovieToFavorites(movie);
        }
    }

    @OnClick(R.id.toggle_watchlist)
    public void onWatchlistClicked(){
        if (!toggleWatchlist.isChecked()){
            presenter.removeMovieFromWatchlist(movie.id());
        }else {
            presenter.addMovieToWatchlist(movie);
        }
    }

    private void setupViews(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fullHeartConstantState = ContextCompat.getDrawable(this,R.drawable.fab_heart_fill).getConstantState();
            emptyHeartConstantState = ContextCompat.getDrawable(this,R.drawable.fab_heart_empty).getConstantState();
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.transparent_black));
            posterImage.setTransitionName(sharedElementName);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenter.isMovieFavorite(movie.id());
        presenter.isMovieInWatchlist(movie.id());


        MovieDetailsPagerAdapter detailsPagerAdapter = new MovieDetailsPagerAdapter(
                getSupportFragmentManager(),movie);
        detailsViewPager.setAdapter(detailsPagerAdapter);
        detailsTabLayout.setupWithViewPager(detailsViewPager);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarMovieTitle.setAlpha(0);
        toolbarMovieTitle.setText(movie.title());
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
            if(collapsingToolbar.getHeight() + verticalOffset < 2 * collapsingToolbar.getScrimVisibleHeightTrigger()){
                toolbarMovieTitle.animate().alpha(1).setDuration(500);
            }else {
                toolbarMovieTitle.animate().alpha(0).setDuration(250);
            }
        });
        populateMovieDetilas();
    }

    private void populateMovieDetilas(){
        textMovieTitle.setText(movie.title());
        release.setText(movie.getFormattedReleaseDate(movie.release_date()));
        Picasso.with(this)
                .load(movie.getFullPosterPath(Movie.QUALITY_MEDIUM))
                .transform(new PaletteTransformation())
                .into(posterImage, new PaletteTransformation.Callback(posterImage) {
                    @Override
                    public void onPalette(Palette palette) {
                        if (palette != null) {
                            Palette.Swatch darkVibrantSwatch = palette.getDarkVibrantSwatch();
                            if (collapsingToolbar != null && darkVibrantSwatch != null) {
                                int darkVibrantColor = darkVibrantSwatch.getRgb();
                                collapsingToolbar.setContentScrimColor(darkVibrantColor);
                                collapsingToolbar.setStatusBarScrimColor(darkVibrantColor);
                                GradientDrawable gradient = new GradientDrawable
                                        (GradientDrawable.Orientation.TL_BR,new int[]{
                                                blackColor,
                                                darkVibrantColor
                                        });
                                gradient.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                                appbarConstraintLayout.setBackground(gradient);
                            }
                        }
                    }});

        Picasso.with(this).load(movie.getFullBackdropPath(Movie.QUALITY_MEDIUM)).fit().centerCrop()
                .transform(new PaletteTransformation())
                .into(blurPoster, new Callback() {
                    @Override
                    public void onSuccess() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            enterReveal();
                        }else {
                            blurPoster.setVisibility(View.VISIBLE);
                        }
                    }
                    @Override
                    public void onError() {
                    }
                });
    }


    @Override
    public boolean onNavigateUp() {
        supportFinishAfterTransition();
        return true;
    }

    @Override
    public void delegateMovieClicked(Movie movie, ImageView sharedView) {
        //Dosen't handle master/detail views
        Intent intent;
        String transitionName = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            transitionName = sharedView.getTransitionName();
            intent = getDetailsIntent(this,movie,transitionName);
            ActivityOptions activityOptions = ActivityOptions
                    .makeSceneTransitionAnimation(this,sharedView,sharedView.getTransitionName());
            startActivity(intent,activityOptions.toBundle());

        }else {
            intent = MovieDetailsActivity.getDetailsIntent(this,movie,transitionName);
            startActivity(intent);
        }
    }


}

package com.rashwan.reactive_popular_movies.feature.movieDetails;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.rashwan.reactive_popular_movies.PopularMoviesApplication;
import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.common.utilities.DividerItemDecoration;
import com.rashwan.reactive_popular_movies.common.utilities.PaletteTransformation;
import com.rashwan.reactive_popular_movies.common.utilities.Utilities;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.data.model.MovieDetails;
import com.rashwan.reactive_popular_movies.data.model.Review;
import com.rashwan.reactive_popular_movies.data.model.Trailer;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.BrowseMoviesActivity;
import com.rashwan.reactive_popular_movies.feature.favoriteMovies.FavoriteMoviesActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Observable;
import timber.log.Timber;

/**
 * Created by rashwan on 7/3/16.
 */

public class MovieDetailsFragment extends android.support.v4.app.Fragment implements MovieDetailsView,MovieTrailersAdapter.ClickListener {

    private static final String ARGUMENT_MOVIE = "ARGUMENT_MOVIE";
    private static final String ARGUMENT_SHARED_ELEMENT_NAME = "ARGUMENT_SHARED_ELEMENT_NAME";
    private static final ButterKnife.Action SHOW = (view, index) -> view.setVisibility(View.VISIBLE);
    private static final ButterKnife.Action HIDE = (view, index) -> view.setVisibility(View.GONE);
    private Movie movie;
    private boolean isFavorite = false;
    private Unbinder unbinder;
    private Drawable.ConstantState emptyHeartConstantState;
    private Drawable.ConstantState fullHeartConstantState;
    private boolean isTwoPane = false;
    private Observable<Trailer> shareTrailerObservable = Observable.empty();
    private String sharedElementName;
    @BindViews({R.id.text_no_internet,R.id.button_refresh}) List<View> offlineViews;
    @BindView(R.id.rv_trailers) RecyclerView rvTrailer;
    @BindView(R.id.rv_reviews) RecyclerView rvReviews;
    @BindView(R.id.image_backdrop) ImageView blurPoster;
    @BindView(R.id.image_poster) ImageView posterImage;
    @BindView(R.id.text_release) TextView release;
    @BindView(R.id.text_movie_title) TextView textMovieTitle;
    @BindView(R.id.toolbar_movie_title) TextView toolbarMovieTitle;
    @BindView(R.id.text_description) TextView description;
    @BindView(R.id.collapsing_toolbar_layout) CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.toggle_watchlist) ToggleButton toggleWatchlist;
    @BindView(R.id.button_play_main_trailer) ImageButton buttonPlayTrailer;
    @BindView(R.id.appbar) AppBarLayout appBarLayout;
    @BindView(R.id.text_runtime) TextView textRuntime;
    @BindView(R.id.text_movie_genres) TextView textGenres;
    @BindView(R.id.image_mpaa_rating) ImageView imageMpaaRating;
    @BindView(R.id.appbar_constraint_layout) ConstraintLayout appbarConstraintLayout;
    @BindColor(R.color.colorPrimaryDark) int primaryDarkColor;
    @Nullable @BindView(R.id.toolbar_details) Toolbar toolbar;
    @BindView(R.id.fab_favorite) FloatingActionButton fab;
    @BindViews({R.id.rv_trailers,R.id.text_trailers_title,R.id.divider_description_trailers})
    List<View> trailersViews;
    @BindViews({R.id.rv_reviews,R.id.text_review_title,R.id.divider_trailers_reviews})
    List<View> reviewsViews;
    @Inject MovieTrailersAdapter trailersAdapter;
    @Inject MovieReviewAdapter reviewsAdapter;
    @Inject MovieDetailsPresenter presenter;

    public static MovieDetailsFragment newInstance(Movie movie, String sharedElementName) {
        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT_MOVIE, movie);
        bundle.putString(ARGUMENT_SHARED_ELEMENT_NAME,sharedElementName);
        movieDetailsFragment.setArguments(bundle);
        return movieDetailsFragment;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BrowseMoviesActivity || context instanceof FavoriteMoviesActivity){
            isTwoPane = true;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ((PopularMoviesApplication)getActivity().getApplication()).createMovieDetailsComponent()
                .inject(this);
        movie = getArguments().getParcelable(ARGUMENT_MOVIE);
        sharedElementName = getArguments().getString(ARGUMENT_SHARED_ELEMENT_NAME);
        Timber.d(movie.toString());

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        if(!isTwoPane){
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setHasOptionsMenu(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            posterImage.setTransitionName(sharedElementName);
        }
        setupLayout();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        ((PopularMoviesApplication)getActivity().getApplication()).releaseMovieDetailsComponent();
    }

    @Override
    public void showTrailers(List<Trailer> trailers) {
        ButterKnife.apply(trailersViews,SHOW);
        shareTrailerObservable = (Observable.just(trailers.get(0)));
        trailersAdapter.setTrailers(trailers);
        trailersAdapter.notifyDataSetChanged();
    }

    @Override
    public void showReviews(List<Review> reviews) {
        ButterKnife.apply(reviewsViews,SHOW);
        reviewsAdapter.setReviews(reviews);
        reviewsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showOfflineLayout() {
        ButterKnife.apply(offlineViews,SHOW);
    }

    @Override
    public void hideOfflineLayout() {
        ButterKnife.apply(offlineViews,HIDE);
    }

    @Override
    public void showFavoriteMovie() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            AnimatedVectorDrawable emptyHeart = (AnimatedVectorDrawable) emptyHeartConstantState.newDrawable();
            fab.setImageDrawable(emptyHeart);
            emptyHeart.start();

        }else {
            fab.setImageResource(R.drawable.heart_fill);
        }
        isFavorite = true;
    }

    @Override
    public void showNonFavoriteMovie() {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                AnimatedVectorDrawable fullHeart = (AnimatedVectorDrawable) fullHeartConstantState.newDrawable();
                fab.setImageDrawable(fullHeart);
                fullHeart.start();

            }else {
                fab.setImageResource(R.drawable.fab_heart_empty);
            }
        isFavorite = false;
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
    public void showTmdbDetails(Movie movie) {
        this.movie = movie;
        textRuntime.setText(movie.getFormattedRuntime(movie.runtime()));
    }

    @Override
    public void showOmdbDetails(MovieDetails movieDetails) {
        Timber.d("Box Office: %s ,Genre: %s", movieDetails.boxOffice(),movieDetails.genre());
        textGenres.setText(movieDetails.genre());
        imageMpaaRating.setImageDrawable(ContextCompat.getDrawable(getActivity()
                ,chooseRatingImage(movieDetails.rated())));
    }
    private @DrawableRes int chooseRatingImage(String rating){
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


    @Override
    public void onTrailerClicked(Uri trailerYoutubeUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW,trailerYoutubeUrl);
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.activity_movie_details,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getActivity().onNavigateUp();
                return true;
            case R.id.menu_share:
                if (!trailersAdapter.isEmpty()) {
                    String shareTrailerURL = trailersAdapter.getTrailer(0).getFullYoutubeUri().toString() ;
                    Utilities.createShareIntent(getActivity(),movie.title(), shareTrailerURL);
                }
                Timber.d("Share Clicked!");
                return true;
        }
        return false;
    }

    @OnClick(R.id.button_refresh)
    public void onRefreshClicked(){

        presenter.getReviews(movie.id());
        presenter.getTrailers(movie.id());
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
    @OnClick(R.id.main_trailer_image_container)
    public void onPlayTrailerClicked(){
        onTrailerClicked(presenter.getOfficialTrailerUri());
    }

    public Observable<Trailer> getShareTrailerObservable() {
        return shareTrailerObservable;
    }

    private void setupLayout() {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarMovieTitle.setAlpha(0);
        toolbarMovieTitle.setText(movie.title());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            fullHeartConstantState = ContextCompat.getDrawable(getActivity(),R.drawable.fab_heart_fill).getConstantState();
            emptyHeartConstantState = ContextCompat.getDrawable(getActivity(),R.drawable.fab_heart_empty).getConstantState();
        }
        presenter.attachView(this);
        presenter.getMovieDetails(movie.id());
        presenter.getTrailers(movie.id());
        presenter.getReviews(movie.id());
        presenter.isMovieFavorite(movie.id());
        presenter.isMovieInWatchlist(movie.id());
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
            if(collapsingToolbar.getHeight() + verticalOffset < 2 * collapsingToolbar.getScrimVisibleHeightTrigger()){
                toolbarMovieTitle.animate().alpha(1).setDuration(500);
            }else {
                toolbarMovieTitle.animate().alpha(0).setDuration(250);
            }
        });


        setupTrailerRv();

        setupReviewRv();

        populateMovieDetails();
    }

    private void populateMovieDetails() {
        description.setText(movie.overview());
        textMovieTitle.setText(movie.title());
        release.setText(movie.getFormattedReleaseDate(movie.release_date()));

        Picasso.with(getActivity())
                .load(movie.getFullPosterPath(Movie.QUALITY_MEDIUM))
                .transform(new PaletteTransformation())
                .into(posterImage, new PaletteTransformation.Callback(posterImage) {
                    @Override
                    public void onPalette(Palette palette) {
                        if (palette != null) {
                            Palette.Swatch darkVibrantSwatch = palette.getDarkVibrantSwatch();
                            if (collapsingToolbar != null && darkVibrantSwatch != null) {
                                int darkVibrantColor = darkVibrantSwatch.getRgb();
                                appbarConstraintLayout.setBackgroundColor(darkVibrantColor);
                                collapsingToolbar.setContentScrimColor(darkVibrantColor);
                                collapsingToolbar.setStatusBarScrimColor(darkVibrantColor);
                            }
                        }
                    }});


        if (!isTwoPane) {
            Picasso.with(getActivity()).load(movie.getFullBackdropPath(Movie.QUALITY_MEDIUM)).fit().centerCrop()
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
        }else {
            Picasso.with(getActivity()).load(movie.getFullBackdropPath(Movie.QUALITY_MEDIUM))
                    .fit().centerCrop().into(blurPoster);
        }



    }

    private void setupReviewRv() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity());
        rvReviews.setLayoutManager(linearLayoutManager);
        rvReviews.setHasFixedSize(true);
        rvReviews.addItemDecoration(itemDecoration);
        rvReviews.setNestedScrollingEnabled(false);
        rvReviews.setAdapter(reviewsAdapter);
    }

    private void setupTrailerRv() {
        trailersAdapter.setClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        rvTrailer.setLayoutManager(linearLayoutManager);
        rvTrailer.setHasFixedSize(true);
        rvTrailer.setNestedScrollingEnabled(false);
        rvTrailer.setAdapter(trailersAdapter);
    }

}

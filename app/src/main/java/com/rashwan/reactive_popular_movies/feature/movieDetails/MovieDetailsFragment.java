package com.rashwan.reactive_popular_movies.feature.movieDetails;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.rashwan.reactive_popular_movies.PopularMoviesApplication;
import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.common.utilities.DividerItemDecoration;
import com.rashwan.reactive_popular_movies.common.utilities.PaletteTransformation;
import com.rashwan.reactive_popular_movies.common.utilities.Utilities;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.data.model.Review;
import com.rashwan.reactive_popular_movies.data.model.Trailer;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.BrowseMoviesActivity;
import com.rashwan.reactive_popular_movies.feature.favoriteMovies.FavoriteMoviesActivity;
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
    @BindView(R.id.text_vote) TextView vote;
    @BindView(R.id.text_description) TextView description;
    @BindView(R.id.collapsing_toolbar_layout) CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.toggle_watchlist) ToggleButton toggleWatchlist;
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

    public static MovieDetailsFragment newInstance(Movie movie,String sharedElementName) {
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

        if (isFavorite) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                AnimatedVectorDrawable fullHeart = (AnimatedVectorDrawable) fullHeartConstantState.newDrawable();
                fab.setImageDrawable(fullHeart);
                fullHeart.start();

            }else {
                fab.setImageResource(R.drawable.fab_heart_empty);
            }
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
    public void onTrailerClicked(Trailer trailer) {
        Intent intent = new Intent(Intent.ACTION_VIEW,trailer.getFullYoutubeUri());
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

    public Observable<Trailer> getShareTrailerObservable() {
        return shareTrailerObservable;
    }

    private void setupLayout() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            fullHeartConstantState = ContextCompat.getDrawable(getActivity(),R.drawable.fab_heart_fill).getConstantState();
            emptyHeartConstantState = ContextCompat.getDrawable(getActivity(),R.drawable.fab_heart_empty).getConstantState();
        }
        presenter.attachView(this);
        presenter.getTrailers(movie.id());
        presenter.getReviews(movie.id());
        presenter.isMovieFavorite(movie.id());
        presenter.isMovieInWatchlist(movie.id());

        setupTrailerRv();

        setupReviewRv();

        populateMovieDetails();
    }

    private void populateMovieDetails() {
        description.setText(movie.overview());
        collapsingToolbar.setTitle(movie.title());
        vote.setText(movie.getFormattedVoteAverage(movie.vote_average()));
        release.setText(movie.getFormattedReleaseDate(movie.release_date()));
        Picasso.with(getActivity())
                .load(movie.getFullPosterPath(Movie.QUALITY_MEDIUM)).into(posterImage);


        if (!isTwoPane) {
            Picasso.with(getActivity()).load(movie.getFullBackdropPath(Movie.QUALITY_MEDIUM)).fit().centerCrop()
                    .transform(new PaletteTransformation())
                    .into(blurPoster, new PaletteTransformation.Callback(blurPoster) {
                        @Override
                        public void onPalette(Palette palette) {
                            if (palette != null) {
                                Palette.Swatch darkVibrantSwatch = palette.getDarkVibrantSwatch();
                                if (darkVibrantSwatch != null && collapsingToolbar != null) {
                                    collapsingToolbar.setContentScrimColor(primaryDarkColor);
                                }
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    if (darkVibrantSwatch != null && getActivity() != null) {
                                        Window window = getActivity().getWindow();
                                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                                        window.setStatusBarColor(darkVibrantSwatch.getRgb());
                                    }
                                }
                            }
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
        rvReviews.setAdapter(reviewsAdapter);
    }

    private void setupTrailerRv() {
        trailersAdapter.setClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvTrailer.setLayoutManager(linearLayoutManager);
        rvTrailer.setHasFixedSize(true);
        rvTrailer.setAdapter(trailersAdapter);
    }

}

package com.rashwan.reactive_popular_movies.feature.movieDetails;

import android.content.Intent;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.rashwan.reactive_popular_movies.PopularMoviesApplication;
import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.common.utilities.DividerItemDecoration;
import com.rashwan.reactive_popular_movies.common.utilities.PaletteTransformation;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.data.model.Review;
import com.rashwan.reactive_popular_movies.data.model.Trailer;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by rashwan on 7/3/16.
 */

public class MovieDetailsFragment extends android.support.v4.app.Fragment implements MovieDetailsView,MovieTrailersAdapter.ClickListener {
    public static final String BUNDLE_MOVIE = "BUNDLE_MOVIE";
    @BindViews({R.id.tv_no_internet,R.id.button_refresh})
    List<View> offlineViews;
    @BindView(R.id.rv_trailers)
    RecyclerView rvTrailer;
    @BindViews({R.id.rv_trailers,R.id.trailers_title,R.id.discription_trailers_divider})
    List<View> trailersViews;
    @BindViews({R.id.rv_reviews,R.id.review_title,R.id.trailers_reviews_divider})
    List<View> reviewsViews;
    @BindView(R.id.rv_reviews)
    RecyclerView rvReviews;
    @BindView(R.id.blur_poster)
    ImageView blurPoster;
    @BindView(R.id.poster_image)
    ImageView posterImage;
    @BindView(R.id.release)
    TextView release;
    @BindView(R.id.vote)
    TextView vote;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.toolbar_details)
    Toolbar toolbar;
    @BindView(R.id.fab_favorite)
    FloatingActionButton fab;
    @Inject MovieTrailersAdapter trailersAdapter;
    @Inject MovieReviewAdapter reviewsAdapter;
    private Movie movie;
    private Boolean isFavorite = false;
    @Inject MovieDetailsPresenter presenter;
    private Unbinder unbinder;

    public static MovieDetailsFragment newInstance(Movie movie) {
        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_MOVIE, movie);
        movieDetailsFragment.setArguments(bundle);
        return movieDetailsFragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        PopularMoviesApplication.getComponent().inject(this);
        movie = getArguments().getParcelable(BUNDLE_MOVIE);
        if (movie == null){
            throw new IllegalArgumentException("Movie Details Fragment needs a movie object");
        }
        Timber.d(movie.toString());
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        unbinder = ButterKnife.bind(this, view);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupLayout();
        fab.setSelected(false);

        return view;
    }

    private void setupLayout() {
        presenter.attachView(this);
        presenter.getTrailers(movie.id());
        presenter.getReviews(movie.id());
        presenter.isMovieFavorite(movie.id());

        setupTrailerRv();

        setupReviewRv();

        populateMovieDetails();
    }

    private void populateMovieDetails() {
        description.setText(movie.overview());
        collapsingToolbar.setTitle(movie.title());
        vote.setText(movie.getFormattedVoteAverage(movie.vote_average()));
        release.setText(movie.getFormattedReleaseDate(movie.release_date()));
        Picasso.with(getActivity()).load(movie.getFullPosterPath(Movie.QUALITY_MEDIUM)).into(posterImage);
        Picasso.with(getActivity()).load(movie.getFullBackdropPath(Movie.QUALITY_MEDIUM)).fit().centerCrop()
                .transform(new PaletteTransformation())
                .into(blurPoster, new PaletteTransformation.Callback(blurPoster) {
            @Override
            public void onPalette(Palette palette) {
                if (palette != null) {
                    Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                    Palette.Swatch darkVibrantSwatch = palette.getDarkVibrantSwatch();
                    if (vibrantSwatch != null && collapsingToolbar != null) {
                        collapsingToolbar.setContentScrimColor(vibrantSwatch.getRgb());
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (darkVibrantSwatch != null && getActivity()!= null) {
                            Window window = getActivity().getWindow();
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.setStatusBarColor(darkVibrantSwatch.getRgb());
                        }
                    }
                }
            }
        });

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

    @Override
    public void showTrailers(List<Trailer> trailers) {
        ButterKnife.apply(trailersViews,SHOW);
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
            Animatable2 animatedDrawable = (Animatable2) fab.getDrawable();
            animatedDrawable.start();
            Observable.interval(1000, TimeUnit.MILLISECONDS, Schedulers.io()).take(1)
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(
                    aLong -> {
                        AnimatedVectorDrawable drawable2 = (AnimatedVectorDrawable) ContextCompat.getDrawable(getActivity(),R.drawable.avd_heart_fill);
                        AnimatedVectorDrawable s = (AnimatedVectorDrawable) drawable2.getConstantState().newDrawable();
                        fab.setImageDrawable(s);
                    },throwable -> Timber.d("error in interval")
                    ,() -> Timber.d("Finished interval")
            );
        }else {
           fab.setImageResource(R.drawable.ic_favorite);
        }
        isFavorite = true;
    }

    @Override
    public void showNormalMovie() {

        if (isFavorite) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Animatable2 animatedDrawable = (Animatable2) fab.getDrawable();
                animatedDrawable.start();

                Observable.interval(1000, TimeUnit.MILLISECONDS, Schedulers.io()).take(1)
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        aLong -> {
                            AnimatedVectorDrawable drawable2 = (AnimatedVectorDrawable) ContextCompat.getDrawable(getActivity(), R.drawable.avd_heart_empty);
                            AnimatedVectorDrawable s = (AnimatedVectorDrawable) drawable2.getConstantState().newDrawable();
                            fab.setImageDrawable(s);
                        }, throwable -> Timber.d("error in interval")
                        , () -> Timber.d("Finished interval"));

            }else {
                fab.setImageResource(R.drawable.avd_heart_empty);
            }
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) ContextCompat.getDrawable(getActivity(),R.drawable.avd_heart_empty);
                AnimatedVectorDrawable s = (AnimatedVectorDrawable) drawable.getConstantState().newDrawable();
                fab.setImageDrawable(s);
            }else {
                fab.setImageResource(R.drawable.avd_heart_empty);
            }
        }
        isFavorite = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onTrailerClicked(Trailer trailer) {
        Intent intent = new Intent(Intent.ACTION_VIEW,trailer.getFullYoutubeUri());
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getActivity().onNavigateUp();
                return true;
        }
        return false;
    }
    private static final ButterKnife.Action SHOW = (view, index) -> view.setVisibility(View.VISIBLE);
    private static final ButterKnife.Action HIDE = (view, index) -> view.setVisibility(View.GONE);
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

}

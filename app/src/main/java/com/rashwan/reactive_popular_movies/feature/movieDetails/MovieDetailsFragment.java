package com.rashwan.reactive_popular_movies.feature.movieDetails;

import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
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
import com.rashwan.reactive_popular_movies.model.Movie;
import com.rashwan.reactive_popular_movies.model.Review;
import com.rashwan.reactive_popular_movies.model.Trailer;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by rashwan on 7/3/16.
 */

public class MovieDetailsFragment extends Fragment implements MovieDetailsView,MovieTrailersAdapter.ClickListener {
    public static final String BUNDLE_MOVIE = "BUNDLE_MOVIE";
    @BindView(R.id.rv_trailers)
    RecyclerView rvTrailer;
    @Inject MovieTrailersAdapter trailersAdapter;
    @BindView(R.id.rv_reviews)
    RecyclerView rvReviews;
    @Inject MovieReviewAdapter reviewsAdapter;
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
    private Movie movie;
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

        return view;
    }

    private void setupLayout() {
        presenter.attachView(this);
        presenter.getTrailers(movie.id());
        presenter.getReviews(movie.id());

        setupTrailerRv();

        setupReviewRv();

        populateMovieDetails();
    }

    private void populateMovieDetails() {
        description.setText(movie.overview());
        collapsingToolbar.setTitle(movie.title());
        vote.setText(movie.getFormattedVoteAverage(movie.voteAverage()));
        release.setText(movie.getFormattedReleaseDate(movie.releaseDate()));
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
                        if (darkVibrantSwatch != null) {
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
        trailersAdapter.setTrailers(trailers);
        trailersAdapter.notifyDataSetChanged();
    }

    @Override
    public void showReviews(List<Review> reviews) {
        reviewsAdapter.setReviews(reviews);
        reviewsAdapter.notifyDataSetChanged();
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

}

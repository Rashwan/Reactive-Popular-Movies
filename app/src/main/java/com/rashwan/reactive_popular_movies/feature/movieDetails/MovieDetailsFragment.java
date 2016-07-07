package com.rashwan.reactive_popular_movies.feature.movieDetails;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.model.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rashwan on 7/3/16.
 */

public class MovieDetailsFragment extends Fragment {
    public static final String BUNDLE_MOVIE = "BUNDLE_MOVIE";
    @BindView(R.id.rv_trailers)
    RecyclerView rvTrailer;
    MovieTrailersAdapter trailersAdapter;
    @BindView(R.id.rv_reviews)
    RecyclerView rvReviews;
    MovieReviewAdapter reviewsAdapter;
    @BindView(R.id.blur_poster)
    ImageView blurPoster;
    @BindView(R.id.poster_image)
    ImageView posterImage;
    @BindView(R.id.releaseTitle)
    TextView releaseTitle;
    @BindView(R.id.release)
    TextView release;
    @BindView(R.id.voteTitle)
    TextView voteTitle;
    @BindView(R.id.vote)
    TextView vote;
    @BindView(R.id.description)
    TextView description;
    Movie movie;

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
        movie = getArguments().getParcelable(BUNDLE_MOVIE);
        if (movie == null){
            throw new IllegalArgumentException("Movie Details Fragment needs a movie object");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        ButterKnife.bind(this, view);
        trailersAdapter = new MovieTrailersAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvTrailer.setLayoutManager(linearLayoutManager);
        rvTrailer.setHasFixedSize(true);
        rvTrailer.setAdapter(trailersAdapter);

        reviewsAdapter = new MovieReviewAdapter();
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());

        rvReviews.setLayoutManager(linearLayoutManager1);
        rvReviews.setHasFixedSize(true);
        rvReviews.setAdapter(reviewsAdapter);

        description.setText(movie.overview());
        Picasso.with(getActivity()).load(movie.getFullPosterPath(Movie.QUALITY_MEDIUM)).into(posterImage);

        return view;
    }
}

package com.rashwan.reactive_popular_movies.feature.movieDetails;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rashwan.reactive_popular_movies.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rashwan on 7/3/16.
 */

public class MovieDetailsFragment extends Fragment {
    @BindView(R.id.rv_trailers)
    RecyclerView rvTrailer;
    MovieTrailersAdapter trailersAdapter;
    @BindView(R.id.rv_reviews)
    RecyclerView rvReviews;
    MovieReviewAdapter reviewsAdapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details,container,false);
        ButterKnife.bind(this,view);
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
        return view;
    }
}

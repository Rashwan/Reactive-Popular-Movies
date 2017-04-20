package com.rashwan.reactive_popular_movies.feature.movieDetails.movieReviews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rashwan.reactive_popular_movies.PopularMoviesApplication;
import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.common.utilities.DividerItemDecoration;
import com.rashwan.reactive_popular_movies.data.model.Review;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by rashwan on 4/20/17.
 */

public class MovieReviewsFragment extends Fragment implements MovieReviewsView{
    private static final String ARGUMENT_MOVIE_ID = "ARGUMENT_MOVIE_ID";
    @BindView(R.id.rv_movie_reviews) RecyclerView rvReviews;
    @BindView(R.id.offline_layout) LinearLayout offlineLayout;
    @BindView(R.id.text_no_reviews) TextView textNoReviews;
    @Inject MovieReviewsAdapter reviewsAdapter;
    @Inject MovieReviewsPresenter presenter;
    private long movieId;
    private Unbinder unbinder;



    public static MovieReviewsFragment newInstance(long movieId) {
        MovieReviewsFragment movieReviewsFragment = new MovieReviewsFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(ARGUMENT_MOVIE_ID, movieId);
        movieReviewsFragment.setArguments(bundle);
        return movieReviewsFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!= null) {
            movieId = getArguments().getLong(ARGUMENT_MOVIE_ID);
        }
        ((PopularMoviesApplication)getActivity().getApplication()).createMovieReviewsComponent()
                .inject(this);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_reviews, container, false);
        unbinder = ButterKnife.bind(this, view);
        setupReviewRv();
        presenter.attachView(this);
        presenter.getReviews(movieId);
        return view;
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

    @Override
    public void showReviews(List<Review> reviews) {
        textNoReviews.setVisibility(View.GONE);
        reviewsAdapter.addReviews(reviews);
        reviewsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showOfflineLayout() {
        offlineLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideOfflineLayout() {
        offlineLayout.setVisibility(View.GONE);
    }

    @Override
    public void showNoReviewsMsg() {
        textNoReviews.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.button_refresh)
    public void onRefreshClicked(){
        presenter.getReviews(movieId);
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
        ((PopularMoviesApplication)getActivity().getApplication()).releaseMovieReviewsComponent();
    }

}

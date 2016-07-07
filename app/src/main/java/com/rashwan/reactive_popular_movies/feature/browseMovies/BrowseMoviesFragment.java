package com.rashwan.reactive_popular_movies.feature.browseMovies;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.rashwan.reactive_popular_movies.PopularMoviesApplication;
import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.feature.movieDetails.MovieDetailsActivity;
import com.rashwan.reactive_popular_movies.model.Movie;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by rashwan on 6/25/16.
 */

public class BrowseMoviesFragment extends Fragment implements BrowseMoviesView, BrowseMoviesAdapter.ClickListener {

    @Inject BrowseMoviesPresenter presenter;
    @BindView(R.id.rv_browse_movies)
    RecyclerView rvBrowseMovies;
    @BindView(R.id.progressbar_browse_movies)
    ProgressBar pbBrowse;
    @Inject BrowseMoviesAdapter browseMoviesAdapter;
    Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        PopularMoviesApplication.getComponent().inject(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browse_movies, container, false);
        ButterKnife.bind(this, view);
        setupViews();
        presenter.attachView(this);
        presenter.getMovies();
        showProgress();
        setRetainInstance(true);
        return view;
    }

    private void setupViews(){

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 6);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 2;
            }
        });
        rvBrowseMovies.setHasFixedSize(true);
        rvBrowseMovies.setLayoutManager(gridLayoutManager);
        rvBrowseMovies.setAdapter(browseMoviesAdapter);
        browseMoviesAdapter.setClickListener(this);
    }
    @Override
    public void showMovies(List<Movie> movies) {

        browseMoviesAdapter.setMovies(movies);
        browseMoviesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        pbBrowse.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbBrowse.setVisibility(View.GONE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
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
    public void onMovieClicked(Movie movie) {
        Intent intent = MovieDetailsActivity.getDetailsIntent(getActivity(),movie);
        startActivity(intent);
    }
}

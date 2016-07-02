package com.rashwan.reactive_popular_movies.feature.browseMovies;

import android.app.Fragment;
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
import com.rashwan.reactive_popular_movies.model.Movie;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rashwan on 6/25/16.
 */

public class BrowseMoviesFragment extends Fragment implements BrowseMoviesView {

    @Inject BrowseMoviesPresenter presenter;
    @BindView(R.id.rv_browse_movies)
    RecyclerView rvBrowseMovies;
    @BindView(R.id.progressbar_browse_movies)
    ProgressBar pbBrowse;
    private BrowseMoviesAdapter browseMoviesAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ((PopularMoviesApplication)(getActivity().getApplication())).getComponent().inject(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browse_movies, container, false);
        ButterKnife.bind(this, view);
        browseMoviesAdapter = new BrowseMoviesAdapter();
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
}

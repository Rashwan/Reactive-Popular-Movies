package com.rashwan.reactive_popular_movies.feature.browseMovies;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.rashwan.reactive_popular_movies.PopularMoviesApplication;
import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.common.utilities.DisplayMetricsUtils;
import com.rashwan.reactive_popular_movies.common.utilities.EndlessRecyclerViewScrollListener;
import com.rashwan.reactive_popular_movies.feature.movieDetails.MovieDetailsActivity;
import com.rashwan.reactive_popular_movies.model.Movie;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

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
    private Unbinder unbinder;
    private int mPage = 1;
    private int moviesSortPref ;
    private int checkedMenuItemId;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        PopularMoviesApplication.getComponent().inject(this);
        setHasOptionsMenu(true);
        moviesSortPref = BrowseMoviesPresenter.SORT_POPULAR_MOVIES;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browse_movies, container, false);
        unbinder = ButterKnife.bind(this, view);
        setupViews();
        presenter.attachView(this);
        if (browseMoviesAdapter.isEmpty()){
            presenter.getMovies(moviesSortPref,1);
        }
        setRetainInstance(true);
        return view;
    }

    private void setupViews(){

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 6);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (DisplayMetricsUtils.isScreenSW(600)) {
                    return 2;
                }else {
                    return 3;
                }
            }
        });
        rvBrowseMovies.setHasFixedSize(true);
        rvBrowseMovies.setLayoutManager(gridLayoutManager);
        rvBrowseMovies.setAdapter(browseMoviesAdapter);
        rvBrowseMovies.addOnScrollListener(new EndlessRecyclerViewScrollListener(mPage,gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Timber.d("on Load more, Page: %d" ,page);
                Timber.d("on Load more, totalItemsCount: %d" ,totalItemsCount);
                mPage = page;
                presenter.getMovies(moviesSortPref,page);
            }
        });
        browseMoviesAdapter.setClickListener(this);
    }
    @Override
    public void showMovies(List<Movie> movies) {
        int currentSize = browseMoviesAdapter.getItemCount();
        browseMoviesAdapter.addMovies(movies);
        browseMoviesAdapter.notifyItemRangeInserted(currentSize,movies.size());
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
    public void clearScreen() {
        browseMoviesAdapter.clearMovies();
        browseMoviesAdapter.notifyDataSetChanged();
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.browse_movies_menu,menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (checkedMenuItemId != 0){
            menu.findItem(checkedMenuItemId).setChecked(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_popular_movies:
                if (!item.isChecked()){
                    item.setChecked(true);
                    checkedMenuItemId = item.getItemId();
                    moviesSortPref = BrowseMoviesPresenter.SORT_POPULAR_MOVIES;
                    presenter.getMovies(moviesSortPref,1);
                }
                return true;
            case R.id.menu_top_rated_movies:
                if (!item.isChecked()){
                    item.setChecked(true);
                    checkedMenuItemId = item.getItemId();
                    moviesSortPref = BrowseMoviesPresenter.SORT_TOP_RATED_MOVIES;
                    presenter.getMovies(moviesSortPref,1);
                }
                return true;
        }
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}

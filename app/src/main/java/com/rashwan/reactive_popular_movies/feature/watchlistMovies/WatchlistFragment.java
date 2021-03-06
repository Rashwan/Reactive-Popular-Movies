package com.rashwan.reactive_popular_movies.feature.watchlistMovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rashwan.reactive_popular_movies.PopularMoviesApplication;
import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.feature.BaseFragment;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.BrowseMoviesAdapter;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by rashwan on 4/9/17.
 */

public class WatchlistFragment extends BaseFragment implements WatchlistView {
    @Inject WatchlistPresenter presenter;
    @Inject BrowseMoviesAdapter adapter;
    @BindView(R.id.rv_browse_movies) RecyclerView rvBrowseMovies;
    @BindView(R.id.layout_empty_watchlist) LinearLayout layoutEmptyWatchlist;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((PopularMoviesApplication)(getActivity().getApplication())).createWatchlistComponent()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_watchlist_movies,container,false);
        super.onCreateBaseFragment(presenter,BaseFragment.SORT_WATCHLIST_MOVIES,adapter);
        super.setupViews(view);
        setRetainInstance(true);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.attachView(this);
        presenter.getWatchlistMovies();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        ((PopularMoviesApplication)(getActivity().getApplication())).releaseWatchlistMoviesComponent();

    }

    @Override
    public void showEmptyWatchlist() {
        layoutEmptyWatchlist.setVisibility(View.VISIBLE);
    }

    @Override
    public void clearScreen() {
        int itemCount = adapter.getItemCount();
        adapter.clearMovies();
        adapter.notifyItemRangeRemoved(0,itemCount);
    }
}

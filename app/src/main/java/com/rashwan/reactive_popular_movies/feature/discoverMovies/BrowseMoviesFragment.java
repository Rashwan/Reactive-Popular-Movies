package com.rashwan.reactive_popular_movies.feature.discoverMovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.rashwan.reactive_popular_movies.PopularMoviesApplication;
import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.feature.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by rashwan on 6/25/16.
 */

public class BrowseMoviesFragment extends BaseFragment implements BrowseMoviesView {

    private int moviesSortPref ;
    private Snackbar snackbar;
    @Inject BrowseMoviesPresenter presenter;
    @Inject BrowseMoviesAdapter browseMoviesAdapter;
    @BindView(R.id.coordinator_layout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.progressbar_browse_movies) ProgressBar pbBrowse;
    @BindView(R.id.layout_offline) LinearLayout layoutOffline;
    @BindView(R.id.layout_no_favorites) LinearLayout layoutNoFavorites;
    public static final String ARG_PAGE = "ARG_PAGE";


    public static BrowseMoviesFragment newInstance(int page) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE,page);
        BrowseMoviesFragment fragment = new BrowseMoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((PopularMoviesApplication)getActivity().getApplication())
                .createBrowseMoviesComponent().inject(this);
        chooseSortPref(getArguments().getInt(ARG_PAGE));

    }

    private void chooseSortPref(int mPage) {
        switch (mPage){
            case 0:
                moviesSortPref = BrowseMoviesPresenter.SORT_POPULAR_MOVIES;
                break;
            case 1:
                moviesSortPref = BrowseMoviesPresenter.SORT_TOP_RATED_MOVIES;
                break;
            case 2:
                moviesSortPref = BrowseMoviesPresenter.SORT_UPCOMING_MOVIES;
                break;
            default:
                moviesSortPref = BrowseMoviesPresenter.SORT_POPULAR_MOVIES;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browse_movies, container, false);
        super.onCreateBaseFragment(presenter,moviesSortPref,browseMoviesAdapter);
        super.setupViews(view);

        setRetainInstance(true);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.attachView(this);
        if (browseMoviesAdapter.isEmpty()) {
            presenter.getMovies(moviesSortPref, true);
        }
    }

    @Override
    public void showProgress() {
        pbBrowse.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbBrowse.setVisibility(View.GONE);}
    @Override
    public void clearScreen() {
        layoutNoFavorites.setVisibility(View.GONE);
        layoutOffline.setVisibility(View.GONE);
        if (snackbar != null) {
            snackbar.dismiss();
        }
        browseMoviesAdapter.clearMovies();
        browseMoviesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showOfflineLayout() {
        layoutOffline.setVisibility(View.VISIBLE);
    }

    @Override
    public void showOfflineSnackbar() {
        snackbar = Snackbar.make(coordinatorLayout,"Please check your internet connection",Snackbar.LENGTH_INDEFINITE)
            .setAction("refresh", view -> presenter.getMovies(moviesSortPref,false));
        snackbar.show();
    }

    @Override
    public void showNoFavorites() {
        layoutNoFavorites.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        ((PopularMoviesApplication)getActivity().getApplication()).releaseBrowseMoviesComponent();
    }

    @OnClick(R.id.button_refresh)
    public void onRefreshClicked(){
        presenter.getMovies(moviesSortPref,true);
    }
}

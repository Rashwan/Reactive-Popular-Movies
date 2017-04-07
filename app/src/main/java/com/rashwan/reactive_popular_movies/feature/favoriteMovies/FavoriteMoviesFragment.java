package com.rashwan.reactive_popular_movies.feature.favoriteMovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rashwan.reactive_popular_movies.PopularMoviesApplication;
import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.common.utilities.Utilities;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.BrowseMoviesAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by rashwan on 4/7/17.
 */

public class FavoriteMoviesFragment extends Fragment implements FavoriteMoviesView,BrowseMoviesAdapter.ClickListener{
    @Inject FavoriteMoviesPresenter presenter;
    @Inject BrowseMoviesAdapter adapter;
    @BindView(R.id.rv_browse_movies) RecyclerView rvBrowseMovies;
    private boolean isTwoPane;
    private Unbinder unbinder;
    @BindView(R.id.layout_no_favorites) LinearLayout layoutNoFavorites;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((PopularMoviesApplication)getActivity().getApplication())
                .createFavoriteMoviesComponent().inject(this);
        isTwoPane = Utilities.isScreenSW(800);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_movies,container,false);
        unbinder = ButterKnife.bind(this, view);
        setupViews();
        setRetainInstance(true);
        return view;
    }

    private void setupViews() {
        boolean isSmallScreen = Utilities.isScreenSW(600);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 6);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (isTwoPane) {
                    return 2;
                }else {
                    if (isSmallScreen){
                        return 2;
                    }else {
                        return 3;
                    }
                }
            }
        });
        rvBrowseMovies.setHasFixedSize(true);
        rvBrowseMovies.setLayoutManager(gridLayoutManager);
        rvBrowseMovies.setAdapter(adapter);

        //When the recyclerView is drawn start the postponed shared element transition
        rvBrowseMovies.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                rvBrowseMovies.getViewTreeObserver().removeOnPreDrawListener(this);
                getActivity().supportStartPostponedEnterTransition();
                return true;
            }
        });
        adapter.setClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.attachView(this);
        presenter.getFavoriteMovies();

    }

    @Override
    public void showMovies(List<Movie> movies) {
        int currentSize = adapter.getItemCount();
        adapter.addMovies(movies);
        adapter.notifyItemRangeInserted(currentSize,movies.size());
    }

    @Override
    public void showNoFavorites() {
        layoutNoFavorites.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMovieClicked(Movie movie, ImageView view) {

    }
    @Override
    public void onPause() {
        super.onPause();
        //Needs better handling to unsubscribe
        presenter.detachView();
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
        ((PopularMoviesApplication)getActivity().getApplication()).releaseFavoriteMoviesComponent();
    }

}

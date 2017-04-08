package com.rashwan.reactive_popular_movies.feature;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.common.BasePresenter;
import com.rashwan.reactive_popular_movies.common.utilities.DelegateToActivity;
import com.rashwan.reactive_popular_movies.common.utilities.EndlessRecyclerViewScrollListener;
import com.rashwan.reactive_popular_movies.common.utilities.Utilities;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.BrowseMoviesAdapter;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.BrowseMoviesPresenter;
import com.rashwan.reactive_popular_movies.feature.favoriteMovies.FavoriteMoviesPresenter;
import com.rashwan.reactive_popular_movies.feature.nearbyMovies.NearbyMoviesPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by rashwan on 4/8/17.
 */

public class BaseFragment extends Fragment implements BrowseMoviesAdapter.ClickListener{
    private DelegateToActivity delegateListener;
    private boolean isTwoPane;
    @BindView(R.id.rv_browse_movies) RecyclerView rvBrowseMovies;
    BasePresenter presenter;
    int moviesSortPref;
    Unbinder unbinder;
    BrowseMoviesAdapter browseMoviesAdapter;



    @Override
    public void onMovieClicked(Movie movie, ImageView view) {
        delegateListener.delegateMovieClicked(movie,view);

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DelegateToActivity){
            delegateListener = (DelegateToActivity) context;
        }
    }
    public void showMovies(List<Movie> movies) {
        int currentSize = browseMoviesAdapter.getItemCount();
        browseMoviesAdapter.addMovies(movies);
        browseMoviesAdapter.notifyItemRangeInserted(currentSize,movies.size());
    }
    public void onCreateBaseFragment(BasePresenter presenter,int moviesSortPref,BrowseMoviesAdapter browseMoviesAdapter) {
        isTwoPane = Utilities.isScreenSW(800);
        this.presenter = presenter;
        this.moviesSortPref = moviesSortPref;
        this.browseMoviesAdapter = browseMoviesAdapter;
    }

    public void setupViews(View view){

        unbinder = ButterKnife.bind(this, view);
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
        rvBrowseMovies.setAdapter(browseMoviesAdapter);
        rvBrowseMovies.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore() {
                if (moviesSortPref != BrowseMoviesPresenter.SORT_FAVORITE_MOVIES
                        && moviesSortPref != BrowseMoviesPresenter.SORT_NEARBY_MOVIES) {

                    ((BrowseMoviesPresenter)(presenter)).getMovies(moviesSortPref, false);
                }
            }
        });


        //When the recyclerView is drawn start the postponed shared element transition
        rvBrowseMovies.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                rvBrowseMovies.getViewTreeObserver().removeOnPreDrawListener(this);
                getActivity().supportStartPostponedEnterTransition();
                return true;
            }
        });
        browseMoviesAdapter.setClickListener(this);
    }


    @Override
    public void onPause() {
        super.onPause();
        //Needs better handling to unsubscribe
        if (moviesSortPref == BrowseMoviesPresenter.SORT_FAVORITE_MOVIES) {
            FavoriteMoviesPresenter favoriteMoviesPresenter = (FavoriteMoviesPresenter) presenter;
            favoriteMoviesPresenter.detachView();
        }else if(moviesSortPref == BrowseMoviesPresenter.SORT_NEARBY_MOVIES) {
            NearbyMoviesPresenter nearbyMoviesPresenter = (NearbyMoviesPresenter) presenter;
            nearbyMoviesPresenter.detachView();
        }else {
            BrowseMoviesPresenter browseMoviesPresenter = (BrowseMoviesPresenter) presenter;
            browseMoviesPresenter.detachView();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}

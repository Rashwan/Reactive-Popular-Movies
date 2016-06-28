package com.rashwan.reactive_popular_movies.feature.browseMovies;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    BrowseMoviesAdapter browseMoviesAdapter;

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
        rvBrowseMovies.setHasFixedSize(true);
        rvBrowseMovies.setLayoutManager(new GridLayoutManager(getActivity(),2));
        rvBrowseMovies.setAdapter(browseMoviesAdapter);
        //Call the presenter's getMovies method
        presenter.attachView(this);
        presenter.getMovies();
        return view;
    }

    @Override
    public void showMovies(List<Movie> movies) {

        browseMoviesAdapter.setMovies(movies);
        browseMoviesAdapter.notifyDataSetChanged();
    }
}

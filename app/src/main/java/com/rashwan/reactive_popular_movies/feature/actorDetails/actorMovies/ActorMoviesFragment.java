package com.rashwan.reactive_popular_movies.feature.actorDetails.actorMovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rashwan.reactive_popular_movies.PopularMoviesApplication;
import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.common.utilities.DividerItemDecoration;
import com.rashwan.reactive_popular_movies.data.model.ActorMovie;
import com.rashwan.reactive_popular_movies.data.model.Cast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by rashwan on 5/12/17.
 */

public class ActorMoviesFragment extends Fragment implements ActorMoviesView{
    private static final String ARGUMENT_CAST_ITEM = "com.rashwan.reactive_popular_movies.feature.actorDetails.actorMovies.EXTRA_CAST_ID";

    @BindView(R.id.actor_movies_rv) RecyclerView actorMoviesRV;
    @Inject ActorMoviesPresenter presenter;
    @Inject ActorMoviesAdapter adapter;
    private Cast castItem;
    private Unbinder unbinder;

    public static ActorMoviesFragment newInstance(Cast castItem) {
        ActorMoviesFragment actorMoviesFragment = new ActorMoviesFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT_CAST_ITEM, castItem);
        actorMoviesFragment.setArguments(bundle);
        return actorMoviesFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ((PopularMoviesApplication)getActivity().getApplication()).createActorMoviesComponent()
                .inject(this);
        castItem = getArguments().getParcelable(ARGUMENT_CAST_ITEM);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_actor_movies, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.attachView(this);
        setupViews();
        return view;
    }

    private void setupViews() {
        setupActorMoviesRV();
        presenter.getActorMovies(castItem.id());
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        ((PopularMoviesApplication)getActivity().getApplication()).releaseActorInfoComponent();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }
    private void setupActorMoviesRV() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        actorMoviesRV.setLayoutManager(linearLayoutManager);
        actorMoviesRV.setHasFixedSize(true);
        actorMoviesRV.setNestedScrollingEnabled(false);
        actorMoviesRV.addItemDecoration(new DividerItemDecoration(getActivity()));
        actorMoviesRV.setAdapter(adapter);
    }

    @Override
    public void showActorMovies(List<ActorMovie> actorMovies) {
        adapter.addActorMovies(actorMovies);
        adapter.notifyDataSetChanged();
    }
}

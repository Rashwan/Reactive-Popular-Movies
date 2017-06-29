package com.rashwan.reactive_popular_movies.feature.movieDetails.movieCast;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rashwan.reactive_popular_movies.PopularMoviesApplication;
import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.common.utilities.DelegateToActivity;
import com.rashwan.reactive_popular_movies.common.utilities.RvItemClickListener;
import com.rashwan.reactive_popular_movies.data.model.Cast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by rashwan on 4/21/17.
 */

public class MovieCastFragment extends Fragment implements MovieCastView,RvItemClickListener<Cast>{
    private static final String ARGUMENT_MOVIE_ID = "ARGUMENT_MOVIE_ID";
    private long movieId;
    private Unbinder unbinder;
    @BindView(R.id.rv_movie_cast) RecyclerView rvCast;
    @BindView(R.id.offline_layout) LinearLayout offlineLayout;
    @Inject MovieCastPresenter presenter;
    @Inject MovieCastAdapter castAdapter;
    private DelegateToActivity<Cast> delegateListener;


    public static MovieCastFragment newInstance(long movieId) {
        MovieCastFragment movieCastFragment = new MovieCastFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(ARGUMENT_MOVIE_ID, movieId);
        movieCastFragment.setArguments(bundle);
        return movieCastFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!= null) {
            movieId = getArguments().getLong(ARGUMENT_MOVIE_ID);
        }
        ((PopularMoviesApplication) getActivity().getApplication())
                .createMovieCastComponent().inject(this);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DelegateToActivity){
            delegateListener = (DelegateToActivity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        delegateListener = null;
        castAdapter.removeClickListener();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_cast, container, false);
        unbinder = ButterKnife.bind(this, view);
        setupCastRv();
        presenter.attachView(this);
        presenter.getMovieCast(movieId);
        return view;
    }
    private void setupCastRv() {
        castAdapter.setItemClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvCast.setLayoutManager(linearLayoutManager);
        rvCast.setNestedScrollingEnabled(false);
        rvCast.setAdapter(castAdapter);
    }

    @Override
    public void showCast(List<Cast> castList) {
        castAdapter.addCast(castList);
        castAdapter.notifyItemRangeInserted(0,60);
    }

    @Override
    public void showOfflineLayout() {
        offlineLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideOfflineLayout() {
        offlineLayout.setVisibility(View.GONE);
    }
    @OnClick(R.id.button_refresh)
    public void onRefreshClicked(){
        presenter.getMovieCast(movieId);
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
        ((PopularMoviesApplication)getActivity().getApplication()).releaseMovieCastComponent();
    }

    @Override
    public void onItemClicked(Cast item, ImageView sharedView) {
        delegateListener.delegateItemClicked(item,sharedView);
    }
}

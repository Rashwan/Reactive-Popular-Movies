package com.rashwan.reactive_popular_movies.feature.nearbyMovies;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.nearby.Nearby;
import com.rashwan.reactive_popular_movies.PopularMoviesApplication;
import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.common.utilities.DelegateToActivity;
import com.rashwan.reactive_popular_movies.common.utilities.Utilities;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.feature.browseMovies.BrowseMoviesAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import timber.log.Timber;

import static android.app.Activity.RESULT_OK;

/**
 * Created by rashwan on 1/8/17.
 */

public class NearbyMoviesFragment extends Fragment implements
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks
        , NearbyMoviesView,BrowseMoviesAdapter.ClickListener{
    public static final int REQUEST_RESOLVE_ERROR = 1001;
    private GoogleApiClient mGoogleApiClient;
    @Inject NearbyMoviesPresenter presenter;
    @Inject BrowseMoviesAdapter adapter;
    @BindView(R.id.toggleButton_nearby) ToggleButton nearbyToggleButton;
    @BindView(R.id.rv_nearby_movies) RecyclerView nearbyMoviesRv;
    @BindView(R.id.progressbar_nearby_movies) ProgressBar nearbyPb;
    @BindView(R.id.image_nearby_logo) ImageView nearbyLogo;
    @BindView(R.id.text_nearby_description) TextView nearbyDescription;
    @BindView(R.id.text_nearby_searching) TextView nearbySearching;
    private DelegateToActivity delegateListener;
    private boolean isTwoPane;
    private Unbinder unbinder;

    public static NearbyMoviesFragment newInstance() {
        Bundle args = new Bundle();
        NearbyMoviesFragment fragment;
        fragment = new NearbyMoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        ((PopularMoviesApplication)getActivity().getApplication())
                .createNearbyMoviesComponent().inject(this);
        buildGoogleClient();
        isTwoPane = Utilities.isScreenSW(800);

        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_nearby_movies,container,false);
        unbinder = ButterKnife.bind(this,view);
        setupViews();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DelegateToActivity){
            delegateListener = (DelegateToActivity) context;
        }
    }
    private void setupViews(){
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
        nearbyMoviesRv.setHasFixedSize(true);
        nearbyMoviesRv.setLayoutManager(gridLayoutManager);
        nearbyMoviesRv.setAdapter(adapter);

        //When the recyclerView is drawn start the postponed shared element transition
        nearbyMoviesRv.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                nearbyMoviesRv.getViewTreeObserver().removeOnPreDrawListener(this);
                getActivity().supportStartPostponedEnterTransition();
                return true;
            }
        });
        adapter.setClickListener(this);
    }
    private void buildGoogleClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Nearby.MESSAGES_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        presenter.onConnectionFailed(connectionResult);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        presenter.onConnected(bundle,mGoogleApiClient);

    }

    @Override
    public void onConnectionSuspended(int i) {
        Timber.d("google Api disconnected");
        presenter.onConnectionSuspended(i);
    }
    @OnClick(R.id.toggleButton_nearby)
    public void
    onNearbyButtonClicked (ToggleButton button){
        if (button.isChecked()) {
            presenter.startNearbyClicked(mGoogleApiClient);
        }else {
            presenter.stopNearbyClicked(mGoogleApiClient);
        }
    }

    @Override
    public void onStop() {
        Timber.d("on stop");
//        presenter.stopGoogleApi(mGoogleApiClient);
        super.onStop();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (!isVisibleToUser && presenter != null){
            presenter.nearbyHidden(mGoogleApiClient);
            adapter.clearMovies();
            nearbyToggleButton.setChecked(false);
        }
        super.setUserVisibleHint(isVisibleToUser);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Timber.d("On activity result");
        if (requestCode == REQUEST_RESOLVE_ERROR) {
            if (resultCode == RESULT_OK) {
                Timber.d("result ok");
                mGoogleApiClient.connect();
            } else {
                Timber.d("GoogleApiClient connection failed. Unable to resolve.");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null ){
           presenter.activityCreated(mGoogleApiClient);
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void resolveError(ConnectionResult connectionResult) {
        try {
            connectionResult.startResolutionForResult(getActivity(), REQUEST_RESOLVE_ERROR);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void showNearbyMovie(Movie movie) {
        adapter.addMovie(movie);
        adapter.notifyItemInserted(adapter.getItemCount()-1);
    }

    @Override
    public void showProgress() {
        nearbyPb.setVisibility(View.VISIBLE);
        nearbyLogo.setVisibility(View.GONE);
        nearbySearching.setVisibility(View.VISIBLE);
        nearbyDescription.setVisibility(View.GONE);


    }

    @Override
    public void hideProgress() {
        nearbyLogo.setVisibility(View.VISIBLE);
        nearbyPb.setVisibility(View.GONE);
        nearbyDescription.setVisibility(View.VISIBLE);
        nearbySearching.setVisibility(View.GONE);
        if (nearbyToggleButton.isChecked()) {
            nearbyToggleButton.setChecked(false);
        }
    }

    @Override
    public void clearScreen() {
        nearbyPb.setVisibility(View.GONE);
        nearbySearching.setVisibility(View.GONE);
        nearbyToggleButton.setVisibility(View.GONE);
    }

    @Override
    public void onMovieClicked(Movie movie, ImageView view) {
        delegateListener.delegateMovieClicked(movie,view);
    }
    @Override
    public void onPause() {
        super.onPause();
        //Needs better handling to unsubscribe
        presenter.detachView();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        unbinder.unbind();
        ((PopularMoviesApplication)getActivity().getApplication()).releaseNearbyMoviesComponent();
    }
}

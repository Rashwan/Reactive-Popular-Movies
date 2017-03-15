package com.rashwan.reactive_popular_movies.feature.nearbyMovies;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.nearby.Nearby;
import com.rashwan.reactive_popular_movies.PopularMoviesApplication;
import com.rashwan.reactive_popular_movies.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

import static android.app.Activity.RESULT_OK;

/**
 * Created by rashwan on 1/8/17.
 */

public class NearbyMoviesFragment extends Fragment implements
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks , NearbyMoviesView{
    public static final int REQUEST_RESOLVE_ERROR = 1001;
    private GoogleApiClient mGoogleApiClient;
    @Inject NearbyMoviesPresenter presenter;
    @BindView(R.id.button_nearby) Button nearbyButton;

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
        presenter.attachView(this);
        buildGoogleClient();
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_nearby_movies,container,false);
        ButterKnife.bind(this,view);

        return view;
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
    @OnClick(R.id.button_nearby)
    public void onNearbyClicked (){
        presenter.buttonClicked(mGoogleApiClient);
    }

    @Override
    public void onStop() {
        Timber.d("on stop");
        presenter.stopGoogleApi(mGoogleApiClient);
        super.onStop();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (!isVisibleToUser && presenter != null){
            presenter.nearbyHidden(mGoogleApiClient);
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
}

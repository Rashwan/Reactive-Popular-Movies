package com.rashwan.reactive_popular_movies.feature.discoverMovies.nearbyMovies;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.nearby.Nearby;
import com.rashwan.reactive_popular_movies.PopularMoviesApplication;
import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.common.utilities.Utilities;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.feature.BaseFragment;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.BrowseMoviesAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static android.app.Activity.RESULT_OK;

/**
 * Created by rashwan on 1/8/17.
 */

public class NearbyMoviesFragment extends BaseFragment implements
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks
        , NearbyMoviesView{
    public static final int REQUEST_RESOLVE_ERROR = 1001;
    private GoogleApiClient mGoogleApiClient;
    @Inject NearbyMoviesPresenter presenter;
    @Inject BrowseMoviesAdapter adapter;
    @BindView(R.id.toggleButton_nearby) ToggleButton nearbyToggleButton;
    @BindView(R.id.progressbar_nearby_movies) ProgressBar nearbyPb;
    @BindView(R.id.image_nearby_logo) ImageView nearbyLogo;
    @BindView(R.id.text_nearby_description) TextView nearbyDescription;
    @BindView(R.id.text_nearby_searching) TextView nearbySearching;
    @BindView(R.id.layout_nearby_offline) LinearLayout nearbyOffline;
    @BindView(R.id.layout_nearby_logo) LinearLayout nearbyLogoLayout;
    @BindView(R.id.button_nearby_refresh) Button nearbyRefresh;

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
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_nearby_movies,container,false);
        super.onCreateBaseFragment(presenter, SORT_NEARBY_MOVIES,adapter);
        super.setupViews(view);
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
        presenter.onConnected(mGoogleApiClient);

    }

    @Override
    public void onConnectionSuspended(int i) {
        Timber.d("google Api disconnected");
        presenter.onConnectionSuspended(i);
    }
    @OnClick(R.id.toggleButton_nearby)
    public void onNearbyButtonClicked (ToggleButton button){
        if (!Utilities.isNetworkAvailable(getActivity().getApplication())){
            showOfflineLayout();
            button.setChecked(false);
        }else {
            if (button.isChecked()) {
                presenter.startNearbyClicked(mGoogleApiClient);
            } else {
                presenter.stopNearbyClicked(mGoogleApiClient);
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (!isVisibleToUser && presenter != null){
            presenter.nearbyHidden(mGoogleApiClient);
            adapter.clearMovies();
            if (nearbyToggleButton != null) {
                nearbyToggleButton.setChecked(false);
            }
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
        if (adapter.isEmpty()) {
            nearbyLogo.setVisibility(View.VISIBLE);
            nearbyPb.setVisibility(View.GONE);
            nearbyDescription.setVisibility(View.VISIBLE);
            nearbySearching.setVisibility(View.GONE);
            if (nearbyToggleButton.isChecked()) {
                nearbyToggleButton.setChecked(false);
            }
        }
    }

    @Override
    public void clearScreen() {
        nearbyPb.setVisibility(View.GONE);
        nearbySearching.setVisibility(View.GONE);
        nearbyToggleButton.setVisibility(View.GONE);
    }

    @Override
    public void showOfflineLayout() {
        nearbyLogoLayout.setVisibility(View.GONE);
        nearbyOffline.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideOfflineLayout() {
        nearbyOffline.setVisibility(View.GONE);
        nearbyLogoLayout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.button_nearby_refresh)
    void onRefreshClicked(){
        if (Utilities.isNetworkAvailable(getActivity().getApplication())){
            hideOfflineLayout();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
//        unbinder.unbind();
        ((PopularMoviesApplication)getActivity().getApplication()).releaseNearbyMoviesComponent();
    }
}

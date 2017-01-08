package com.rashwan.reactive_popular_movies.feature.nearbyMovies;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.nearby.Nearby;
import com.rashwan.reactive_popular_movies.R;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

public class NearbyMoviesActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,GoogleApiClient.ConnectionCallbacks{
    private GoogleApiClient mGoogleApiClient;
    @BindView(R.id.button_start_nearby) Button nearbyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_movies);
    }

    private void connectGoogleClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Nearby.MESSAGES_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .enableAutoManage(this,this)
                .build();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Timber.d("Google Api Connection failed %s",connectionResult.getErrorMessage());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Timber.d("google Api Connected");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    protected void onStop() {
        super.onStop();
    }
    @OnClick(R.id.button_start_nearby)
    public void onNearbyClicked (){
        if (!mGoogleApiClient.isConnected() || mGoogleApiClient == null){
            connectGoogleClient();
        }
    }
}

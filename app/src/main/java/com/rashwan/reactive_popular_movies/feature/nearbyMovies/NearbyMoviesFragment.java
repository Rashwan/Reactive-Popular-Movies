package com.rashwan.reactive_popular_movies.feature.nearbyMovies;

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
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.rashwan.reactive_popular_movies.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by rashwan on 1/8/17.
 */

public class NearbyMoviesFragment extends Fragment implements
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
    private GoogleApiClient mGoogleApiClient;
    private Message mActiveMessage;
    MessageListener messageListener;

    @BindView(R.id.button_nearby)
    Button nearbyButton;

    public static NearbyMoviesFragment newInstance() {

        Bundle args = new Bundle();

        NearbyMoviesFragment fragment = new NearbyMoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageListener = new MessageListener() {
            @Override
            public void onFound(Message message) {
                super.onFound(message);
                Timber.d("Found This Message: %s",message.toString());
            }

            @Override
            public void onLost(Message message) {
                super.onLost(message);
                Timber.d("Lost This Message: %s",message.toString());

            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_nearby_movies,container,false);
        ButterKnife.bind(this,view);
        return view;
    }
    private void connectGoogleClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(Nearby.MESSAGES_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .enableAutoManage(getActivity(),this)
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
        Timber.d("google Api disconnected");

    }
    @OnClick(R.id.button_nearby)
    public void onNearbyClicked (){
        Timber.d("button Clicked");
        if (mGoogleApiClient == null){
            connectGoogleClient();
        }
        publish("Hello!");
        subscribe();
    }

    @Override
    public void onStart() {
        Timber.d("on start");
        super.onStart();
    }

    @Override
    public void onStop() {
        Timber.d("on stop");
        unpublish();
        unsubscribe();
        super.onStop();
    }

    @Override
    public void onPause() {
        Timber.d("on pause");
        super.onPause();
    }
    private void publish(String message){
        if (mGoogleApiClient.isConnected()){
            Timber.d("Publishing message: " + message);
            mActiveMessage = new Message(message.getBytes());
            Nearby.Messages.publish(mGoogleApiClient,mActiveMessage);
        }else {
            Timber.d("couldn't publish because google client is disconnected");
        }

    }
    private void unpublish() {
        Timber.d("Unpublishing");
        if (mGoogleApiClient.isConnected()) {
            if (mActiveMessage != null) {
                Nearby.Messages.unpublish(mGoogleApiClient, mActiveMessage);
                mActiveMessage = null;
            }
        }else {
            Timber.d("couldn't unpublish because google client is disconnected");
        }
    }
    private void subscribe(){
        Timber.d("Subscribing");
        if (mGoogleApiClient.isConnected()) {
            Nearby.Messages.subscribe(mGoogleApiClient, messageListener);
        }else {
            Timber.d("couldn't subscribe because google client is disconnected");
        }
    }
    private void unsubscribe() {
        Timber.d("Unsubscribing.");
        if (mGoogleApiClient.isConnected()) {
            Nearby.Messages.unsubscribe(mGoogleApiClient, messageListener);
        }else {
            Timber.d("couldn't unsubscribe because google client is disconnected");
        }
    }
}

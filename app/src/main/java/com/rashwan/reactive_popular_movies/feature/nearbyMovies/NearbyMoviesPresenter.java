package com.rashwan.reactive_popular_movies.feature.nearbyMovies;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.SubscribeCallback;
import com.google.android.gms.nearby.messages.SubscribeOptions;
import com.rashwan.reactive_popular_movies.common.BasePresenter;
import com.rashwan.reactive_popular_movies.service.MoviesService;
import com.squareup.moshi.Moshi;

import rx.Subscription;
import timber.log.Timber;

/**
 * Created by rashwan on 1/30/17.
 */

public class NearbyMoviesPresenter extends BasePresenter<NearbyMoviesView> {
    private final MessageListener messageListener;
    private MoviesService moviesService;
    private Subscription favoriteSubscription;
    private Moshi moshi;
    private boolean nearbyActive;
    private Message mActiveMessage;


    public NearbyMoviesPresenter(MoviesService moviesService,Moshi moshi) {
        this.moviesService = moviesService;
        this.moshi = moshi;
        nearbyActive = false;
        messageListener = new MessageListener() {
            @Override
            public void onFound(Message message) {
                super.onFound(message);
                String string = new String(message.getContent());
                Timber.d("Found This Message %s",string);
            }

            @Override
            public void onLost(Message message) {
                super.onLost(message);
                String string = new String(message.getContent());
                Timber.d("Lost This Message: %s", string);

            }
        };

    }
//    public void getFavoriteMovies() {
//        if (favoriteSubscription == null || favoriteSubscription.isUnsubscribed()) {
//            favoriteSubscription = moviesService.getFavoriteMovies().observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(movies -> {
//                                Timber.d(String.valueOf(movies.size()));
//                                if (movies.isEmpty()){
////                                    getView().clearScreen();
////                                    getView().showNoFavorites();
//                                }else {
//                                    if (movies.size() != favoriteMovies.size()){
////                                        getView().clearScreen();
////                                        getView().showMovies(movies);
//                                    }
//                                }
//                                favoriteMovies = movies;
//                                JsonAdapter<Movie> adapter = moshi.adapter(Movie.class);
//                                String s;
//
//                                for (Movie m: movies) {
//                                    s = adapter.toJson(m);
//                                    Message me = new Message(s.getBytes());
//                                }
//                            }
//                            , throwable -> Timber.d(throwable, throwable.getMessage())
//                            , () -> Timber.d("Finished getting fav movies"));
//        }else {
//            getView().hideProgress();
//            if (favoriteMovies.isEmpty()){
//                getView().showNoFavorites();
//            }else {
//                getView().showMovies(favoriteMovies);
//            }
//        }
//
//    }

    public void onConnected(@Nullable Bundle bundle,GoogleApiClient mGoogleApiClient) {
        Timber.d("presenter google Api Connected");
        if (nearbyActive){
            subscribe(mGoogleApiClient);
            publish("Hello",mGoogleApiClient);
        }
    }

    public void onConnectionSuspended(int i) {
        Timber.d("presenter google Api disconnected");

    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Timber.d("Google Api Connection failed %s",connectionResult.getErrorMessage());
        if (connectionResult.hasResolution()) {
            getView().resolveError(connectionResult);
        } else {
            Timber.d("GoogleApiClient connection failed");
        }
    }

    public void buttonClicked(GoogleApiClient mGoogleApiClient){
        Timber.d("button Clicked");
        if (nearbyActive){
            nearbyActive = false;
            unpublish(mGoogleApiClient);
            unsubscribe(mGoogleApiClient);
        }else {
            nearbyActive = true;
            if (mGoogleApiClient.isConnected()){
                subscribe(mGoogleApiClient);
                publish("Hello!!",mGoogleApiClient);

            }else {
                mGoogleApiClient.connect();
            }
        }
    }
    public void stopGoogleApi(GoogleApiClient mGoogleApiClient){
        if (mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }
    private void publish(String message,GoogleApiClient mGoogleApiClient){
        if (mGoogleApiClient.isConnected()){
            Timber.d("Publishing message: " + message);
            mActiveMessage = new Message(message.getBytes());
            Nearby.Messages.publish(mGoogleApiClient,mActiveMessage)
                    .setResultCallback(status -> {
                        if (status.isSuccess()) {
                            Timber.d("published successfully.");
                        }else {
                            Timber.d("didn't published successfully.");

                        }

                    });
        }else {
            Timber.d("couldn't publish because google client is disconnected");
        }

    }
    private void unpublish(GoogleApiClient mGoogleApiClient) {
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
    private void subscribe(GoogleApiClient mGoogleApiClient){
        Timber.d("Subscribing");
        if (mGoogleApiClient.isConnected()) {
            SubscribeOptions subscribeOptions = new SubscribeOptions.Builder().setCallback(new SubscribeCallback() {
                @Override
                public void onExpired() {
                    Timber.d("expired");
                    super.onExpired();
                }
            }).build();
            Nearby.Messages.subscribe(mGoogleApiClient, messageListener, subscribeOptions)
                    .setResultCallback(status -> {
                        if (status.isSuccess()) {
                            Timber.d("Subscribed successfully.");
                        } else {
                            Timber.d("didn't Subscribed successfully.");

                        }
                    });
        }else {
            Timber.d("couldn't subscribe because google client is disconnected");
        }
    }
    private void unsubscribe(GoogleApiClient mGoogleApiClient) {
        Timber.d("Unsubscribing.");
        if (mGoogleApiClient.isConnected()) {
            Nearby.Messages.unsubscribe(mGoogleApiClient, messageListener);
        }else {
            Timber.d("couldn't unsubscribe because google client is disconnected or null");
        }
    }
    public void activityCreated(GoogleApiClient mGoogleApiClient){
        if ( nearbyActive){
            mGoogleApiClient.connect();
        }
    }
    public void nearbyHidden(GoogleApiClient mGoogleApiClient){
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected() && nearbyActive){
            unpublish(mGoogleApiClient);
            unsubscribe(mGoogleApiClient);
            nearbyActive = false;
        }
    }
}

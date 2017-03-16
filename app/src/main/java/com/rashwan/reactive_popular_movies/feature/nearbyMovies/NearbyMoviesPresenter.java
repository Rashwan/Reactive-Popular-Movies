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
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by rashwan on 1/30/17.
 */

public class NearbyMoviesPresenter extends BasePresenter<NearbyMoviesView> {
    private final MessageListener messageListener;
    private MoviesService moviesService;
    private Subscription favoriteIdsSubscription;
    private Subscription nearbyMoviesSubscription;
    private boolean nearbyActive;
    private Message mActiveMessage;
    private List<Long> idsList;
    private JsonAdapter<List<Long>> adapter;
    private Subscription subscription;
    private List<Long> receivedIds = new ArrayList<>();



    public NearbyMoviesPresenter(MoviesService moviesService,Moshi moshi) {
        this.moviesService = moviesService;
        nearbyActive = false;
        ParameterizedType listLong = Types.newParameterizedType(List.class,Long.class);
        adapter = moshi.adapter(listLong);
        messageListener = new MessageListener() {
            @Override
            public void onFound(Message message) {
                super.onFound(message);
                String content = new String(message.getContent());
                try {
                    getNearbyMoviesDetails(adapter.fromJson(content));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Timber.d("Found This Message %s",content);
            }

            @Override
            public void onLost(Message message) {
                super.onLost(message);
                String string = new String(message.getContent());
                Timber.d("Lost This Message: %s", string);

            }
        };

    }
    private void getFavoritesIds(GoogleApiClient mGoogleApiClient){
        Timber.d("getting favorites ids");
        if (favoriteIdsSubscription == null || favoriteIdsSubscription.isUnsubscribed()){
            favoriteIdsSubscription = moviesService.getFavoriteMoviesIds().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(ids -> {
                        Timber.d("Got %d ids from favorites",ids.size());
                        publish(ids,mGoogleApiClient);
                        idsList = ids;
                    });
        }else {
            publish(idsList,mGoogleApiClient);
        }
    }
    private void getNearbyMoviesDetails(List<Long> ids){
        Timber.d("getting nearby movies details");
        for (Long id: ids) {
            if (!receivedIds.contains(id)){
                subscription = moviesService.getMovieDetails(id).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(
                                movie -> {
                                    getView().showNearbyMovie(movie);
                                    Timber.d("received Movie with Title: %s", movie.title());
                                    receivedIds.add(id);
                                }
                        );
            }else {
                Timber.d("We already have this movie!");
            }
        }
    }

    public void onConnected(@Nullable Bundle bundle,GoogleApiClient mGoogleApiClient) {
        Timber.d("presenter google Api Connected");
        if (nearbyActive){
            subscribe(mGoogleApiClient);
            getFavoritesIds(mGoogleApiClient);

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

    public void startNearbyClicked(GoogleApiClient mGoogleApiClient){
        Timber.d("start button Clicked");
            nearbyActive = true;
            if (mGoogleApiClient.isConnected()){
                subscribe(mGoogleApiClient);
                getFavoritesIds(mGoogleApiClient);

            }else {
                mGoogleApiClient.connect();
            }
    }
    public void stopNearbyClicked(GoogleApiClient mGoogleApiClient){
        Timber.d("stop button clicked");
        nearbyActive = false;
        unpublish(mGoogleApiClient);
        unsubscribe(mGoogleApiClient);
    }
    public void stopGoogleApi(GoogleApiClient mGoogleApiClient){
        if (mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }
    private void publish(List<Long> ids, GoogleApiClient mGoogleApiClient){
        Timber.d("publishing");
        if (mGoogleApiClient.isConnected()){

            String idsString = adapter.toJson(ids);
            Timber.d("Converted: %s",idsString);

            mActiveMessage = new Message(idsString.getBytes());
            Nearby.Messages.publish(mGoogleApiClient,mActiveMessage)
                    .setResultCallback(status -> {
                        if (status.isSuccess()){
                            Timber.d("published successfully.");
                        }else {
                            Timber.d("didn't publish successfully.");
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
        if (nearbyActive){
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

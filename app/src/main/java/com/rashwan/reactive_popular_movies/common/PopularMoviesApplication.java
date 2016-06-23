package com.rashwan.reactive_popular_movies.common;

import android.app.Application;

import com.rashwan.reactive_popular_movies.R;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by rashwan on 6/23/16.
 */

public class PopularMoviesApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree() {
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                return super.createStackElementTag(element) + ':' + element.getLineNumber();
            }
        });
        Timber.d("Hello!");

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(chain -> {
            Request originalRequest = chain.request();
            HttpUrl originalUrl = originalRequest.url();

            HttpUrl newUrl = originalUrl.newBuilder()
                    .addQueryParameter("api_key", getString(R.string.movies_api_key))
                    .build();
            Timber.d(newUrl.toString());
            Request.Builder newRequestBuilder = originalRequest.newBuilder().url(newUrl);
            Request newRequest = newRequestBuilder.build();
            return chain.proceed(newRequest);
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.movies_api_base_url))
                .client(okHttpClient.build())
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        TMDBApi moviesApi = retrofit.create(TMDBApi.class);

    }
}

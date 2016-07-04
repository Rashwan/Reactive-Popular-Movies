package com.rashwan.reactive_popular_movies.DI;

import android.app.Application;

import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.service.MoviesService;
import com.rashwan.reactive_popular_movies.service.MoviesServiceImp;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by rashwan on 6/23/16.
 */
@Module
public class ApplicationModule {
    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides @Singleton
    public Application provideApplicaton(){
        return application;
    }

    @Provides @Singleton @Named("Retrofit Okhttp client")
    public OkHttpClient provideOkhttpClient(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        return new OkHttpClient.Builder().addInterceptor(chain -> {
            Request originalRequest = chain.request();
            HttpUrl originalUrl = originalRequest.url();

            HttpUrl newUrl = originalUrl.newBuilder()
                    .addQueryParameter("api_key", application.getString(R.string.movies_api_key))
                    .build();
            Timber.d(newUrl.toString());
            Request.Builder newRequestBuilder = originalRequest.newBuilder().url(newUrl);
            Request newRequest = newRequestBuilder.build();
            return chain.proceed(newRequest);
        }).addInterceptor(logging).build();
    }

    @Provides @Singleton
    public Retrofit provideRetrofit(@Named("Retrofit Okhttp client") OkHttpClient okHttpClient){
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        return new Retrofit.Builder()
                .baseUrl(application.getString(R.string.movies_api_base_url))
                .client(okHttpClient)
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
    }

    @Provides @Singleton
    public MoviesService provideMoviesService(Retrofit retrofit){
        return new MoviesServiceImp(retrofit);
    }


}

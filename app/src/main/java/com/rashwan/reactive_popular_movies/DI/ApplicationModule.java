package com.rashwan.reactive_popular_movies.DI;

import android.app.Application;

import com.rashwan.reactive_popular_movies.BuildConfig;
import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.data.MovieDatabaseHelper;
import com.rashwan.reactive_popular_movies.data.MyAdapterFactory;
import com.rashwan.reactive_popular_movies.service.MoviesService;
import com.rashwan.reactive_popular_movies.service.MoviesServiceImp;
import com.squareup.moshi.Moshi;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

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

    @Provides @Singleton
    public OkHttpClient provideOkhttpClient(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        return new OkHttpClient.Builder().addInterceptor(chain -> {
            Request originalRequest = chain.request();
            HttpUrl originalUrl = originalRequest.url();

            HttpUrl newUrl = originalUrl.newBuilder()
                    .addQueryParameter("api_key", BuildConfig.MOVIES_API_KEY)
                    .build();
            Request.Builder newRequestBuilder = originalRequest.newBuilder().url(newUrl);
            Request newRequest = newRequestBuilder.build();
            return chain.proceed(newRequest);
        }).addInterceptor(logging).build();
    }

    @Provides @Singleton
    public Moshi provideMoshi(){
        return new Moshi.Builder().add(MyAdapterFactory.create()).build();

    }

    @Provides @Singleton
    public Retrofit provideRetrofit(OkHttpClient okHttpClient,Moshi moshi){
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        return new Retrofit.Builder()
                .baseUrl(application.getString(R.string.movies_api_base_url))
                .client(okHttpClient)
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build();
    }

    @Provides @Singleton
    public MoviesService provideMoviesService(Application application,Retrofit retrofit,BriteDatabase db){
        return new MoviesServiceImp(application,retrofit,db);
    }

    @Provides @Singleton
    public MovieDatabaseHelper provideOpenHelper(Application application){
        return new MovieDatabaseHelper(application);
    }
    @Provides @Singleton
    public SqlBrite provideSqlBrite(){
        return SqlBrite.create(message -> Timber.tag("Database").d(message));
    }

    @Provides @Singleton
    public BriteDatabase provideDatabase(SqlBrite sqlBrite,MovieDatabaseHelper databaseHelper){
        BriteDatabase db =  sqlBrite.wrapDatabaseHelper(databaseHelper,Schedulers.io());
        db.setLoggingEnabled(true);
        return db;
    }
}

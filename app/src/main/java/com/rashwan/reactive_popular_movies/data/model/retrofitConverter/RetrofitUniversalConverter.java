package com.rashwan.reactive_popular_movies.data.model.retrofitConverter;

import android.support.annotation.Nullable;

import com.rashwan.reactive_popular_movies.data.remote.CastResponse;
import com.rashwan.reactive_popular_movies.data.remote.ResultsResponse;
import com.squareup.moshi.Moshi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by rashwan on 6/2/17.
 */
@Singleton
public class RetrofitUniversalConverter extends Converter.Factory {
    private Converter.Factory tmdbMovieConverter;
    private Converter.Factory tmdbCastConverter;
    private Moshi moshi;

    @Inject
    public RetrofitUniversalConverter(@Named("Movie Converter") Converter.Factory tmdbMovieConverter
            , @Named("Cast Converter") Converter.Factory tmdbCastConverter, Moshi moshi) {
        this.tmdbMovieConverter = tmdbMovieConverter;
        this.tmdbCastConverter = tmdbCastConverter;
        this.moshi = moshi;
    }

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        for (Annotation annotation: annotations){
            if (annotation.annotationType() == ResultsResponse.class){
                return tmdbMovieConverter.responseBodyConverter(type,annotations,retrofit);
            }
            if (annotation.annotationType() == CastResponse.class){
                return tmdbCastConverter.responseBodyConverter(type,annotations,retrofit);
            }
        }
        return MoshiConverterFactory.create(moshi).responseBodyConverter(type,annotations,retrofit);
    }
}

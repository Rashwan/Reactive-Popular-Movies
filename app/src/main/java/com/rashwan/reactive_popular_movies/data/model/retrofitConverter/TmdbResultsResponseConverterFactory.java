package com.rashwan.reactive_popular_movies.data.model.retrofitConverter;

import android.support.annotation.Nullable;

import com.rashwan.reactive_popular_movies.data.model.TmdbResultsResponse;
import com.squareup.moshi.Types;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by rashwan on 5/28/17.
 */
public class TmdbResultsResponseConverterFactory extends Converter.Factory {
    private Converter.Factory moshiUniversalFactory ;

    public TmdbResultsResponseConverterFactory(Converter.Factory moshiUniversalFactory) {
        this.moshiUniversalFactory = moshiUniversalFactory;
    }

    @Nullable
    @Override
    public Converter<ResponseBody,?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {

        ParameterizedType listParameterizeType = (ParameterizedType) type;
        Type actualTypeArgument = listParameterizeType.getActualTypeArguments()[0];
        Type parametrizedType = Types.newParameterizedType(TmdbResultsResponse.class, actualTypeArgument);
        Converter<ResponseBody, ?> delegate = moshiUniversalFactory.responseBodyConverter(
                parametrizedType,annotations,retrofit);

        return new TmdbResultsResponseConverter(delegate);
    }
}

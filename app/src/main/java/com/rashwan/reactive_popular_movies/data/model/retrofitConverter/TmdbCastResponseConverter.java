package com.rashwan.reactive_popular_movies.data.model.retrofitConverter;

import android.support.annotation.NonNull;

import com.rashwan.reactive_popular_movies.data.model.TmdbCastResponse;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by rashwan on 6/2/17.
 */

public class TmdbCastResponseConverter<T> implements Converter<ResponseBody,List<T>> {
    private final Converter<ResponseBody,TmdbCastResponse<T>> delegate;

    public TmdbCastResponseConverter(Converter<ResponseBody, TmdbCastResponse<T>> delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<T> convert(@NonNull ResponseBody value) throws IOException {

        TmdbCastResponse<T> tmdbResponse = delegate.convert(value);
        return tmdbResponse.getCast();
    }
}

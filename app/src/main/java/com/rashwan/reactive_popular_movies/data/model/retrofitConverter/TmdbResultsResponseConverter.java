package com.rashwan.reactive_popular_movies.data.model.retrofitConverter;

import android.support.annotation.NonNull;

import com.rashwan.reactive_popular_movies.data.model.TmdbResultsResponse;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by rashwan on 5/28/17.
 */

public class TmdbResultsResponseConverter<T> implements Converter<ResponseBody,List<T>> {
    private final Converter<ResponseBody,TmdbResultsResponse<T>> delegate;

    public TmdbResultsResponseConverter(Converter<ResponseBody, TmdbResultsResponse<T>> delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<T> convert(@NonNull ResponseBody value) throws IOException {
        TmdbResultsResponse<T> tmdbResultsResponse = delegate.convert(value);
        return tmdbResultsResponse.getResults();
    }
}

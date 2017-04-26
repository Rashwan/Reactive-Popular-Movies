package com.rashwan.reactive_popular_movies.feature.movieDetails.movieCast;

import com.rashwan.reactive_popular_movies.common.MvpView;
import com.rashwan.reactive_popular_movies.data.model.Cast;

import java.util.List;

/**
 * Created by rashwan on 4/21/17.
 */

public interface MovieCastView extends MvpView{
    void showCast(List<Cast> castList);
    void showOfflineLayout();
    void hideOfflineLayout();
}

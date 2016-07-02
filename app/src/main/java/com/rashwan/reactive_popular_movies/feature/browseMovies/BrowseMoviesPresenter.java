package com.rashwan.reactive_popular_movies.feature.browseMovies;

import com.rashwan.reactive_popular_movies.common.BasePresenter;
import com.rashwan.reactive_popular_movies.model.MoviesResponse;
import com.rashwan.reactive_popular_movies.service.MoviesService;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by rashwan on 6/24/16.
 */

public class BrowseMoviesPresenter extends BasePresenter<BrowseMoviesView>  {
    private Observable<MoviesResponse> request;
    @Inject
    public BrowseMoviesPresenter(MoviesService moviesService) {
        request = moviesService.getPopularMovies().cache();
    }


    public void getMovies(){
        //Retrieve Movies using Retrofit then on success call getView.showMovies()
        request.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(moviesResponse -> {
                getView().hideProgress();
                getView().showMovies(moviesResponse.getMovies());
                Timber.d(moviesResponse.getMovies().get(1).getTitle());
            }
                , throwable -> Timber.d(throwable,"Error retrieving movies")
                , () ->Timber.d("Finished getting movies"));
    }


}

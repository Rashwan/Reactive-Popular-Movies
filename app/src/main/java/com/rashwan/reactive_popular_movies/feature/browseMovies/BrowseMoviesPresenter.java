package com.rashwan.reactive_popular_movies.feature.browseMovies;

import com.rashwan.reactive_popular_movies.common.BasePresenter;
import com.rashwan.reactive_popular_movies.service.MoviesService;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by rashwan on 6/24/16.
 */

public class BrowseMoviesPresenter extends BasePresenter<BrowseMoviesView>  {
     private MoviesService moviesService;

    public BrowseMoviesPresenter(MoviesService moviesService) {
        this.moviesService  = moviesService;
    }

    public void getMovies(){
        //Retrieve Movies using Retrofit then on success call getView.showMovies()
        moviesService.getPopularMovies().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(moviesResponse -> {
            getView().showMovies(moviesResponse.getMovies());
            Timber.d(moviesResponse.getMovies().get(1).getTitle());
        }
                , throwable -> Timber.d(throwable,"Error retrieving movies")
                , () ->Timber.d("Finished getting movies"));
    }


}

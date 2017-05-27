package com.rashwan.reactive_popular_movies.feature.discoverMovies;

import com.rashwan.reactive_popular_movies.common.BasePresenter;
import com.rashwan.reactive_popular_movies.common.utilities.Exceptions.NoInternetException;
import com.rashwan.reactive_popular_movies.common.utilities.Utilities;
import com.rashwan.reactive_popular_movies.dI.PerFragment;
import com.rashwan.reactive_popular_movies.data.MoviesRepository;
import com.rashwan.reactive_popular_movies.data.model.Movie;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import timber.log.Timber;

import static com.rashwan.reactive_popular_movies.feature.BaseFragment.SORT_POPULAR_MOVIES;
import static com.rashwan.reactive_popular_movies.feature.BaseFragment.SORT_TOP_RATED_MOVIES;
import static com.rashwan.reactive_popular_movies.feature.BaseFragment.SORT_UPCOMING_MOVIES;

/**
 * Created by rashwan on 6/24/16.
 */
@PerFragment
public class BrowseMoviesPresenter extends BasePresenter<BrowseMoviesView>  {
    private Subscription browseSubscription;
    private int page ;
    private MoviesRepository moviesRepository;

    @Inject
    public BrowseMoviesPresenter(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
        page = 1;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (browseSubscription != null) browseSubscription.unsubscribe();
    }
    public void cancelInFlightRequests(){
        if (browseSubscription != null) browseSubscription.unsubscribe();
    }

    public void getMovies(int sortBy,Boolean firstPage){
        checkViewAttached();
        if (firstPage) {
            getView().clearScreen();
            getView().showProgress();
            page = 1;
        }
        Observable<List<Movie>> request ;
        switch (sortBy){
            case SORT_POPULAR_MOVIES:
                request = moviesRepository.getPopularMovies(page);
                break;
            case SORT_TOP_RATED_MOVIES:
                request = moviesRepository.getTopRatedMovies(page);
                break;
            case SORT_UPCOMING_MOVIES:
                request = moviesRepository.getUpcomingMovies(page);
                break;
            default:
                request = moviesRepository.getPopularMovies(page);
        }

        browseSubscription = request.compose(Utilities.applySchedulers()).subscribe(movies -> {
                getView().hideProgress();
                getView().showMovies(movies);
                Timber.d(movies.get(1).title());
                }
                , throwable -> {
                        if (throwable instanceof NoInternetException){
                            NoInternetException exception = (NoInternetException) throwable;
                            Timber.d("Error retrieving movies: %s . First page: %s",exception.message,exception.firstPage);
                            if (((NoInternetException) throwable).firstPage){
                                getView().hideProgress();
                                getView().showOfflineLayout();
                            }else {
                                getView().showOfflineSnackbar();
                            }
                        }
                        else {
                            Timber.d(throwable,"Error retrieving movies");
                        }
                    }
                , () -> {
                        page ++;
                        Timber.d("Finished getting movies");
                    });

    }
}

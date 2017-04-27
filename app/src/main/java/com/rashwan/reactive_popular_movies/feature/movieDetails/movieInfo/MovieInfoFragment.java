package com.rashwan.reactive_popular_movies.feature.movieDetails.movieInfo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rashwan.reactive_popular_movies.PopularMoviesApplication;
import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.common.utilities.DelegateToActivity;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.rashwan.reactive_popular_movies.data.model.MovieDetails;
import com.rashwan.reactive_popular_movies.data.model.Rating;
import com.rashwan.reactive_popular_movies.data.model.Trailer;
import com.rashwan.reactive_popular_movies.feature.discoverMovies.BrowseMoviesActivity;
import com.rashwan.reactive_popular_movies.feature.favoriteMovies.FavoriteMoviesActivity;
import com.rashwan.reactive_popular_movies.feature.movieDetails.MovieDetailsActivity;
import com.rashwan.reactive_popular_movies.feature.movieDetails.ShowDetailsInActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Observable;

/**
 * Created by rashwan on 7/3/16.
 */

public class MovieInfoFragment extends Fragment implements MovieInfoView
        ,MovieTrailersAdapter.ClickListener,SimilarMoviesAdapter.ClickListener {

    private static final String ARGUMENT_MOVIE = "ARGUMENT_MOVIE";
    private static final ButterKnife.Action SHOW = (view, index) -> view.setVisibility(View.VISIBLE);
    private static final ButterKnife.Action HIDE = (view, index) -> view.setVisibility(View.GONE);
    private DelegateToActivity<Movie> delegateListener;
    private Movie movie;
    private Unbinder unbinder;
    private boolean isTwoPane = false;
    private Observable<Trailer> shareTrailerObservable = Observable.empty();
    private ShowDetailsInActivity showDetailsInActivityListener;
    @BindViews({R.id.text_no_internet,R.id.button_refresh}) List<View> offlineViews;
    @BindView(R.id.rv_trailers) RecyclerView rvTrailer;
    @BindView(R.id.text_description) TextView description;
    @BindView(R.id.image_rotten_logo) ImageView imageRottenLogo;
    @BindView(R.id.image_metacritic_logo) ImageView imageMetacriticLogo;
    @BindView(R.id.text_tmdb_rating) TextView textTmdbRating;
    @BindView(R.id.text_imdb_rating) TextView textImdbRating;
    @BindView(R.id.text_rotten_rating) TextView textRottenRating;
    @BindView(R.id.text_metacritic_rating) TextView textMetacriticRating;
    @BindView(R.id.text_box_office) TextView textBoxOffice;
    @BindView(R.id.text_awards) TextView textAwards;
    @BindView(R.id.text_production) TextView textProduction;
    @BindView(R.id.rv_similar_movies) RecyclerView rvSimilarMovies;
    @BindColor(R.color.colorPrimaryDark) int primaryDarkColor;
    @BindColor(R.color.metacritic_average) int metacriticAverageColor;
    @BindColor(R.color.metacritic_unfavorable) int metacriticUnfavorableColor;
    @BindViews({R.id.rv_trailers,R.id.text_trailers_title,R.id.divider_overview_trailers})
    List<View> trailersViews;
    @BindViews({R.id.rv_similar_movies,R.id.text_similar_movies_title,R.id.divider_trailers_similar_movies})
    List<View> similarMoviesViews;
    @Inject MovieTrailersAdapter trailersAdapter;
    @Inject SimilarMoviesAdapter similarMoviesAdapter;
    @Inject MovieInfoPresenter presenter;

    public static MovieInfoFragment newInstance(Movie movie) {
        MovieInfoFragment movieInfoFragment = new MovieInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT_MOVIE, movie);
        movieInfoFragment.setArguments(bundle);
        return movieInfoFragment;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BrowseMoviesActivity || context instanceof FavoriteMoviesActivity){
            isTwoPane = true;
        }
        if (context instanceof DelegateToActivity){
            delegateListener = (DelegateToActivity) context;
        }
        if (context instanceof MovieDetailsActivity){
            showDetailsInActivityListener = (ShowDetailsInActivity) context;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        delegateListener = null;
        showDetailsInActivityListener = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ((PopularMoviesApplication)getActivity().getApplication()).createMovieInfoComponent()
                .inject(this);
        movie = getArguments().getParcelable(ARGUMENT_MOVIE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_info, container, false);
        unbinder = ButterKnife.bind(this, view);
        setupLayout();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        ((PopularMoviesApplication)getActivity().getApplication()).releaseMovieInfoComponent();
    }

    @Override
    public void showTrailers(List<Trailer> trailers) {
        ButterKnife.apply(trailersViews,SHOW);
        shareTrailerObservable = (Observable.just(trailers.get(0)));
        trailersAdapter.setTrailers(trailers);
        trailersAdapter.notifyDataSetChanged();
    }


    @Override
    public void showOfflineLayout() {
        ButterKnife.apply(offlineViews,SHOW);
    }

    @Override
    public void hideOfflineLayout() {
        ButterKnife.apply(offlineViews,HIDE);
    }



    @Override
    public void showMovieRuntime(String runtime) {
        showDetailsInActivityListener.showRuntime(runtime);
    }

    @Override
    public void showShareIcon(String trailerUrl) {
        showDetailsInActivityListener.showShareIcon(trailerUrl);
    }

    @Override
    public void showPlayMainTrailer(Uri mainTrailerUri) {
        showDetailsInActivityListener.showPlayMainTrailer(mainTrailerUri);
    }

    @Override
    public void showOmdbDetails(MovieDetails movieDetails) {
        showDetailsInActivityListener.showOmdbDetails(movieDetails);
        populateRatings(movieDetails);
        textAwards.setText(movieDetails.awards());
        textBoxOffice.setText(movieDetails.getFormattedBoxOffice());
        textProduction.setText(movieDetails.production());
    }

    @Override
    public void showSimilarMovies(List<Movie> movies) {
        ButterKnife.apply(similarMoviesViews,SHOW);
        similarMoviesAdapter.addMovies(movies);
        similarMoviesAdapter.notifyDataSetChanged();
    }

    private void populateRatings(MovieDetails movieDetails) {
        textTmdbRating.setText(movie.vote_average());
        textImdbRating.setText(movieDetails.imdbRating());
        textMetacriticRating.setText(movieDetails.metascore());
        for (Rating rating: movieDetails.ratings()) {
           if (rating.source().equals(Rating.ROTTEN_TOMATOES_KEY)) {
               textRottenRating.setText(rating.value());
               if (!rating.value().equals("N/A")) {
                   if (Integer.valueOf(rating.value().substring(0, 2)) < 60) {
                       imageRottenLogo.setImageResource(R.drawable.ic_rotten_tomatoes_spat);
                   }
               }
               break;
           }
        }
        if (!movieDetails.metascore().equals("N/A")) {
            Integer metascore = Integer.valueOf(movieDetails.metascore());
            if (metascore < 40) {
                imageMetacriticLogo.setBackgroundColor(metacriticUnfavorableColor);
            } else if (metascore > 40 && metascore < 61) {
                imageMetacriticLogo.setBackgroundColor(metacriticAverageColor);
            }
        }
    }



    @Override
    public void onTrailerClicked(Uri trailerYoutubeUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW,trailerYoutubeUrl);
        startActivity(intent);
    }

    @OnClick(R.id.button_refresh)
    public void onRefreshClicked(){
        presenter.getMovieDetails(movie.id());
        presenter.getTrailers(movie.id());
        presenter.getSimilarMovies(movie.id());
    }


    @OnClick({R.id.image_tmdb_logo,R.id.text_tmdb_rating})
    public void onTmdbRatingClicked(){
        showDetailsInActivityListener.showReviewMessage("The Movie Database Rating");
    }
    @OnClick({R.id.image_imdb_logo,R.id.text_imdb_rating})
    public void onImdbRatingClicked(){
        showDetailsInActivityListener.showReviewMessage("Internet Movie Database Rating");

    }
    @OnClick({R.id.image_rotten_logo,R.id.text_rotten_rating})
    public void onRottenRatingClicked(){
        showDetailsInActivityListener.showReviewMessage("Rotten Tomatoes Rating");

    }
    @OnClick({R.id.image_metacritic_logo,R.id.text_metacritic_rating})
    public void onMetacriticRatingClicked(){
        showDetailsInActivityListener.showReviewMessage("Metacritic Rating");

    }

    public Observable<Trailer> getShareTrailerObservable() {
        return shareTrailerObservable;
    }

    private void setupLayout() {

        presenter.attachView(this);
        presenter.getMovieDetails(movie.id());
        presenter.getTrailers(movie.id());
        presenter.getSimilarMovies(movie.id());
        setupTrailerRv();
        setupSimilarMoviesrRv();
        populateMovieDetails();
    }

    private void populateMovieDetails() {
        description.setText(movie.overview());
    }



    private void setupTrailerRv() {
        trailersAdapter.setClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        rvTrailer.setLayoutManager(linearLayoutManager);
        rvTrailer.setHasFixedSize(true);
        rvTrailer.setNestedScrollingEnabled(false);
        rvTrailer.setAdapter(trailersAdapter);
    }

    private void setupSimilarMoviesrRv() {
        similarMoviesAdapter.setClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        rvSimilarMovies.setLayoutManager(linearLayoutManager);
        rvSimilarMovies.setHasFixedSize(true);
        rvSimilarMovies.setNestedScrollingEnabled(false);
        rvSimilarMovies.setAdapter(similarMoviesAdapter);
    }


    @Override
    public void onMovieClicked(Movie movie, ImageView view) {
        delegateListener.delegateItemClicked(movie,view);
    }
}

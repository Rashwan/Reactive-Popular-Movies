package com.rashwan.reactive_popular_movies.feature.discoverMovies;

import android.content.Context;
import android.os.Build;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.common.utilities.PaletteTransformation;
import com.rashwan.reactive_popular_movies.common.utilities.Utilities;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rashwan on 6/26/16.
 */

public class BrowseMoviesAdapter extends RecyclerView.Adapter<ViewHolder> {

    private ClickListener mClickListener;
    private List<Movie> movies;
    @BindColor(R.color.colorPrimaryDark) int defaultBGColor;
    @BindColor(R.color.primaryText) int defaultTextColor;


    public BrowseMoviesAdapter() {
        movies = new ArrayList<>();
    }

    public void setClickListener(ClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }
    public void removeClickListener(){
        this.mClickListener  = null;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_browse_movie, parent, false);
        ButterKnife.bind(this, view);
        return new BrowseMoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Context context = holder.itemView.getContext();
        Movie movie = movies.get(position);
        BrowseMoviesViewHolder browseViewHolder = (BrowseMoviesViewHolder) holder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            browseViewHolder.ivMoviePoster.setTransitionName("poster_" + movie.id());
        }
        browseViewHolder.mMovie = movie;
        browseViewHolder.tvMovieTitle.setText(movie.title());
        Picasso.with(context)
            .load(Utilities.getFullPosterPath(context,movie.posterPath(),Utilities.QUALITY_LOW))
            .tag(BrowseMoviesActivity.class)
            .transform(new PaletteTransformation())
            .into(browseViewHolder.ivMoviePoster, new PaletteTransformation.Callback(
                    browseViewHolder.ivMoviePoster) {
                @Override
                public void onPalette(Palette palette) {
                    if (palette != null) {
                        final Palette.Swatch titleSwatch = palette.getDarkVibrantSwatch();
                        final int bgColor = titleSwatch != null ? titleSwatch.getRgb() : defaultBGColor;
                        final int textColor = titleSwatch != null ? titleSwatch.getTitleTextColor() : defaultTextColor;
                        if (titleSwatch != null) {
                            browseViewHolder.tvMovieTitle.setBackgroundColor(bgColor);
                        }
                        browseViewHolder.tvMovieTitle.setTextColor(textColor);

                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return this.movies.size();
    }

    public void addMovies(List<Movie> movies){
        this.movies.addAll(movies);
    }
    public void addMovie(Movie movie){
        this.movies.add(movie);
    }
    public void clearMovies(){
        this.movies.clear();
    }

    public Boolean isEmpty(){return this.movies.isEmpty();}

    public class BrowseMoviesViewHolder extends ViewHolder {
        @BindView(R.id.image_movie_poster) ImageView ivMoviePoster;
        @BindView(R.id.text_movie_title) TextView tvMovieTitle;
        Movie mMovie;

        public BrowseMoviesViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.movie_item)
        public void movieClicked(View view) {
            if (mClickListener != null){
                ImageView poster = ButterKnife.findById(view,R.id.image_movie_poster);
                mClickListener.onMovieClicked(mMovie, poster);
            }

        }
    }

    public interface ClickListener {
        void onMovieClicked(Movie movie, ImageView view);
    }
}


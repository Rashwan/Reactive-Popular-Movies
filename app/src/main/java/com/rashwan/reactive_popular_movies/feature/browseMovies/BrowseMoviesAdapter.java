package com.rashwan.reactive_popular_movies.feature.browseMovies;

import android.content.Context;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.common.utilities.PaletteTransformation;
import com.rashwan.reactive_popular_movies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rashwan on 6/26/16.
 */

public class BrowseMoviesAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<Movie> movies;
    @BindColor(R.color.colorPrimaryDark)
    int defaultBGColor;
    @BindColor(R.color.blackText)
    int defaultTextColor;
    private ClickListener mClickListener;


    @Inject
    public BrowseMoviesAdapter() {
        movies = new ArrayList<>();
    }

    public void setClickListener(ClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    @Override
    public BrowseMoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_browse_movies, parent, false);
        ButterKnife.bind(this, view);
        return new BrowseMoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Context context = holder.itemView.getContext();
        if (holder instanceof BrowseMoviesViewHolder) {
            //make the api call and adapt the result to the items
            Movie movie = movies.get(position);
            BrowseMoviesViewHolder browseViewHolder = (BrowseMoviesViewHolder) holder;
            browseViewHolder.tvMovieTitle.setText(movie.getTitle());
            Picasso.with(context)
                    .load(movie.getFullPosterPath(Movie.QUALITY_MEDIUM))
                    .transform(new PaletteTransformation())
                    .into(browseViewHolder.ivMoviePoster, new PaletteTransformation.Callback(browseViewHolder.ivMoviePoster) {
                        @Override
                        public void onPalette(Palette palette) {
                            final Palette.Swatch titleSwatch = palette.getVibrantSwatch();
                            final int bgColor = titleSwatch != null ? titleSwatch.getRgb() : defaultBGColor;
                            final int textColor = titleSwatch != null ? titleSwatch.getBodyTextColor() : defaultTextColor;
                            if (titleSwatch != null) {
                                browseViewHolder.tvMovieTitle.setBackgroundColor(bgColor);
                                browseViewHolder.tvMovieTitle.setTextColor(textColor);
                            }
                        }
                    });
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void add(Movie movie) {
        movies.add(movie);
    }

    public void setMovies(List<Movie> movies) {

        this.movies = movies;
    }


    public class BrowseMoviesViewHolder extends ViewHolder {
        @BindView(R.id.iv_movie_poster)
        ImageView ivMoviePoster;
        @BindView(R.id.tv_movie_title)
        TextView tvMovieTitle;

        public BrowseMoviesViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.iv_movie_poster, R.id.tv_movie_title})
        public void movieClicked() {
            if (mClickListener != null){
                mClickListener.onMovieClicked();
            }

        }
    }

    static class ProgressBarViewHolder extends ViewHolder {
        @BindView(R.id.progressbar_browse_movies)
        ProgressBar pbBrowseMovies;

        ProgressBarViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface ClickListener {
        void onMovieClicked();
    }
}

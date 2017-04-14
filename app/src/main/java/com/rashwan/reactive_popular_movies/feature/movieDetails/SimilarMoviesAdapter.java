package com.rashwan.reactive_popular_movies.feature.movieDetails;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rashwan on 4/14/17.
 */

public class SimilarMoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Movie> movies;
    private ClickListener mClickListener;

    public SimilarMoviesAdapter() {
        movies = new ArrayList<>();
    }
    public void setClickListener(ClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_similar_movie, parent, false);
        return new SimilarMoviesVH(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Context context = holder.itemView.getContext();
        Movie movie = movies.get(position);
        SimilarMoviesVH similarViewHolder = (SimilarMoviesVH) holder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            similarViewHolder.similarMoviePoster.setTransitionName("similarPoster_"+movie.id());
        }
        similarViewHolder.movie = movie;
        similarViewHolder.similarMovieTitle.setText(movie.title());
        Picasso.with(context)
                .load(movie.getFullPosterPath(Movie.QUALITY_LOW))
                .into(similarViewHolder.similarMoviePoster);
    }

    @Override
    public int getItemCount() {
        return movies.size();
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

    public class SimilarMoviesVH extends RecyclerView.ViewHolder{
        @BindView(R.id.image_similar_movie_poster) ImageView similarMoviePoster;
        @BindView(R.id.text_similar_movie_title) TextView similarMovieTitle;
        private Movie movie;
        public SimilarMoviesVH(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }

        @OnClick(R.id.similar_movie_item)
        public void movieClicked(View view) {
            if (mClickListener != null){
                ImageView poster = ButterKnife.findById(view,R.id.image_similar_movie_poster);
                mClickListener.onMovieClicked(movie, poster);
            }
        }
    }
    public interface ClickListener {
        void onMovieClicked(Movie movie, ImageView view);
    }
}
